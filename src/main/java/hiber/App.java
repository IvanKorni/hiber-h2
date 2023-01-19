package hiber;

import java.util.List;

import hiber.entity.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Worker worker = new Worker("Ivan", "Kornilov");
        Worker worker1 = new Worker("Lena", "Ivanova");

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            //открываем транзакцию
            transaction = session.beginTransaction();
            //пихаем в сессию воркеров
            session.save(worker);
            session.save(worker1);
            //коммитим
            transaction.commit();
        } catch (Exception e) {
            // если возник какой-то эксепш, например сессия прервалась, но в транзакцию уже что-то пихнули, откатываем ее
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = sessionFactory.openSession()) {
            // делаем select с помощью createQuery
            List<Worker> workers = session.createQuery("from Worker", Worker.class).list();
            // выводим воркеров в консоль
            workers.forEach(s -> System.out.println(s.toString()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}