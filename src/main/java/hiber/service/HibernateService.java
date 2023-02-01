package hiber.service;

import hiber.entity.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateService {
    private Transaction transaction;
    private final SessionFactory sessionFactory;

    public HibernateService() {
        this.transaction = null;
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void save(Worker worker) {
        try (Session session = sessionFactory.openSession()) {
            //открываем транзакцию
            transaction = session.beginTransaction();
            //пихаем в сессию воркеров
            session.save(worker);
            //коммитим
            transaction.commit();
        } catch (Exception e) {
            // если возник какой-то эксепш, например сессия прервалась, но в транзакцию уже что-то пихнули, откатываем ее
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Worker> getAll() {
        try (Session session = sessionFactory.openSession()) {
            // делаем select с помощью createQuery
            return session.createQuery("from Worker", Worker.class).list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
