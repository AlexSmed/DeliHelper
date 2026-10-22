
package app.config;

import app.utils.Utils;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public final class HibernateConfig {

    private static volatile EntityManagerFactory emf;

    private HibernateConfig() {}

    
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

    return new Configuration()
            .addProperties(props)
            .buildSessionFactory();
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
        Properties props = new Properties();

        props.setProperty(
                "hibernate.connection.url",
                requireEnv("JDBC_DATABASE_URL")
        );
        props.setProperty(
                "hibernate.connection.username",
                requireEnv("JDBC_DATABASE_USERNAME")
        );
        props.setProperty(
                "hibernate.connection.password",
                requireEnv("JDBC_DATABASE_PASSWORD")
        );

        // Add your project's other Hibernate settings and entity mappings
        // here, or use your existing shared Hibernate configuration builder.
        return new Configuration()
                .addProperties(props)
                .buildSessionFactory();
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
