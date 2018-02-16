package fr.laerce.gestionstages.web;


import fr.laerce.gestionstages.dao.*;
import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.domain.Eleve;
import fr.laerce.gestionstages.domain.Professeur;
import fr.laerce.gestionstages.service.ImportProfesseursFromSTS;
import fr.laerce.gestionstages.service.ImportSTSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
      return "index";
    }

    /** un dropdown qui fonctionne avec BT-beta et CDN qui vont bien
     *
     */
    @GetMapping("/dropdown")
    public String dropdown(){
        return "testDropDown";
    }

}
