package ma.projet.classes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "EmployeTache")

@IdClass(EmployeTach.PK.class)

public class EmployeTach {

    @Id
    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @Id
    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    public EmployeTach() {}
    public EmployeTach(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }
    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }
    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date dateFinReelle) { this.dateFinReelle = dateFinReelle; }

    // Clé composée (noms et types = attributs @Id ci-dessus, avec l'id en int)
    public static class PK implements Serializable {
        private int employe;
        private int tache;

        public PK() {}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PK)) return false;
            PK that = (PK) o;
            return employe == that.employe && tache == that.tache;
        }

        @Override
        public int hashCode() { return Objects.hash(employe, tache); }
    }
}