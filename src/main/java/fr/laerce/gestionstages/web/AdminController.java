package fr.laerce.gestionstages.web;

import fr.laerce.gestionstages.dao.DisciplineRepository;
import fr.laerce.gestionstages.domain.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    DisciplineRepository repo;

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
        model.addAttribute("disciplines", repo.findAll());
        return "disciplines";
    }

    @GetMapping("/deletedisciplines/{id}")
    public String listeDisciplines(@PathVariable("id") long id) {
        // le repository nous oblige à géger les pointeurs null
        // via un Optional
        Optional<Discipline> d = repo.findById(id);
        if (d.isPresent()) {
            repo.delete(d.get());
        }
        return "redirect:/disciplines";
    }
}
