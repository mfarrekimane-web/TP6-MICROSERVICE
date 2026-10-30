package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService ls = new LigneCommandeService();

        // Données
        Categorie c1 = new Categorie("INFO", "Informatique");
        Categorie c2 = new Categorie("ACC", "Accessoires");
        cs.create(c1);
        cs.create(c2);

        Produit p1 = new Produit("ES12", 120, c1);
        Produit p2 = new Produit("ZR85", 100, c1);
        Produit p3 = new Produit("EE85", 200, c2);
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);


        Commande cmd = new Commande(f.parse("2013-03-14"));
        cms.create(cmd);

        ls.create(new LigneCommandeProduit(p1, cmd, 7));
        ls.create(new LigneCommandeProduit(p2, cmd, 14));
        ls.create(new LigneCommandeProduit(p3, cmd, 5));

        // 1. Produits par catégorie
        System.out.println("\n=== Produits par catégorie ===");
        ps.afficherParCategorie(c1);

        // 2. Produits commandés entre deux dates
        System.out.println("\n=== Produits commandés entre deux dates ===");
        ps.afficherEntreDates(f.parse("2013-01-01"), f.parse("2013-12-31"));

        // 3. Produits d'une commande
        System.out.println("\n=== Produits d'une commande ===");
        ps.afficherParCommande(cmd.getId());

        // 4. Requête nommée : prix > 100 DH
        System.out.println("\n=== Produits dont le prix > 100 DH ===");
        for (Produit p : ps.getPrixSuperieurA(100)) {
            System.out.printf("%s  %.0f DH%n", p.getReference(), p.getPrix());
        }
    }
}