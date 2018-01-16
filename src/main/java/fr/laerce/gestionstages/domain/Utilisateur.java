package fr.laerce.gestionstages.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * Projet pfmp-base
 * Pour LAERCE SAS
 * <p>
 * Créé le  12/01/2018.
 *
 * @author fred
 */
@Entity
@Table(name="utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 50)
    private String login;
    @Column(nullable = false, length = 30)
    private String mdp;

    @OneToOne
    private Individu individu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Individu getIndividu() {
        return individu;
    }

    public void setIndividu(Individu individu) {
        this.individu = individu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getMdp(), that.getMdp()) &&
                Objects.equals(getIndividu(), that.getIndividu());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getLogin(), getMdp(), getIndividu());
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", mdp='" + mdp + '\'' +
                ", individu=" + individu +
                '}';
    }
}
