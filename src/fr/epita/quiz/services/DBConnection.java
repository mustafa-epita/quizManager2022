package fr.epita.quiz.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public java.sql.Connection GetDBConnection() throws SQLException {
        Properties properties = getProperties();
        if (properties == null) return null;
        String dbURL = properties.getProperty("dburl");
        String dbUsername = properties.getProperty("dbusername");
        String dbPassword = properties.getProperty("dbpassword");
        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }

    private java.util.Properties getProperties() {
        java.util.Properties properties = new java.util.Properties();
        try {
            properties.load(new FileInputStream("./credentials.properties"));
        } catch (IOException e) {
            System.out.println("Sorry, the program is not finding the required files, check your setup " +
                    "(authentication is not possible)");
            return null;
        }
        return properties;
    }
}
