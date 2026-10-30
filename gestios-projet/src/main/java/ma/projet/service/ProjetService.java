package ma.projet.service;

import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProjetService extends AbstractService<Projet> {

    private static final SimpleDateFormat FMT_COURT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat FMT_LONG = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

    public ProjetService() { super(Projet.class); }

    private void afficherEntete(Projet p) {
        System.out.println("Projet : " + p.getId()
                + "      Nom : " + p.getNom()
                + "     Date début : " + FMT_LONG.format(p.getDateDebut()));
    }

    /** Tâches planifiées pour un projet. */
    public void afficherTachesPlanifiees(int projetId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Projet p = s.get(Projet.class, projetId);
            if (p == null) {
                System.out.println("Projet introuvable : " + projetId);
                return;
            }
            List<Tache> taches = s.createQuery(
                            "from Tache t where t.projet.id = :id", Tache.class)
                    .setParameter("id", projetId)
                    .list();
            afficherEntete(p);
            System.out.println("Liste des tâches planifiées :");
            System.out.printf("%-5s %-15s %-15s %s%n",
                    "Num", "Nom", "Date Début", "Date Fin");
            for (Tache t : taches) {
                System.out.printf("%-5d %-15s %-15s %s%n",
                        t.getId(), t.getNom(),
                        FMT_COURT.format(t.getDateDebut()),
                        FMT_COURT.format(t.getDateFin()));
            }
        }
    }

    /** Tâches réalisées avec les dates réelles. */
    public void afficherTachesRealisees(int projetId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Projet p = s.get(Projet.class, projetId);
            if (p == null) {
                System.out.println("Projet introuvable : " + projetId);
                return;
            }
            List<EmployeTach> liste = s.createQuery(
                            "select et from EmployeTache et join fetch et.tache t "
                                    + "where t.projet.id = :id order by t.id", EmployeTach.class)
                    .setParameter("id", projetId)
                    .list();
            afficherEntete(p);
            System.out.println("Liste des tâches:");
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