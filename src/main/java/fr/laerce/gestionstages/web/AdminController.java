package fr.laerce.gestionstages.web;


import fr.laerce.gestionstages.dao.*;
import fr.laerce.gestionstages.domain.*;
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
  private ProfesseurRepository repoProfesseur;

  @Autowired
  private EleveRepository repoEleve;


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
        Professeur individuManaged = repoProfesseur.findByCodeSynchro(code);
        Professeur individuImported = importFromSTS.getDicoIndividus().get(code);

        if(individuManaged != null){

        } else {
            for(Discipline discipline: individuImported.getDisciplines()){
                Discipline discipline1 = repoDiscipline.findByCode(discipline.getCode());
                discipline = discipline1;
            }
            for(Division division: individuImported.getDivisions()){

            }
            repoProfesseur.save(individuImported);
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


  @GetMapping("/test")
  public String testCheck(Model model) {
    Professeur p1 = new Professeur();
    Eleve p2 = new Eleve();
    try {
      p1.setCivilite("M.");
      p1.setEmail("aze@rty");
      p1.setLogin("login5");
      repoProfesseur .save(p1);
      p2.setCivilite("Me");
      p2.setEmail("aze@error");
      p2.setLogin("login6");
      repoEleve.save(p2);
    } catch (Exception e) {
      model.addAttribute("message", "Non enregistré : " + e.getMessage());
    }
    model.addAttribute("individu", p1);
    return "testcheck";
  }

}
