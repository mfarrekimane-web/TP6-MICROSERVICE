package ma.projet.classes;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String code ;

    private String libelle;

    @OneToMany(mappedBy = "categorie")
    private List <Produit> produits = new ArrayList<>();

    public Categorie (){}

    public Categorie (String code , String libelle ){
        this.code = code ;
        this.libelle = libelle ;
    }

    public long  getId() {return id ; }
    public String getCode() {return code ;}
    public String getLibelle(){ return libelle ; }

    public void setId(Long id ){this.id=id ;}
    public void setCode(String code ){this.code=code ;}
    public void setLibelle(String libelle){this.libelle=libelle ;}

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", produits=" + produits +
                '}';
    }
}
