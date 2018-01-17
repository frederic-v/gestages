package fr.laerce.gestionstages.dao;

import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.domain.Niveau;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Projet pfmp-base
 * Pour LAERCE SAS
 * <p>
 * Créé le  12/01/2018.
 *
 * @author fred
 */

@Repository
public class DisciplineDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Discipline> findAll() {
        return entityManager.createQuery("SELECT d FROM Discipline d", Discipline.class).getResultList();
    }

    public void persist(Discipline discipline) {
        entityManager.merge(discipline);
    }

    public Discipline getById(Long id) {
        return entityManager.find(Discipline.class, id);
    }

    public void delete(Discipline discipline) {
        entityManager.remove(discipline);
    }
}
