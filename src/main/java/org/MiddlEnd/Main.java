package org.MiddlEnd;

import org.BackEnd.DatabaseConnection;
import org.BackEnd.QueryOperations;
import org.UI.ApplicationStart;
import org.UI.Controller;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            Controller.connection = DatabaseConnection.GetConnection();
            QueryOperations.CheckIfTablesExists(Controller.connection);
            ApplicationStart.StartWindowApp();
            
        } catch (SQLException e) {
            System.out.println("Something went wrong with connection to database");
            e.printStackTrace();
        }
    }
}


