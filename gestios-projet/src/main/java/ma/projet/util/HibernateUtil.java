package ma.projet.util;

import ma.projet.classes.Projet;
import ma.projet.classes.EmployeTach;
import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = build();

    private static SessionFactory build() {
        try (InputStream in = HibernateUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            Properties props = new Properties();
            props.load(in);
            return new Configuration()
                    .setProperties(props)
                    .addAnnotatedClass(Projet.class)
                    .addAnnotatedClass(Employe.class)
                    .addAnnotatedClass(Tache.class)
                    .addAnnotatedClass(EmployeTach.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}