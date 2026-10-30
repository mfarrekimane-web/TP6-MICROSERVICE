package ma.projet.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String telephone;

    @OneToMany(mappedBy = "chef")
    private List<Projet> projets = new ArrayList<>();

    @OneToMany(mappedBy = "employe")
    private List<EmployeTach> employeTaches = new ArrayList<>();

    public Employe() {}
    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public List<Projet> getProjets() { return projets; }
    public void setProjets(List<Projet> projets) { this.projets = projets; }
    public List<EmployeTach> getEmployeTaches() { return employeTaches; }
    public void setEmployeTaches(List<EmployeTach> employeTaches) { this.employeTaches = employeTaches; }
}