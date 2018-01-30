package fr.laerce.gestionstages.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Projet gestionstages
 * Pour LAERCE SAS
 * <p>
 * Créé le  26/01/2018.
 *
 * @author fred
 */

@ConfigurationProperties("gestages.import")
public class ImportSTSProperties {
    private String stsFile;

    public String getStsFile() {
        return stsFile;
    }

    public void setStsFile(String stsFile) {
        this.stsFile = stsFile;
    }
}
