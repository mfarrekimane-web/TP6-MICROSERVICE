package ma.projet.util;

import ma.projet.classes.*;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
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
                    .addAnnotatedClass(Categorie.class)
                    .addAnnotatedClass(Produit.class)
                    .addAnnotatedClass(Commande.class)
                    .addAnnotatedClass(LigneCommandeProduit.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}