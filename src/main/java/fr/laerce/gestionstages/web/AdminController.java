package fr.laerce.gestionstages.web;

import fr.laerce.gestionstages.dao.DisciplineRepository;
import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.service.ImportFromSTS;
import fr.laerce.gestionstages.service.ImportSTSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {

  private String errorServiceImport;

  @Autowired
  private DisciplineRepository repoDiscipline;

  private ImportFromSTS importFromSTS;

  @Autowired
  public void setImportFromSTS(ImportFromSTS importFromSTS) {
    this.importFromSTS = importFromSTS;
    try {
      this.importFromSTS.parse("/home/kpu/download/sts_emp_0940321S_2017.xml");
    } catch (ImportSTSException e) {

      errorServiceImport = e.getMessage();
    }
  }

  @GetMapping("/populate")
  public String populate(Model model) {
    if (errorServiceImport != null && !errorServiceImport.isEmpty()) {
      model.addAttribute("message", errorServiceImport);
    }

    importIntoDataBase();

    model.addAttribute("niveaux", importFromSTS.getDicoNiveaux().size());
    model.addAttribute("disciplines", importFromSTS.getDicoDisciplines().size());
    model.addAttribute("divisions", importFromSTS.getDicoDivisions().size());
    model.addAttribute("individus", importFromSTS.getDicoIndividus().size());
    return "populate";
  }

  private void importIntoDataBase() {

    List<Discipline> updatedDisciplines = new ArrayList<>();

    for (String code : importFromSTS.getDicoDisciplines().keySet()) {
      Discipline disciplineDBManaged = repoDiscipline.findByCode(code);
      Discipline disciplineImported = importFromSTS.getDicoDisciplines().get(code);

      if (disciplineDBManaged != null) {
        if (!disciplineDBManaged.equals(disciplineImported)) {
          updatedDisciplines.add(disciplineDBManaged);
          // TODO "log" updated objects by import
        }
        // mise à jour des attributs d'après l'objet nouvellement importé
        disciplineDBManaged.setLibelle(disciplineImported.getLibelle());
        repoDiscipline.save(disciplineDBManaged);
      } else {
        repoDiscipline.save(disciplineImported);
      }
    }
  }


  // après avoir étudier
  //  https://spring.io/guides/gs/accessing-data-jpa/
  // puis
  //  https://spring.io/guides/gs/handling-form-submission/
  // implémenter une interaction de création, suppression et liste
  // des objets de type Discipline

  // voici un extrait de solution de suppression sans confirmation
  // de l'utilisateur (TODO).

  @GetMapping("/disciplines")
  public String listeDisciplines(Model model) {
    model.addAttribute("disciplines", repoDiscipline.findAll());
    return "disciplines";
  }

  @GetMapping("/deletediscipline/{id}")
  public String deleteDiscipline(@PathVariable("id") Long id) {
    // le repository nous oblige à géger les pointeurs null
    // via un Optional
    Optional<Discipline> d = repoDiscipline.findById(id);
    if (d.isPresent()) {
      repoDiscipline.delete(d.get());
    }
    return "redirect:/disciplines";
  }
}
