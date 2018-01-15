package fr.laerce.gestionstages.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Projet pfmp-base
 * Pour LAERCE SAS
 * <p>
 * Créé le  11/01/2018.
 *
 * @author fred
 */
@Entity
@Table(name="PROFESSEURS")
public class Professeur {
    @Id
    @Column(name="ID", nullable = false)
    private Long id;
    @Column
    private String nom;
    @Column
    private String prenom;
    @Column
    private String login;
    @Column
    private String mdp;
    @Column
    private LocalDate naissance;
    @Column
    private String codeSynchro;
    @Column
    private String telephoneMobile;
    @Column
    private String telephoneFixe;
    @Column
    private String email;


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

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professeur that = (Professeur) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getNom(), that.getNom()) &&
                Objects.equals(getPrenom(), that.getPrenom()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getMdp(), that.getMdp()) &&
                Objects.equals(getNaissance(), that.getNaissance()) &&
                Objects.equals(getCodeSynchro(), that.getCodeSynchro()) &&
                Objects.equals(getTelephoneMobile(), that.getTelephoneMobile()) &&
                Objects.equals(getTelephoneFixe(), that.getTelephoneFixe()) &&
                Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getNom(), getPrenom(), getLogin(), getMdp(), getNaissance(), getCodeSynchro(), getTelephoneMobile(), getTelephoneFixe(), getEmail());
    }

    @Override
    public String toString() {
        return "Professeur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", login='" + login + '\'' +
                ", mdp='" + mdp + '\'' +
                ", naissance=" + naissance +
                ", codeSynchro='" + codeSynchro + '\'' +
                ", telephoneMobile='" + telephoneMobile + '\'' +
                ", telephoneFixe='" + telephoneFixe + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
