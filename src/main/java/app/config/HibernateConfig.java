
package app.config;

import app.utils.Utils;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public final class HibernateConfig {

    private static volatile EntityManagerFactory emf;

    private HibernateConfig() {}

    public static EntityManagerFactory getEntityManagerFactory() {
        if (Boolean.parseBoolean(System.getenv("PRODUCTION"))) {
            if (emf == null) {
                synchronized (HibernateConfig.class) {
                    if (emf == null) {
                        emf = setupHibernateConfigurationForProduction();
                    }
                }
            }
            return emf;
        }

        return HibernateEmfBuilder.build(buildDevProps());
    }

    private static Properties buildDevProps() {
        Properties props = HibernateBaseProperties.createBase();

        String dbName = Utils.getPropertyValue("DB_NAME", "config.properties");
        String username = Utils.getPropertyValue("DB_USERNAME", "config.properties");
        String password = Utils.getPropertyValue("DB_PASSWORD", "config.properties");

        props.setProperty(
                "hibernate.connection.url",
                "jdbc:postgresql://localhost:5432/" + dbName
        );
        props.setProperty("hibernate.connection.username", username);
        props.setProperty("hibernate.connection.password", password);
        props.setProperty("hibernate.hbm2ddl.auto", "update");

        return props;
    }

    private static EntityManagerFactory setupHibernateConfigurationForProduction() {
        Properties props = HibernateBaseProperties.createBase();

        String dbName = requireEnv("DB_NAME");
        String username = requireEnv("DB_USERNAME");
        String password = requireEnv("DB_PASSWORD");

        props.setProperty(
                "hibernate.connection.url",
                "jdbc:postgresql://db:5432/" + dbName
        );
        props.setProperty("hibernate.connection.username", username);
        props.setProperty("hibernate.connection.password", password);
        props.setProperty("hibernate.hbm2ddl.auto", "update");

        Configuration configuration = new Configuration()
                .addProperties(props);

        EntityRegistry.registerEntities(configuration);

        return configuration.buildSessionFactory();
    }

    private static String requireEnv(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable: " + name
            );
        }

        return value;
    }
}
