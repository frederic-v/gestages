package fr.laerce.gestionstages.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Projet pfmp-base
 * Pour LAERCE SAS
 * <p>
 * Créé le  11/01/2018.
 *
 * @author fred
 */
@Entity
public class Individu implements Cloneable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;
  @Column
  private String nom;
  @Column
  private String prenom;

  @Column(nullable = false)
  String civilite;

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

  @OneToOne(mappedBy = "individu")
  private Utilisateur utilisateur;

  @ManyToMany
  private Set<Division> divisions;

  @ManyToMany
  private Set<Discipline> disciplines;


  public Individu(){
    this.divisions = new HashSet<>();
    this.disciplines = new HashSet<>();
  }

  public Set<Discipline> getDisciplines() {
    return disciplines;
  }

  public void setDisciplines(Set<Discipline> disciplines) {
    this.disciplines = disciplines;
  }

  public void addDiscipline(Discipline discipline) {
    if (this.disciplines != null) {
      this.disciplines.remove(discipline);
    }
    this.disciplines.add(discipline);
    // gestion du lien inverse
    discipline.addProfesseur(this);
  }


  public Set<Division> getDivisions() {
    return divisions;
  }

  public void setDivisions(Set<Division> divisions) {
    this.divisions = divisions;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
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
            Objects.equals(getNaissance(), that.getNaissance()) &&
            Objects.equals(getCodeSynchro(), that.getCodeSynchro()) &&
            Objects.equals(getTelephoneMobile(), that.getTelephoneMobile()) &&
            Objects.equals(getTelephoneFixe(), that.getTelephoneFixe()) &&
            Objects.equals(getEmail(), that.getEmail());
  }


  public void addDivision(Division division) {
    this.divisions.add(division);
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(), getNom(), getPrenom(), getNaissance(), getCodeSynchro(), getTelephoneMobile(), getTelephoneFixe(), getEmail());
  }

  @Override
  public String toString() {
    return "Individu{" +
            "id=" + id +
            ", nom='" + nom + '\'' +
            ", prenom='" + prenom + '\'' +
            ", naissance=" + naissance +
            ", codeSynchro='" + codeSynchro + '\'' +
            ", telephoneMobile='" + telephoneMobile + '\'' +
            ", telephoneFixe='" + telephoneFixe + '\'' +
            ", email='" + email + '\'' +
            '}';
  }

}
