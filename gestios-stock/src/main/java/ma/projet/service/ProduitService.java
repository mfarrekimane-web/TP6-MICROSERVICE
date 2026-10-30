package ma.projet.service;

import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProduitService extends AbstractService<Produit> {

    private static final SimpleDateFormat FMT =
            new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

    public ProduitService() { super(Produit.class); }

    /** Produits d'une catégorie donnée. */
    public List<Produit> getByCategorie(Categorie categorie) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "from Produit p where p.categorie.id = :id", Produit.class)
                    .setParameter("id", categorie.getId())
                    .list();
        }
    }

    public void afficherParCategorie(Categorie categorie) {
        System.out.println("Catégorie : " + categorie.getLibelle());
        System.out.println("Référence   Prix");
        for (Produit p : getByCategorie(categorie)) {
            System.out.printf("%-11s %.0f DH%n", p.getReference(), p.getPrix());
        }
    }

    /** Produits commandés entre deux dates. */
    public void afficherEntreDates(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<LigneCommandeProduit> lignes = s.createQuery(
                            "select l from LigneCommandeProduit l "
                                    + "join fetch l.produit join fetch l.commande c "
                                    + "where c.date between :d1 and :d2 order by c.id",
                            LigneCommandeProduit.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
            int courante = -1;
            for (LigneCommandeProduit l : lignes) {
                Commande c = l.getCommande();
                if (c.getId() != courante) {
                    courante = c.getId();
                    System.out.println("\nCommande : " + c.getId()
                            + "     Date : " + FMT.format(c.getDate()));
                    System.out.println("Référence   Prix    Quantité");
                }
                afficherLigne(l);
            }
        }
    }

    /** Produits d'une commande donnée. */
    public void afficherParCommande(int commandeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Commande c = s.get(Commande.class, commandeId);
            if (c == null) {
                System.out.println("Commande introuvable : " + commandeId);
                return;
            }
            List<LigneCommandeProduit> lignes = s.createQuery(
                            "select l from LigneCommandeProduit l join fetch l.produit "
                                    + "where l.commande.id = :id", LigneCommandeProduit.class)
                    .setParameter("id", commandeId)
                    .list();
            System.out.println("Commande : " + c.getId()
                    + "     Date : " + FMT.format(c.getDate()));
            System.out.println("Liste des produits :");
            System.out.println("Référence   Prix    Quantité");
            for (LigneCommandeProduit l : lignes) afficherLigne(l);
        }
    }

    /** Produits dont le prix > seuil, via requête nommée. */
    public List<Produit> getPrixSuperieurA(float seuil) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Produit.prixSuperieurA", Produit.class)
                    .setParameter("prix", seuil)
                    .list();
        }
    }

    private void afficherLigne(LigneCommandeProduit l) {
        System.out.printf("%-11s %-7s %d%n",
                l.getProduit().getReference(),
                String.format("%.0f DH", l.getProduit().getPrix()),
                l.getQuantite());
    }
}