package ma.projet;

import ma.projet.util.HibernateUtil;

public class TestConnexion {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
        System.out.println("Base générée avec succès");
        HibernateUtil.getSessionFactory().close();
    }
}