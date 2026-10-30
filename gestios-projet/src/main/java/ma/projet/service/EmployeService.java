package ma.projet.service;

import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EmployeService extends AbstractService<Employe> {

    private static final SimpleDateFormat FMT_COURT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat FMT_LONG = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

    public EmployeService() { super(Employe.class); }

    /** Tâches réalisées par un employé. */
    public void afficherTachesRealisees(int employeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Employe e = s.get(Employe.class, employeId);
            if (e == null) {
                System.out.println("Employé introuvable : " + employeId);
                return;
            }
            List<EmployeTach> liste = s.createQuery(
                            "select et from EmployeTache et join fetch et.tache "
                                    + "where et.employe.id = :id", EmployeTach.class)
                    .setParameter("id", employeId)
                    .list();
            System.out.println("Employé : " + e.getNom() + " " + e.getPrenom());
            System.out.println("Liste des tâches réalisées :");
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

    /** Projets gérés par un employé (chef de projet). */
    public void afficherProjetsGeres(int employeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Employe e = s.get(Employe.class, employeId);
            if (e == null) {
                System.out.println("Employé introuvable : " + employeId);
                return;
            }
            List<Projet> projets = s.createQuery(
                            "from Projet p where p.chef.id = :id", Projet.class)
                    .setParameter("id", employeId)
                    .list();
            System.out.println("Projets gérés par " + e.getNom() + " " + e.getPrenom() + " :");
            System.out.printf("%-5s %-20s %s%n", "Num", "Nom", "Date début");
            for (Projet p : projets) {
                System.out.printf("%-5d %-20s %s%n",
                        p.getId(), p.getNom(), FMT_LONG.format(p.getDateDebut()));
            }
        }
    }
}