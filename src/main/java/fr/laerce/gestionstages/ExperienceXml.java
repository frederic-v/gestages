package fr.laerce.gestionstages;

import fr.laerce.gestionstages.service.ImportFromSTSBis;
import fr.laerce.gestionstages.service.STSImportException;

/**
 * Projet gestionstages
 * Pour LAERCE SAS
 * <p>
 * Créé le  23/01/2018.
 *
 * @author fred
 */
public class ExperienceXml {

  public static void main(String[] args) {
    ExperienceXml app = new ExperienceXml();
    app.run();
  }

  public void run() {
    try {
      ImportFromSTSBis importFromSTS = new ImportFromSTSBis();
      importFromSTS.setFileName("/home/kpu/download/sts_emp_0940321S_2017.xml");
      System.out.println("Nb de niveaux = " + importFromSTS.getDicoNiveaux().size());
    } catch (STSImportException e) {
      System.out.println("ERREUR = " + e.getMessage());
    }
  }
}