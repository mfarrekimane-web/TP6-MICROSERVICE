package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;

public class Test {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        // Employés
        Employe e1 = new Employe("Alami", "Karim", "0600000001");
        Employe e2 = new Employe("Benani", "Sara", "0600000002");
        es.create(e1);
        es.create(e2);

        // Projet (chef = e1)
        Projet p = new Projet("Gestion de stock",
                f.parse("2013-01-14"), f.parse("2013-06-14"), e1);
        ps.create(p);

        // Tâches
        Tache t1 = new Tache("Analyse", f.parse("2013-02-10"), f.parse("2013-02-20"), 1200, p);
        Tache t2 = new Tache("Conception", f.parse("2013-03-10"), f.parse("2013-03-15"), 800, p);
        Tache t3 = new Tache("Développement", f.parse("2013-04-10"), f.parse("2013-04-30"), 5000, p);
        ts.create(t1);
        ts.create(t2);
        ts.create(t3);

        // Réalisations (dates réelles)
        ets.create(new EmployeTach(e1, t1, f.parse("2013-02-10"), f.parse("2013-02-20")));
        ets.create(new EmployeTach(e2, t2, f.parse("2013-03-10"), f.parse("2013-03-15")));
        ets.create(new EmployeTach(e2, t3, f.parse("2013-04-10"), f.parse("2013-04-25")));

        System.out.println("\n=== Tâches réalisées par l'employé 2 ===");
        es.afficherTachesRealisees(e2.getId());

        System.out.println("\n=== Projets gérés par l'employé 1 ===");
        es.afficherProjetsGeres(e1.getId());

        System.out.println("\n=== Tâches planifiées du projet ===");
        ps.afficherTachesPlanifiees(p.getId());

        System.out.println("\n=== Tâches réalisées du projet (dates réelles) ===");
        ps.afficherTachesRealisees(p.getId());

        System.out.println("\n=== Tâches dont le prix > 1000 DH ===");
        ts.afficherPrixSuperieurA(1000);

        System.out.println("\n=== Tâches réalisées entre deux dates ===");
        ts.afficherRealiseesEntreDates(f.parse("2013-02-01"), f.parse("2013-03-31"));
    }
}