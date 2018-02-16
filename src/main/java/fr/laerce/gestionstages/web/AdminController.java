package fr.laerce.gestionstages.web;


import fr.laerce.gestionstages.dao.*;
import fr.laerce.gestionstages.domain.*;
import fr.laerce.gestionstages.service.ImportProfesseursFromSTS;
import fr.laerce.gestionstages.service.ImportSTSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class AdminController {

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

    @Autowired
    private ImportProfesseursFromSTS importProfesseursFromSTS;


    @GetMapping("/populate")
    public String populate(Model model) {
        String errorServiceImport = "";

        try {
            this.importProfesseursFromSTS.parseAndCreateUpdateIntoDatabase("J:/CDI/JAVAprojets/gestionStages/src/main/docs/sts_emp_0940321S_2017(clean).xml");
        } catch (ImportSTSException e) {
            errorServiceImport = "Erreur traitement XML : " + e.getMessage();
        } catch (Exception e) {
            errorServiceImport = "Erreur synchronisation base de données : " + e.getMessage();
        }

        if (!errorServiceImport.isEmpty()) {
            model.addAttribute("message", errorServiceImport);
        }

        model.addAttribute("niveaux", importProfesseursFromSTS.getDicoNiveaux().size());
        model.addAttribute("disciplines", importProfesseursFromSTS.getDicoDisciplines().size());
        model.addAttribute("divisions", importProfesseursFromSTS.getDicoDivisions().size());
        model.addAttribute("individus", importProfesseursFromSTS.getDicoIndividus().size());
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


    @GetMapping("/test")
    public String testCheck(Model model) {
        Professeur p1 = new Professeur();
        Eleve p2 = new Eleve();
        try {
            p1.setCivilite("M.");
            p1.setEmail("aze@rty");
            p1.setLogin("login5");
            repoProfesseur.save(p1);
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
