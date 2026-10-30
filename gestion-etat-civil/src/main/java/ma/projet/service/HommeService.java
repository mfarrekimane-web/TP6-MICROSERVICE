package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;

public class HommeService extends AbstractService<Homme> {

    private static final SimpleDateFormat AFF = new SimpleDateFormat("dd/MM/yyyy");

    public HommeService() { super(Homme.class); }

    /** Mariages d'un homme : en cours (dateFin null) puis échoués. */
    public void afficherMariages(int hommeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Homme h = s.get(Homme.class, hommeId);
            if (h == null) {
                System.out.println("Homme introuvable : " + hommeId);
                return;
            }
            List<Mariage> mariages = s.createQuery(
                            "select m from Mariage m join fetch m.femme "
                                    + "where m.homme.id = :id order by m.dateDebut", Mariage.class)
                    .setParameter("id", hommeId)
                    .list();

            System.out.println("Nom : " + h.getNom() + " " + h.getPrenom());

            System.out.println("Mariages En Cours :");
            int i = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    System.out.printf("%d. Femme : %-14s Date Début : %s    Nbr Enfants : %d%n",
                            i++, nomFemme(m), AFF.format(m.getDateDebut()), m.getNbrEnfant());
                }
            }

            System.out.println("\nMariages échoués :");
            i = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    System.out.printf("%d. Femme : %-14s Date Début : %s%n",
                            i++, nomFemme(m), AFF.format(m.getDateDebut()));
                    System.out.printf("Date Fin : %s    Nbr Enfants : %d%n",
                            AFF.format(m.getDateFin()), m.getNbrEnfant());
                }
            }
        }
    }

    private String nomFemme(Mariage m) {
        return m.getFemme().getPrenom() + " " + m.getFemme().getNom();
    }
}