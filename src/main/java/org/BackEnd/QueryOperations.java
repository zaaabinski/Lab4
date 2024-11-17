package org.BackEnd;

import org.SubClasses.Ingredient;

import java.sql.*;
import java.util.ArrayList;

public class QueryOperations {

    public static void CheckIfTablesExists(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String productQuery = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME ='PRODUCTS'";

        ResultSet productSet = statement.executeQuery(productQuery);

        if (productSet.next()) {
            int count = productSet.getInt(1);
            if (count <= 0) {
                CreateProductsTable(connection);
                System.out.println("Product table created");
            }
        }

        String mealsQuery = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME ='MEALS'";

        ResultSet mealsSet = statement.executeQuery(mealsQuery);

        if (mealsSet.next()) {
            int count = mealsSet.getInt(1);
            if (count <= 0) {
                CreateMealsTable(connection);
                System.out.println("Meals table created");
            }
        }
    }

    public static void CreateProductsTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String TableProduct = "CREATE TABLE PRODUCTS (" +
                "productName text PRIMARY KEY, " +
                "carbs int, " +
                "protein int, " +
                "fats int,"+
                "category text)";
        statement.executeUpdate(TableProduct);
    }

    public static void CreateMealsTable(Connection connection) throws SQLException
    {
        Statement statement = connection.createStatement();
        String TableMeals = "CREATE TABLE MEALS (" +
                "mealID text PRIMARY KEY, " +
                "productName text, " +
                "productWeight int, " +
                "mealCategory text, "+
                "FOREIGN KEY (productName) REFERENCES PRODUCTS(productName) ON DELETE RESTRICT ON UPDATE CASCADE)";

        statement.executeUpdate(TableMeals);
    }

    public static void AddProductToBase(String foodName, int carbs,int protein, int fats, String category, Connection currentConnection) throws SQLException {
        String addProduct = "INSERT INTO PRODUCTS () VALUES (?,?,?,?,?)";
        PreparedStatement statement = currentConnection.prepareStatement(addProduct);
        statement.setString(1, foodName);
        statement.setInt(2, carbs);
        statement.setInt(3, protein);
        statement.setInt(4, fats);
        statement.setString(5, category);
        statement.executeUpdate();
    }

    public static void AddMealToBase(ArrayList<Ingredient> meal , Connection currentConnection) throws SQLException {
        for(Ingredient ingredient : meal)
        {
            String addMeal = "INSERT INTO MEALS () VALUES (?,?,?,?)";
            PreparedStatement statement = currentConnection.prepareStatement(addMeal);
            statement.setString(1,ingredient.getMealId());
            statement.setString(2,ingredient.getProductName());
            statement.setInt(3,ingredient.getProductWeight());
            statement.setString(4,ingredient.getMealCategory());
            statement.executeUpdate();
        }
    }


    public static ArrayList<String> ShowBase(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUCTS");
        ResultSet rs = statement.executeQuery();
        ArrayList<String> Baselines = new ArrayList<>();

        return Baselines;
    }

    public static void DeleteMeal(String mealID , Connection connection) throws SQLException {
        String deleteSQL = "DELETE FROM MEALS WHERE mealID = ?";
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.setString(1, mealID);
        statement.executeUpdate();
    }

    public static void DeleteProduct(String mealID , Connection connection) throws SQLException {
        String deleteSQL = "DELETE FROM PRODUCTS WHERE productName = ?";
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.setString(1, mealID);
        statement.executeUpdate();
    }

    /*public static void EditProduct(String productName, Connection connection) throws SQLException {
        String editProduct = "UPDATE PRODUCTS SET" +
                "carbs = ?, " +
                "protein = ?, " +
                "fats = ?, " +
                "category = ? " +
                "WHERE productName = ?";
        PreparedStatement statement = connection.prepareStatement(editProduct);
        statement.setInt(1, carbs);
        statement.setInt(2, protein);
        statement.setInt(3, fats);
        statement.setString(4, category);
        statement.setString(5, productName);
    }*/


}
