
package dao;

import app.config.HibernateConfig;
import app.dao.ProductsDAO;
import app.entities.Products;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductsDAOTest {

    private static EntityManagerFactory emf;
    private static ProductsDAO dao;

    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactory();
        dao = new ProductsDAO(emf);
    }

    @AfterAll
    static void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private Products createProduct(String category) {
        Products product = new Products();
        product.setName("Test-" + UUID.randomUUID());
        product.setPicture("test-picture.jpg");
        product.setPrice("19.99");
        product.setRecipe("Test recipe");
        product.setAllergies("None");
        product.setIngredients("Test ingredients");
        product.setCategory(category);
        product.setLastUse("2026-12-31");
        product.setStore_id(1);
        product.setPlu(12345);
        return product;
    }

    @Test
    void createShouldPersistProduct() {
        Products product = createProduct("Fruit");

        dao.create(product);

        assertTrue(product.getId() > 0);
        assertNotNull(dao.getById(product.getId()));
    }

    @Test
    void saveShouldPersistAndReturnProduct() {
        Products product = createProduct("Bakery");

        Products saved = dao.save(product);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals(product.getName(),
                dao.getById(saved.getId()).getName());
    }

    @Test
    void findByNameShouldReturnMatchingProduct() {
        Products product = dao.save(createProduct("Drinks"));

        Products found = dao.findByName(product.getName());

        assertNotNull(found);
        assertEquals(product.getName(), found.getName());
    }

    @Test
    void findByNameShouldReturnNullWhenNotFound() {
        Products found = dao.findByName("Missing-" + UUID.randomUUID());

        assertNull(found);
    }

    @Test
    void findByCategoryShouldReturnMatchingProduct() {
        String category = "Category-" + UUID.randomUUID();
        Products product = dao.save(createProduct(category));

        Products found = dao.findByCategory(category);

        assertNotNull(found);
        assertEquals(category, found.getCategory());
    }

    @Test
    void findByCategoryShouldReturnNullWhenNotFound() {
        Products found =
                dao.findByCategory("Missing-" + UUID.randomUUID());

        assertNull(found);
    }

    @Test
    void getByIdShouldReturnSavedProduct() {
        Products product = dao.save(createProduct("Frozen"));

        Products found = dao.getById(product.getId());

        assertNotNull(found);
        assertEquals(product.getId(), found.getId());
    }

    @Test
    void getAllShouldContainSavedProduct() {
        Products product = dao.save(createProduct("Vegetables"));

        List<Products> products = dao.getAll();

        assertNotNull(products);
        assertTrue(products.stream().anyMatch(
                p -> p.getId() == product.getId()
        ));
    }

    @Test
    void updateShouldPersistChanges() {
        Products product = dao.save(createProduct("Fruit"));
        product.setCategory("Updated-" + UUID.randomUUID());
        product.setPrice("29.99");

        Products updated = dao.update(product);
        Products found = dao.getById(updated.getId());

        assertNotNull(found);
        assertEquals(product.getCategory(), found.getCategory());
        assertEquals("29.99", found.getPrice());
    }

    @Test
    void deleteShouldRemoveProduct() {
        Products product = dao.save(createProduct("Snacks"));
        long id = product.getId();

        dao.delete(id);

        assertNull(dao.getById(id));
    }

    @Test
    void deleteShouldNotFailWhenProductDoesNotExist() {
        assertDoesNotThrow(() -> dao.delete(-999999L));
    }
}
