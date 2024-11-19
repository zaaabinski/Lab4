package org.BackEnd;

import org.SubClasses.Ingredient;
import org.SubClasses.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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
                "category text," +
                "calories int)";
        statement.executeUpdate(TableProduct);
    }

    public static void CreateMealsTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String TableMeals = "CREATE TABLE MEALS (" +
                "mealID text, " +
                "productName text, " +
                "productWeight int, " +
                "productCalories int,"+
                "mealCategory text, " +
                "FOREIGN KEY (productName) REFERENCES PRODUCTS(productName) ON DELETE RESTRICT ON UPDATE CASCADE)";

        statement.executeUpdate(TableMeals);
    }

    //products
    public static void AddProductToBase(String foodName, int carbs, int protein, int fats, String category, Connection currentConnection) throws SQLException {
        String addProduct = "INSERT INTO PRODUCTS (productName,carbs,protein,fats,category,calories)  VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = currentConnection.prepareStatement(addProduct);
        statement.setString(1, foodName);
        statement.setInt(2, carbs);
        statement.setInt(3, protein);
        statement.setInt(4, fats);
        statement.setString(5, category);
        statement.setInt(6,Product.CaluclateCalories(carbs,protein,fats));
        statement.executeUpdate();
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

    public static ArrayList<String>  ShowProductsTable(Connection connection) {
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
                int calories = rs.getInt("calories");

                // Format the data as a string
                String productInfo = "Product Name: " + productName + ", Carbs: " + carbs + ", Protein: " + protein +
                        ", Fats: " + fats + ", Category: " + category + ", Calories : " +calories;

                // Add the formatted string to the list
                productData.add(productInfo);
            }

            // Return the ArrayList containing all product data
            return productData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //meals

    public static void AddMealToBase(ArrayList<Ingredient> meal, Connection connection) throws SQLException {
        for (Ingredient ing : meal) {
            String addMeal = "INSERT INTO MEALS (mealID,productName,productWeight,mealCategory,productCalories) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(addMeal);
            statement.setString(1, ing.getMealId());
            statement.setString(2, ing.getProductName());
            statement.setInt(3, ing.getProductWeight());
            statement.setString(4, ing.getMealCategory());

            int caloriesHelp=GetCaloriesOfProduct(ing.getProductName(),connection);
            double adjustedCalories = caloriesHelp*(ing.getProductWeight()/100.00);

            statement.setInt(5, (int)adjustedCalories);
            statement.executeUpdate();
        }
    }

    private static int GetCaloriesOfProduct(String productName, Connection connection) {
        try {
            String getCalories = "SELECT calories FROM PRODUCTS WHERE productName = ?";
            PreparedStatement statement = connection.prepareStatement(getCalories);
            statement.setString(1, productName.trim());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("calories");
            } else {
                System.out.println("No product found with the name: " + productName);
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("SQLException while fetching calories for product: " + productName);
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Ingredient> SearchForMealToEdit(String mealToEditName,Connection connection) {
        try {
            ArrayList<Ingredient> editableMeal=new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT *, count(*) OVER () AS ROWS FROM MEALS WHERE mealID LIKE ?");

            statement.setString(1, "%" + mealToEditName + "%"); // Adding '%' for wildcard search
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                // Get data from each column
                String mealID = rs.getString("mealID");
                String productName = rs.getString("productName");
                int productWeight = rs.getInt("productWeight");
                String mealCategory = rs.getString("mealCategory");

                // Create a new Ingredient object with the data
                Ingredient ingredient = new Ingredient(mealID, productName, productWeight, mealCategory);

                // Add the Ingredient object to the list
                editableMeal.add(ingredient);
            }

            return editableMeal;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String ShowMealsTable(Connection connection) {
        StringBuilder result = new StringBuilder();

        try {
            String query = "SELECT * FROM MEALS ORDER BY mealID";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            int mealCalories = 0;
            String currentMealID = "";
            boolean isFirst = true;

            while (rs.next()) {
                String mealID = rs.getString("mealID");
                String mealCategory = rs.getString("mealCategory");
                String productName = rs.getString("productName");
                int productWeight = rs.getInt("productWeight");
                int productCalories = rs.getInt("productCalories");

                // Check if it's a new meal (mealID changes)
                if (!mealID.equals(currentMealID)) {
                    if (!isFirst) {
                        // Add the total calories for the previous meal before starting a new one
                        result.append("Total Calories: ").append(mealCalories).append(" kcal\n\n");
                    }
                    isFirst = false;

                    // Reset the total calories for the new meal
                    mealCalories = 0;

                    // Add the meal header
                    result.append("Category: ").append(mealCategory).append("\n");
                    result.append("Meal Name: ").append(mealID).append("\n");
                }

                // Add the product details (name, weight, calories)
                result.append("- Product: ").append(productName)
                        .append(", Weight: ").append(productWeight).append(" grams")
                        .append(", Calories: ").append(productCalories).append(" kcal\n");

                // Add the product's calories to the total meal calories
                mealCalories += productCalories;

                // Update the current meal ID
                currentMealID = mealID;
            }

            // After the loop, add the total calories for the last meal
            if (!isFirst) {
                result.append("Total Calories: ").append(mealCalories).append(" kcal\n");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving meals from the database.", e);
        }

        return result.toString();
    }


    public static void EditMeal(String mealID,String productName, int productWeight, Connection connection) {
        try
        {
            String query = "UPDATE MEALS SET productName = ?, productWeight = ? WHERE mealID = ? AND productName = ?";

            // Prepare the statement to prevent SQL injection
            PreparedStatement statement = connection.prepareStatement(query);
            String newProductName = productName;
            // Set the parameters for the query
            statement.setString(1, newProductName);
            statement.setInt(2, productWeight);
            statement.setString(3, mealID);
            statement.setString(4, productName);

            // Execute the update query
            int rowsUpdated = statement.executeUpdate();

            // Optional: Provide feedback on the number of rows affected
            if (rowsUpdated > 0) {
                System.out.println("Successfully updated " + rowsUpdated + " row(s) in the MEALS table.");
            } else {
                System.out.println("No rows matched the criteria for updating.");
            }
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

    public static HashMap<String, HashMap<String, Integer>> SummarizeIngredientsByProductCategory(Connection connection) throws SQLException {
        HashMap<String, HashMap<String, Integer>> productCategoryMap = new HashMap<>();

        // SQL Query: Retrieve products with their categories and weights
        String query = "SELECT p.category, p.productName, SUM(m.productWeight) AS totalWeight "+
       "FROM MEALS m INNER JOIN PRODUCTS p ON m.productName = p.productName GROUP BY p.category, p.productName";

        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        // Process each row to group by product category and sum weights
        while (rs.next()) {
            String productCategory = rs.getString("category");
            String productName = rs.getString("productName");
            int totalWeight = rs.getInt("totalWeight");

            // Group by product category
            productCategoryMap.putIfAbsent(productCategory, new HashMap<>());
            HashMap<String, Integer> productMap = productCategoryMap.get(productCategory);

            // Add product and weight to the category
            productMap.put(productName, totalWeight);
        }

        rs.close();
        statement.close();

        return productCategoryMap;
    }
}
