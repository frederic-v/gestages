package fr.laerce.gestionstages.domain;

import javax.persistence.*;
import java.util.List;
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
public class Division {
    @Id
    private Long id;
    @Column
    private String code;
    @Column
    private String libelle;
    @ManyToOne
    private Niveau niveau;

    @ManyToMany
    private List<Individu> professeurs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }


    public List<Individu> getProfesseurs() {
        return professeurs;
    }

    public void setProfesseurs(List<Individu> professeurs) {
        this.professeurs = professeurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return Objects.equals(getId(), division.getId()) &&
                Objects.equals(getCode(), division.getCode()) &&
                Objects.equals(getLibelle(), division.getLibelle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getCode(), getLibelle());
    }

    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }

}
