package fr.laerce.gestionstages;

import fr.laerce.gestionstages.dao.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionstagesApplication implements ApplicationRunner {
  @Autowired
  DisciplineRepository disciplineRepository;

  public static void main(String[] args) {

        SpringApplication.run(GestionstagesApplication.class, args);
    }

  @Override
  public void run(ApplicationArguments args) throws Exception {
     ExperienceXml run = new ExperienceXml() ;
     run.run(disciplineRepository);
  }
}
