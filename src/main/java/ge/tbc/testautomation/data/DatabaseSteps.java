package ge.tbc.testautomation.data;

import io.qameta.allure.Step;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseSteps {
    private Connection connection;
    private Statement statement;

    @Step("Select all rows from RegistrationData")
    public ResultSet selectAllRegistrationData() throws SQLException {
        Properties properties = loadProperties();
        connection = DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));
        statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM RegistrationData");
    }

    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignored) {
        }
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("database.properties was not found on the classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load database.properties", e);
        }
        return properties;
    }
}
