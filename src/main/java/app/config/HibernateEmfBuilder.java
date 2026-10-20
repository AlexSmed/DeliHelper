
package app.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

final class HibernateEmfBuilder {

    private HibernateEmfBuilder() {}

    static EntityManagerFactory build(Properties props) {
        StandardServiceRegistry serviceRegistry = null;

        try {
            Configuration configuration = new Configuration();
            configuration.addProperties(props);

            // Register entity classes
            EntityRegistry.registerEntities(configuration);

            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            SessionFactory sessionFactory =
                    configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory.unwrap(EntityManagerFactory.class);

        } catch (Throwable ex) {
            if (serviceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }

            throw new IllegalStateException(
                    "Failed to initialize Hibernate SessionFactory", ex
            );
        }
    }
}
