package fr.laerce.gestionstages.dao;

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
public class NiveauDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Niveau> findAll() {
        return entityManager.createQuery("SELECT n FROM  Niveau n", Niveau.class).getResultList();
    }

    public void persist(Niveau niveau) {
        entityManager.merge(niveau);
    }

    public Niveau getById(Long id) {
        return entityManager.find(Niveau.class, id);
    }

    public void delete(Niveau niveau) {
        entityManager.remove(niveau);
    }
}
