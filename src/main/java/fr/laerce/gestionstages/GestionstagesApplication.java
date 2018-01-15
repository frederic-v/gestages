package fr.laerce.gestionstages;

import fr.laerce.gestionstages.domain.Professeur;
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
        Professeur p = new Professeur();
        p.setNom("Martin");
        p.setPrenom("Paul");
        em.merge(p);
    }
}
