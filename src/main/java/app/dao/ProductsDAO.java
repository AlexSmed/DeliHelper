package app.dao;

import app.config.HibernateConfig;
import app.entities.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ProductsDAO {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public ProductsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //-------------------------------------------------\\
    // Posibably the same mothods just a bit different \\
    public void create(Products products) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(products);
            em.getTransaction().commit();
        }
    }

    public Products save(Products products)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(products);
        em.getTransaction().commit();
        em.close();
        return products;
    }

    //--------------\\
    // getting info \\

    public Products findByCategory(String category) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT p FROM Products p WHERE p.category = :category",
                            Products.class
                    )
                    .setParameter("category", category)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public Products findByName(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT p FROM Products p WHERE p.name = :name",
                            Products.class
                    )
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }
    public Products findById(long id)
    {
        EntityManager em = emf.createEntityManager();
        Products foundProducts = em.find(Products.class, id);
        em.close();
        return foundProducts;
    }

    public Products getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Products.class, id);
        }
    }

    public List<Products> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                    "SELECT p FROM Products p",
                    Products.class
            ).getResultList();
        }
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

    public Products update(Products products)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Products updatedProducts = em.merge(products);
        em.getTransaction().commit();
        em.close();
        return updatedProducts;
    }

    public void close()
    {
        emf.close();
    }
}
