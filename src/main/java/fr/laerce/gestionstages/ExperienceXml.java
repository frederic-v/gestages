package fr.laerce.gestionstages;

import fr.laerce.gestionstages.service.ImportFromSTS;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

        ImportFromSTS importFromSTS = new ImportFromSTS();
        try {
            importFromSTS.parse(new FileInputStream("/users/fred/sts_emp_0940321S_2017.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
