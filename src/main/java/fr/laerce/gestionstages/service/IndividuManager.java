package fr.laerce.gestionstages.service;

import fr.laerce.gestionstages.dao.EleveRepository;
import fr.laerce.gestionstages.dao.IndividuRepository;
import fr.laerce.gestionstages.dao.ProfesseurRepository;
import fr.laerce.gestionstages.domain.Eleve;
import fr.laerce.gestionstages.domain.Individu;
import fr.laerce.gestionstages.domain.Professeur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class IndividuManager {
  private static final Logger log = LoggerFactory.getLogger(IndividuManager.class);
  public static final String CREATE_LOGIN_NON_DISPONIBLE = "CREATE : Tentative d'ajout d'un individu avec un login non disponible.";
  public static final String UPDATE_LOGIN_NON_DISPONIBLE = "UPDATE : Login non disponible !";
  public static final String LOGIN_DUPLICATE = "Login duplicate";

  private IndividuRepository individuRepository;
  private ProfesseurRepository professeurRepository;
  private EleveRepository eleveRepository;

  @Autowired
  public IndividuManager(IndividuRepository individuRepository,
                         ProfesseurRepository professeurRepository,
                         EleveRepository eleveRepository) {
    this.individuRepository = individuRepository;
    this.professeurRepository = professeurRepository;
    this.eleveRepository = eleveRepository;
  }

  @Transactional (rollbackFor = IllegalStateException.class)
  void saveIndividu(Individu individu, JpaRepository repository) {
      if (individu.getId() == null) {
        boolean okLogin = individuRepository.countAllByLogin(individu.getLogin()) == 0;
        if (okLogin) {
          repository.save(individu);
        } else {
          log.warn(CREATE_LOGIN_NON_DISPONIBLE);
          throw new DuplicateLoginException(CREATE_LOGIN_NON_DISPONIBLE);
        }
      } else {
        Individu other = individuRepository.findByLogin(individu.getLogin());
        if (other != null && other.getId() == individu.getId()) {
          repository.save(individu);
        } else {
          log.warn(UPDATE_LOGIN_NON_DISPONIBLE);
          throw new DuplicateLoginException(UPDATE_LOGIN_NON_DISPONIBLE);
        }
      }
      // possible throws IllegalStateEpression if concurrent write access
      Assert.state(individuRepository.countAllByLogin(individu.getLogin()) == 1, LOGIN_DUPLICATE);
  }

  @Transactional
  public void saveProfesseurWithCheckUniqueLogin(Professeur professeur) {
    saveIndividu(professeur, professeurRepository);
  }

  @Transactional
  public void saveEleveWithCheckUniqueLogin(Eleve eleve) {
    saveIndividu(eleve, eleveRepository);
  }

}
