package fr.laerce.gestionstages.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Projet pfmp-base
 * Pour LAERCE SAS
 * <p>
 * Créé le  11/01/2018.
 *
 * @author fred
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Individu implements Cloneable {

    @Column(nullable = false)
    String civilite;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column
    private String nom;
    @Column
    private String prenom;
    @Column
    private String codeSynchro;
    @Column
    private String telephoneMobile;
    @Column
    private String telephoneFixe;
    @Column
    private String email;
    @Column(unique = true)
    private String login;
    @Column
    private String mdp;
    @Column
    private String mdpOrigine;




    public Individu() {
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public String getCodeSynchro() {
        return codeSynchro;
    }

    public void setCodeSynchro(String codeSynchro) {
        this.codeSynchro = codeSynchro;
    }

    public String getTelephoneMobile() {
        return telephoneMobile;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public String getTelephoneFixe() {
        return telephoneFixe;
    }

    public void setTelephoneFixe(String telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individu that = (Individu) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getNom(), that.getNom()) &&
                Objects.equals(getPrenom(), that.getPrenom()) &&

                Objects.equals(getCodeSynchro(), that.getCodeSynchro()) &&
                Objects.equals(getTelephoneMobile(), that.getTelephoneMobile()) &&
                Objects.equals(getTelephoneFixe(), that.getTelephoneFixe()) &&
                Objects.equals(getEmail(), that.getEmail());
    }



    @Override
    public int hashCode() {

        return Objects.hash(getId(), getNom(), getPrenom(),  getCodeSynchro(), getTelephoneMobile(), getTelephoneFixe(), getEmail());
    }

    @Override
    public String toString() {
        return "Individu{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +

                ", codeSynchro='" + codeSynchro + '\'' +
                ", telephoneMobile='" + telephoneMobile + '\'' +
                ", telephoneFixe='" + telephoneFixe + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
