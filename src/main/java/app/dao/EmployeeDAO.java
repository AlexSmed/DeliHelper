package app.dao;

import app.config.HibernateConfig;
import app.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EmployeeDAO {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public Employee save(Employee employee)
    {
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();

            em.persist(employee);
            em.getTransaction().commit();

            return employee;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            em.close();
        }
    }

    public Employee findById(int id)
    {
        EntityManager em = emf.createEntityManager();
        Employee foundEmployee = em.find(Employee.class, id);
        em.close();
        return foundEmployee;
    }

    public Employee findByUsername(String username) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT e FROM Employee e WHERE e.username = :username",
                            Employee.class)
                    .setParameter("username", username)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public Employee update(Employee employee)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Employee updatedEmployee = em.merge(employee);
        em.getTransaction().commit();
        em.close();
        return updatedEmployee;
    }

    public void delete(int id)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Employee employee = findById(id);
        if (employee != null)
        {
            em.remove(employee);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void close()
    {
        emf.close();
    }
}

