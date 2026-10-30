package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class FemmeService extends AbstractService<Femme> {
    public FemmeService() { super(Femme.class); }

    /** Femme la plus âgée = date de naissance la plus ancienne. */
    public Femme getPlusAgee() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "from Femme f order by f.dateNaissance asc", Femme.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}