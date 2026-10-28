package app.dao;

import app.config.HibernateConfig;
import app.entities.Bulletin;
import app.entities.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class BulletinDAO {

    private final EntityManagerFactory emf;

    public BulletinDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Bulletin save(Bulletin bulletin)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(bulletin);
        em.getTransaction().commit();
        em.close();
        return bulletin;
    }

    public Bulletin getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Bulletin.class, id);
        }
    }

    public List<Bulletin> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                    "SELECT b FROM Bulletin b",
                    Bulletin.class
            ).getResultList();
        }
    }

    public Bulletin findByTitle(String title)
    {
        EntityManager em = emf.createEntityManager();
        Bulletin foundBulletin = em.find(Bulletin.class, title);
        em.close();
        return foundBulletin;
    }

    public Bulletin update(Bulletin bulletin)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Bulletin updatedBulletin = em.merge(bulletin);
        em.getTransaction().commit();
        em.close();
        return updatedBulletin;
    }


    public void delete(long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Products product = em.find(Products.class, id);

            if (product != null) {
                em.remove(product);
            }

            em.getTransaction().commit();
        }
    }


    public void close()
    {
        emf.close();
    }
}


