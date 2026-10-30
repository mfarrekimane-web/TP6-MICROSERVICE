package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme")
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {}
    public Femme(String nom, String prenom, String telephone,
                 String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}