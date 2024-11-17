package org.BackEnd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:h2:C:\\Users\\jakmi\\Documents\\GitHub\\Lab4/default";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public static Connection GetConnection() {
        Connection connection = null;
        try {
            // Load the H2 database driver
            Class.forName("org.h2.Driver");
            // Establish a connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("H2 Driver not found. Please add the H2 dependency.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
        }
        return connection;
    }
}

