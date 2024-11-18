package org.BackEnd;

import org.SubClasses.Ingredient;
import org.SubClasses.Product;

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
                "fats int," +
                "category text)";
        statement.executeUpdate(TableProduct);
    }

    public static void CreateMealsTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String TableMeals = "CREATE TABLE MEALS (" +
                "mealID text, " +
                "productName text, " +
                "productWeight int, " +
                "mealCategory text, " +
                "FOREIGN KEY (productName) REFERENCES PRODUCTS(productName) ON DELETE RESTRICT ON UPDATE CASCADE)";

        statement.executeUpdate(TableMeals);
    }

    public static void AddProductToBase(String foodName, int carbs, int protein, int fats, String category, Connection currentConnection) throws SQLException {
        String addProduct = "INSERT INTO PRODUCTS (productName,carbs,protein,fats,category)  VALUES (?,?,?,?,?)";
        PreparedStatement statement = currentConnection.prepareStatement(addProduct);
        statement.setString(1, foodName);
        statement.setInt(2, carbs);
        statement.setInt(3, protein);
        statement.setInt(4, fats);
        statement.setString(5, category);
        statement.executeUpdate();
    }

    public static void AddMealToBase(ArrayList<Ingredient> meal, Connection currentConnection) throws SQLException {
        for (Ingredient ing : meal) {
            String addMeal = "INSERT INTO MEALS (mealID,productName,productWeight,mealCategory) VALUES (?,?,?,?)";
            PreparedStatement statement = currentConnection.prepareStatement(addMeal);
            statement.setString(1, ing.getMealId());
            statement.setString(2, ing.getProductName());
            statement.setInt(3, ing.getProductWeight());
            statement.setString(4, ing.getMealCategory());
            statement.executeUpdate();
        }
    }


    public static Product SearchForProductToEdit(String productName, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE productName LIKE ?");

        // Set the parameter for the query
        statement.setString(1, "%" + productName + "%"); // Adding '%' for wildcard search

        // Execute the query
        ResultSet rs = statement.executeQuery();

        // If a matching product is found, create and return an Ingredient object
        if (rs.next()) {
            Product product = new Product(
                    rs.getString("productName"),
                    rs.getInt("carbs"),
                    rs.getInt("protein"),
                    rs.getInt("fats"),
                    rs.getString("category")
            );

            // Close resources
            rs.close();
            statement.close();
            return product;
        }
        rs.close();
        statement.close();
        return null;
    }

    public static void EditProduct(String foodName, int carbs, int protein, int fats, String category, Connection connection) throws SQLException {

        try {
            String updateProductQuery = "UPDATE PRODUCTS SET carbs = ?, protein = ?, fats = ?, category = ? WHERE productName = ?";

            // Create a prepared statement
            PreparedStatement statement = connection.prepareStatement(updateProductQuery);
            statement.setInt(1, carbs);
            statement.setInt(2, protein);
            statement.setInt(3, fats);
            statement.setString(4, category);
            statement.setString(5, foodName);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String>  ShowProductsTable(Connection connection)
    {
        try {
            String query = "SELECT * FROM PRODUCTS";

            ArrayList<String> productData = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // Get data from each column, assuming column names are 'productName', 'carbs', 'protein', 'fats', and 'category'
                String productName = rs.getString("productName");
                int carbs = rs.getInt("carbs");
                int protein = rs.getInt("protein");
                int fats = rs.getInt("fats");
                String category = rs.getString("category");

                // Format the data as a string
                String productInfo = "Product Name: " + productName + ", Carbs: " + carbs + ", Protein: " + protein +
                        ", Fats: " + fats + ", Category: " + category;

                // Add the formatted string to the list
                productData.add(productInfo);
            }

            // Return the ArrayList containing all product data
            return productData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void DeleteMeal(String mealID, Connection connection) throws SQLException {
        String deleteSQL = "DELETE FROM MEALS WHERE mealID = ?";
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.setString(1, mealID);
        statement.executeUpdate();
    }

    public static void DeleteProduct(String mealID, Connection connection) throws SQLException {
        String deleteSQL = "DELETE FROM PRODUCTS WHERE productName = ?";
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.setString(1, mealID);
        statement.executeUpdate();
    }
}
