package fr.laerce.gestionstages;

import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.domain.Individu;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootApplication
public class GestionstagesApplication implements ApplicationRunner {
    @PersistenceContext
    EntityManager em;

    public static void main(String[] args) {

        SpringApplication.run(GestionstagesApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        // passer spring.jpa.hibernate.ddl-auto=create
        // exécuter une fois ce code
        // passer spring.jpa.hibernate.ddl-auto=update
        // et commenter le code à l'exception des 2 dernières instructions
        // afin de vérifier que les Individus sont bien remontés

        Individu p = new Individu();
        p.setNom("Martin");
        p.setPrenom("Paul");
        p.setCivilite("M.");

        Discipline d = new Discipline();
        d.setCode("Math");
        d.setLibelle("Mathématique");

        p.setDiscipline(d);
        em.persist(d);
        em.persist(p);

        p = new Individu();
        p.setNom("Martin");
        p.setPrenom("Pauline");
        p.setCivilite("Me");
        p.setDiscipline(d);

        em.persist(p);
        System.out.println("professeurs de la discipline :"
                + d.getProfesseurs());


        Discipline d2 = em.find(Discipline.class, new Long(1));
        System.out.println("professeurs de la discipline :"
                + d2.getProfesseurs());

    }
}
