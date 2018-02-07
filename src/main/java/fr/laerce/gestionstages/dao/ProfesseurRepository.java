package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Projet gestionstages
 * Pour LAERCE SAS
 * <p>
 * Créé le  06/02/2018.
 *
 * @author fred
 */
public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    public Professeur findByCodeSynchro(String code);
}
