package fr.laerce.gestionstages.web;


import fr.laerce.gestionstages.dao.DisciplineRepository;
import fr.laerce.gestionstages.dao.DivisionRepository;
import fr.laerce.gestionstages.dao.IndividuRepository;
import fr.laerce.gestionstages.dao.NiveauReposiroty;
import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.domain.Division;
import fr.laerce.gestionstages.domain.Individu;
import fr.laerce.gestionstages.domain.Niveau;
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

  @Autowired
  private NiveauReposiroty repoNiveau;

  @Autowired
  private DivisionRepository repoDivision;

  @Autowired
  private IndividuRepository repoIndividu;

  private ImportFromSTS importFromSTS;

  @Autowired
  public void setImportFromSTS(ImportFromSTS importFromSTS) {
    this.importFromSTS = importFromSTS;
    try {
      this.importFromSTS.parse("/Users/fred/files/sts_emp_0940321S_2017.xml");
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

    for(String code : importFromSTS.getDicoNiveaux().keySet()){
        Niveau niveauManaged = repoNiveau.findByCode(code);
        Niveau niveauImported = importFromSTS.getDicoNiveaux().get(code);

        if(niveauManaged != null){
            if(!niveauManaged.equals(niveauImported)){

            }
            niveauManaged.setLibelleCourt(niveauImported.getLibelleCourt());
            niveauManaged.setLibelleLong(niveauImported.getLibelleLong());
            repoNiveau.save(niveauManaged);
        } else {
            repoNiveau.save(niveauImported);
        }
    }

    for(String code : importFromSTS.getDicoDivisions().keySet()){
        Division divisionManaged = repoDivision.findByCode(code);
        Division divisionImported = importFromSTS.getDicoDivisions().get(code);

        if(divisionManaged != null){
            divisionManaged.setLibelle(divisionImported.getLibelle());
            repoDivision.save(divisionManaged);
        } else {
            Niveau niveau = repoNiveau.findByCode(divisionImported.getNiveau().getCode());
            divisionImported.setNiveau(niveau);
            repoDivision.save(divisionImported);
        }
    }

    for(String code : importFromSTS.getDicoIndividus().keySet()){
        Individu individuManaged = repoIndividu.findByCodeSynchro(code);
        Individu individuImported = importFromSTS.getDicoIndividus().get(code);

        if(individuManaged != null){

        } else {
            for(Discipline discipline: individuImported.getDisciplines()){
                Discipline discipline1 = repoDiscipline.findByCode(discipline.getCode());
                discipline = discipline1;
            }
            for(Division division: individuImported.getDivisions()){

            }
            repoIndividu.save(individuImported);
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
