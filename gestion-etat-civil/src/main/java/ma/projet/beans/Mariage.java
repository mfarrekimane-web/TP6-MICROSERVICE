package ma.projet.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(Mariage.PK.class)
public class Mariage {

    @Id
    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @Id
    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private int nbrEnfant;

    public Mariage() {}
    public Mariage(Homme homme, Femme femme, Date dateDebut,
                   Date dateFin, int nbrEnfant) {
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
    }

    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }

    // Clé composée : noms = attributs @Id, types = type de l'id (int)
    public static class PK implements Serializable {
        private int homme;
        private int femme;

        public PK() {}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PK)) return false;
            PK that = (PK) o;
            return homme == that.homme && femme == that.femme;
        }

        @Override
        public int hashCode() { return Objects.hash(homme, femme); }
    }
}