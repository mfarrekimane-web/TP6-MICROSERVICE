package ma.projet;

import ma.projet.beans.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;

public class Test {

    private static final SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat AFF = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws Exception {
        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();

        // ===== Données (insérées une seule fois) =====
        if (hs.getAll().isEmpty()) {
            hs.create(new Homme("SAFI", "SAID", "0600000001", "Casablanca", F.parse("1960-05-10")));
            hs.create(new Homme("ALAMI", "OMAR", "0600000002", "Rabat", F.parse("1965-03-21")));
            hs.create(new Homme("BENANI", "YOUSSEF", "0600000003", "Fès", F.parse("1970-11-02")));
            hs.create(new Homme("TAZI", "KARIM", "0600000004", "Marrakech", F.parse("1958-07-15")));
            hs.create(new Homme("IDRISSI", "HAMZA", "0600000005", "Tanger", F.parse("1972-01-30")));
        }

        if (fs.getAll().isEmpty()) {
            String[][] femmes = {
                    {"RAMI", "SALIMA", "1962-02-14"}, {"ALI", "AMAL", "1968-06-09"},
                    {"ALAOUI", "WAFA", "1975-09-25"}, {"ALAMI", "KARIMA", "1963-12-01"},
                    {"FASSI", "NADIA", "1970-04-17"}, {"BERRADA", "LAILA", "1980-08-08"},
                    {"CHRAIBI", "HOUDA", "1955-10-30"}, {"SEBTI", "ZINEB", "1978-03-03"},
                    {"LAHLOU", "SAMIRA", "1966-07-22"}, {"KADIRI", "IMANE", "1985-05-19"}
            };
            for (String[] t : femmes) {
                fs.create(new Femme(t[0], t[1], "0610000000", "Maroc", F.parse(t[2])));
            }
        }

        if (ms.getAll().isEmpty()) {
            // SAFI SAID (homme 1) : 3 mariages en cours + 1 échoué
            marier(ms, hs.getById(1), fs.getById(1), "1990-09-03", null, 4);          // SALIMA RAMI
            marier(ms, hs.getById(1), fs.getById(2), "1995-09-03", null, 2);          // AMAL ALI
            marier(ms, hs.getById(1), fs.getById(3), "2000-11-04", null, 3);          // WAFA ALAOUI
            marier(ms, hs.getById(1), fs.getById(4), "1989-09-03", "1990-09-03", 0);  // KARIMA ALAMI

            // Femmes mariées plusieurs fois
            marier(ms, hs.getById(2), fs.getById(5), "1992-06-01", "1998-01-01", 1);  // NADIA, 1er
            marier(ms, hs.getById(3), fs.getById(5), "2000-02-02", null, 2);          // NADIA, 2e
            marier(ms, hs.getById(4), fs.getById(1), "1984-01-01", "1989-12-31", 2);  // SALIMA, 2e
            marier(ms, hs.getById(2), fs.getById(6), "2005-05-05", null, 1);          // LAILA
        }

        // ===== 1. Liste des femmes =====
        System.out.println("\n=== Liste des femmes ===");
        for (Femme fm : fs.getAll()) {
            System.out.println(fm.getId() + " - " + fm.getNom() + " " + fm.getPrenom()
                    + " (née le " + AFF.format(fm.getDateNaissance()) + ")");
        }

        // ===== 2. Femme la plus âgée =====
        System.out.println("\n=== Femme la plus âgée ===");
        Femme plusAgee = fs.getPlusAgee();
        System.out.println(plusAgee.getPrenom() + " " + plusAgee.getNom()
                + " (née le " + AFF.format(plusAgee.getDateNaissance()) + ")");

        // ===== 3. Mariages d'un homme avec détails =====
        System.out.println("\n=== Mariages de l'homme 1 ===");
        hs.afficherMariages(1);

        // ===== Prochaines étapes : les sections suivantes seront ajoutées ici =====    }
    }
    private static void marier(MariageService ms, Homme h, Femme fm,
                               String debut, String fin, int nbEnfants) throws Exception {
        ms.create(new Mariage(h, fm, F.parse(debut),
                fin == null ? null : F.parse(fin), nbEnfants));
    }
}