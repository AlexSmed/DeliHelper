package app.dao;

import app.config.HibernateConfig;
import app.entities.Products;
import app.entities.Routines;
import app.entities.Routines;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RoutinesDAO {
    private final EntityManagerFactory emf;

    public RoutinesDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public Routines save(Routines routines)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(routines);
        em.getTransaction().commit();
        em.close();
        return routines;
    }
    public List<Routines> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                    "SELECT r FROM Routines r",
                    Routines.class
            ).getResultList();
        }
    }

    public void delete(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Routines routines = em.find(Routines.class, id);

            if (routines != null) {
                em.remove(routines);
            }

            em.getTransaction().commit();
        }
    }

    public Routines update(Routines Routines)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Routines updatedRoutines = em.merge(Routines);
        em.getTransaction().commit();
        em.close();
        return updatedRoutines;
    }

    public Routines getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Routines.class, id);
        }
    }
}
