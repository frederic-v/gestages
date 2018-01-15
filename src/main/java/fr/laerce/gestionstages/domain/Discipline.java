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
@Table(name="competences")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="code", length = 20, nullable = false, unique = true)
    private String code;
    @Column(name="libelle", length = 50)
    private String libelle;



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getLibelle(), that.getLibelle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getCode(), getLibelle());
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
