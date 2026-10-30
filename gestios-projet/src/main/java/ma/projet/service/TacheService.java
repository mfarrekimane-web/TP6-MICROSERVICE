package ma.projet.service;

import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TacheService extends AbstractService<Tache> {

    private static final SimpleDateFormat FMT_COURT = new SimpleDateFormat("dd/MM/yyyy");

    public TacheService() { super(Tache.class); }

    /** Tâches dont le prix > seuil (requête nommée). */
    public void afficherPrixSuperieurA(double seuil) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Tache> taches = s.createNamedQuery("Tache.prixSuperieurA", Tache.class)
                    .setParameter("prix", seuil)
                    .list();
            System.out.println("Tâches dont le prix > " + seuil + " DH :");
            System.out.printf("%-5s %-15s %s%n", "Num", "Nom", "Prix");
            for (Tache t : taches) {
                System.out.printf("%-5d %-15s %.0f DH%n",
                        t.getId(), t.getNom(), t.getPrix());
            }
        }
    }

    /** Tâches réalisées entre deux dates (dates réelles). */
    public void afficherRealiseesEntreDates(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTach> liste = s.createQuery(
                            "select et from EmployeTache et join fetch et.tache "
                                    + "where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2",
                            EmployeTach.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
            System.out.println("Tâches réalisées entre "
                    + FMT_COURT.format(d1) + " et " + FMT_COURT.format(d2) + " :");
            System.out.printf("%-5s %-15s %-20s %s%n",
                    "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            for (EmployeTach et : liste) {
                System.out.printf("%-5d %-15s %-20s %s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        FMT_COURT.format(et.getDateDebutReelle()),
                        FMT_COURT.format(et.getDateFinReelle()));
            }
        }
    }
}