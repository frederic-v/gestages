package fr.laerce.gestionstages.configuration;

import fr.laerce.gestionstages.service.ImportFromSTS;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Projet gestionstages
 * Pour LAERCE SAS
 * <p>
 * Créé le  26/01/2018.
 *
 * @author fred
 */
@Configuration
@EnableConfigurationProperties(ImportSTSProperties.class)
public class GeStagesConfiguration {
}
