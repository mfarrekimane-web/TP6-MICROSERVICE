package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractService<T> implements IDao<T> {
    private final Class<T> type;

    protected AbstractService(Class<T> type) {
        this.type = type;
    }

    private boolean execute(Consumer<Session> action) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            action.accept(s);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override public boolean create(T o) { return execute(s -> s.save(o)); }
    @Override public boolean update(T o) { return execute(s -> s.update(o)); }
    @Override public boolean delete(T o) { return execute(s -> s.delete(o)); }

    @Override
    public T getById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(type, id);
        }
    }

    @Override
    public List<T> getAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from " + type.getSimpleName(), type).list();
        }
    }
}