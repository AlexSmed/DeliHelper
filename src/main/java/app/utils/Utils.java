package app.utils;

import app.exceptions.ApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    public static String getPropertyValue(String key, String fileName) {
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        Properties properties = new Properties();

        try (InputStream input = Utils.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Could not find " + fileName);
            }

            properties.load(input);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read " + fileName, e);
        }
    }
}