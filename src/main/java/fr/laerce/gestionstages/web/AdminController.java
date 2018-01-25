package fr.laerce.gestionstages.web;

import fr.laerce.gestionstages.dao.DisciplineRepository;
import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.service.GSImportSTSException;
import fr.laerce.gestionstages.service.ImportFromSTSBis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class AdminController {

  String errorServiceImport;

  @Autowired
  DisciplineRepository repo;

  ImportFromSTSBis importFromSTS;

  @Autowired
  public void setImportFromSTS(ImportFromSTSBis importFromSTS) {
    this.importFromSTS = importFromSTS;
    try {
      this.importFromSTS.parse("/home/kpu/download/sts_emp_0940321S_2017.xml");
    }catch (GSImportSTSException e) {
      errorServiceImport = e.getMessage();
      System.out.println("in injection errorServiceImport = "+ errorServiceImport);
    }
  }

  @GetMapping("/populate")
  public String populate(Model model) {
    System.out.println("in pupulate errorServiceImport = "+ errorServiceImport);
    if (errorServiceImport != null && !errorServiceImport.isEmpty()) {
      model.addAttribute("message", errorServiceImport);
    }
    model.addAttribute("niveaux", importFromSTS.getDicoNiveaux().size());
    model.addAttribute("discilines", importFromSTS.getDicoDisciplines().size());
    return "populate";
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
    System.out.println("Nb de niveaux = " + importFromSTS.getDicoNiveaux().size());
    System.out.println("Nb de disciplines = " + importFromSTS.getDicoDisciplines().size());
    model.addAttribute("disciplines", repo.findAll());
    return "disciplines";
  }

  @GetMapping("/deletediscipline/{id}")
  public String deleteDiscipline(@PathVariable("id") Long id) {
    // le repository nous oblige à géger les pointeurs null
    // via un Optional
    Optional<Discipline> d = repo.findById(id);
    if (d.isPresent()) {
      repo.delete(d.get());
    }
    return "redirect:/disciplines";
  }
}
