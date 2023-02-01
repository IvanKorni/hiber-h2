package hiber.service;

import hiber.entity.Worker;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateServiceTest {

    @Test
    void testSaveAndGetAllWithoutException() {
        HibernateService h = new HibernateService();
        Worker worker = new Worker("Ivan", "Kornilov");
        h.save(worker);

        List<Worker> workers = h.getAll();
        assertEquals("Ivan", workers.get(0).getFirstName());
    }
}