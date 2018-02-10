package fr.laerce.gestionstages;

import fr.laerce.gestionstages.dao.DisciplineRepository;
import fr.laerce.gestionstages.domain.Discipline;

/**
 * Projet gestionstages
 * Pour LAERCE SAS
 * <p>
 * Créé le  23/01/2018.
 *
 * @author fred
 */
public class ExperienceXml {


  public void run(DisciplineRepository disciplineRepository) {
    Discipline d = new Discipline();
    d.setCode("Math");
    d.setLibelle("Mathématiques");
   // disciplineRepository.save(d);
  }
}