package org.MiddlEnd;

import org.BackEnd.DatabaseConnection;
import org.BackEnd.QueryOperations;
import org.UI.ApplicationStart;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection;

        try {
            connection = DatabaseConnection.GetConnection();
            QueryOperations.CheckIfTablesExists(connection);
            ApplicationStart.StartWindowApp();
            
        } catch (SQLException e) {
            System.out.println("Something went wrong with connection to database");
            e.printStackTrace();
        }
    }
}


