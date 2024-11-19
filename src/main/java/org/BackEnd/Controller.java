package org.BackEnd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.SubClasses.Ingredient;
import org.SubClasses.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Controller {

    // Buttons
    @FXML
    private Button bHome;
    @FXML
    private Button bProducts;
    @FXML
    private Button bMeals;
    @FXML
    private Button bPDF;
    @FXML
    private Button ShowAddingProductsPane;
    @FXML
    private Button ShowEditingProductsPane;
    @FXML
    private Button ShowDeletingProductsPane;
    @FXML
    private Button ShowProductsPane;
    @FXML
    private Button ShowMealPane;
    @FXML
    private Button ProductDeleteButton;
    @FXML
    private Button ShowAddingMealPane;
    @FXML
    private Button ShowEditingMealPane;
    @FXML
    private Button ShowDeletingMealPane;

    // Labels
    @FXML
    private Label labelMessage;

    // Panels (Pane) for layout switching
    @FXML
    private Pane MainPane;
    @FXML
    private Pane ButtonsMenu;
    @FXML
    private Pane ProductPane;
    @FXML
    private Pane AddProductPane;
    @FXML
    private Pane EditProductPane;
    @FXML
    private Pane DeleteProductPane;
    @FXML
    private Pane MealPane;
    @FXML
    private Pane AddMealPane;
    @FXML
    private Pane EditMealPane;
    @FXML
    private Pane DeleteMealPane;
    @FXML
    private Pane ShowProductPane;
    @FXML
    private Pane ShowMealBasePane;

    // TextFields
    @FXML
    private TextField nameOfNewProduct;
    @FXML
    private TextField carbsOfNewProduct;
    @FXML
    private TextField proteinOfNewProduct;
    @FXML
    private TextField fatsOfNewProduct;
    @FXML
    private TextField categoryOfNewProduct;
    @FXML
    private TextField nameOfEditedProduct;
    @FXML
    private TextField editedValueCarbs;
    @FXML
    private TextField editedValueProtein;
    @FXML
    private TextField editedValueFats;
    @FXML
    private TextField editedValueCategory;
    @FXML
    private TextField nameOfProductToDelete;
    @FXML
    private TextField nameOfNewMeal;
    @FXML
    private TextField nameOfProductForMealOne;
    @FXML
    private TextField weightOfMealOne;
    @FXML
    private TextField categoryOfNewMeal;
    @FXML
    private TextField nameOfProductForMealTwo;
    @FXML
    private TextField weightOfMealTwo;
    @FXML
    private TextField nameOfProductForMealThree;
    @FXML
    private TextField weightOfMealThree;
    @FXML
    private TextField nameOfProductForMealFour;
    @FXML
    private TextField weightOfMealFour;
    @FXML
    private TextField nameOfProductForMealFive;
    @FXML
    private TextField weightOfMealFive;
    @FXML
    private TextField nameOfEditedMeal;
    @FXML
    private TextField mealEditProductOne;
    @FXML
    private TextField mealEditWeightProductOne;
    @FXML
    private TextField categoryOfEditedMeal;
    @FXML
    private TextField mealEditProductTwo;
    @FXML
    private TextField mealEditWeightProductTwo;
    @FXML
    private TextField mealEditProductThree;
    @FXML
    private TextField mealEditWeightProductThree;
    @FXML
    private TextField mealEditProductFour;
    @FXML
    private TextField mealEditWeightProductFour;
    @FXML
    private TextField mealEditProductFive;
    @FXML
    private TextField mealEditWeightProductFive;
    @FXML
    private TextField nameOfMealToDelete;

    @FXML
    private  TextField Info;
    @FXML
    private TextArea productBaseShowTextArea;
    @FXML
    private TextArea mealBaseShowTextArea;
    public static Connection connection;

    @FXML
    public void ShowPane(Pane paneToShow) {
        // List of all panes
        Pane[] allPanes = {MainPane, ButtonsMenu, EditProductPane, AddProductPane, DeleteProductPane, ProductPane, MealPane, AddMealPane, EditMealPane, DeleteMealPane,ShowProductPane,ShowMealBasePane};

        // Loop through all panes and make them invisible
        for (Pane pane : allPanes) {
            if (!pane.getId().equals("MainPane")) {
                pane.setVisible(false);
            }
        }

        // Make the specified pane visible
        ButtonsMenu.setVisible(true);
        paneToShow.setVisible(true);
        Info.setText("");

    }

    // Action handlers (methods to handle button clicks)
    @FXML
    private void ShowAddProductP() {
        // Action to show Add Product Pane
        ShowPane(AddProductPane);
    }

    @FXML
    private void ShowEditProductP() {
        // Action to show Edit Product Pane
        ShowPane(EditProductPane);
    }

    @FXML
    private void ShowDeleteProductP() {
        // Action to show Delete Product Pane
        ShowPane(DeleteProductPane);
    }

    @FXML
    private void ShowProductMenu() {
        // Action to show the Product Menu Pane
        ShowPane(ProductPane);
    }

    @FXML
    private void handleHomeButton() {
        // Handle the Home button click event
        ShowPane(ButtonsMenu);
        // Hide all other panes and show the main menu if needed
    }

    public void ShowProductBaseB(ActionEvent actionEvent) {
        ShowPane(ShowProductPane);
        productBaseShowTextArea.setText("");
        ArrayList<String> ProductTable =  QueryOperations.ShowProductsTable(connection);

        for(String s : ProductTable)
        {
            productBaseShowTextArea.setText(productBaseShowTextArea.getText() + "\n" + s);
        }
    }

    public void HandleShowMealBasePane(ActionEvent actionEvent) {
        ShowPane(ShowMealBasePane); // Assuming ShowMealPane is the relevant pane
        mealBaseShowTextArea.setText(""); // Clear the TextArea before displaying new data

        try {
            // Fetch the formatted meal data using the ShowMealsTable method
            String mealData = QueryOperations.ShowMealsTable(connection);

            // Set the retrieved meal data into the TextArea
            mealBaseShowTextArea.setText(mealData);
        } catch (Exception e) {
            // Handle exceptions and provide user feedback
            mealBaseShowTextArea.setText("An error occurred while fetching meal data. Please try again.");
        }
    }

    public void ShowAddMealP(ActionEvent actionEvent) {
        ShowPane(AddMealPane);
    }

    public void ShowEditMealP(ActionEvent actionEvent) {
        ShowPane(EditMealPane);
    }

    public void ShowDeleteMealP(ActionEvent actionEvent) {
        ShowPane(DeleteMealPane);
    }

    public void ShowMealsPane(ActionEvent actionEvent) {
        ShowPane(MealPane);
    }

    //adds product to db
    public void AddChosenProductB(ActionEvent actionEvent) {
        try {
            QueryOperations.AddProductToBase(nameOfNewProduct.getText(), Integer.parseInt(carbsOfNewProduct.getText()), Integer.parseInt(proteinOfNewProduct.getText()), Integer.parseInt(fatsOfNewProduct.getText()), categoryOfNewProduct.getText(), connection);

            Info.setText("Added product " + nameOfNewProduct.getText());
            nameOfNewProduct.setText("");
            carbsOfNewProduct.setText("");
            proteinOfNewProduct.setText("");
            fatsOfNewProduct.setText("");
            categoryOfNewProduct.setText("");
        } catch (Exception e) {
            Info.setText("Something went wrong, ensure that data is proper");
        }
    }

    //edits record of existing product
    public void SearchEditChosenProductB(ActionEvent actionEvent) {
        try
        {
            Product productToEdit = QueryOperations.SearchForProductToEdit(nameOfEditedProduct.getText(),connection);
            FillEditedProductTextfields(productToEdit);
        } catch (Exception e) {
            Info.setText("Something went wrong, can't find given product");
        }
    }

    public void FillEditedProductTextfields(Product productToEdit) {
        editedValueProtein.setText(String.valueOf(productToEdit.getProtein()));
        editedValueCarbs.setText(String.valueOf(productToEdit.getCarbs()));
        editedValueFats.setText(String.valueOf(productToEdit.getFats()));
        editedValueCategory.setText(String.valueOf(productToEdit.getCategory()));
    }

    public void EditChosenProductB(ActionEvent actionEvent) {
        try {
            QueryOperations.EditProduct(nameOfEditedProduct.getText(), Integer.parseInt(editedValueCarbs.getText()), Integer.parseInt(editedValueProtein.getText()), Integer.parseInt(editedValueFats.getText()), editedValueCategory.getText(), connection);
            Info.setText("Edited product " + nameOfEditedProduct.getText());
            nameOfEditedProduct.setText("");
            editedValueCategory.setText("");
            editedValueFats.setText("");
            editedValueCarbs.setText("");
            editedValueProtein.setText("");
        } catch (SQLException e) {
            Info.setText("Something went wrong when editing product, check data");
        }
    }

    //deletes chosen product
    public void DeleteChosenProductB(ActionEvent actionEvent) throws SQLException {
        try {
            QueryOperations.DeleteProduct(nameOfProductToDelete.getText(), connection);
            Info.setText("Deleted product " + nameOfProductToDelete.getText());
            nameOfProductToDelete.setText("");
        } catch (Exception e) {
            Info.setText("Something went wrong when deleting product, check data");
        }
    }

    //adds meal to db
    public void AddChosenMealB(ActionEvent actionEvent) {
        ArrayList<Ingredient> Meal = new ArrayList<>();
        TextField[] allNames = {nameOfProductForMealOne, nameOfProductForMealTwo, nameOfProductForMealThree, nameOfProductForMealFour, nameOfProductForMealFive};
        TextField[] allWeights = {weightOfMealOne, weightOfMealTwo, weightOfMealThree, weightOfMealFour, weightOfMealFive};
        int ingredientsToAdd = 0;
        for (TextField t : allNames) {
            if (t.getText() != null && !t.getText().isEmpty())
                ingredientsToAdd++;
        }

        for (int i = 0; i < ingredientsToAdd; i++) {
            Meal.add(new Ingredient(nameOfNewMeal.getText(), allNames[i].getText(), Integer.parseInt(allWeights[i].getText()), categoryOfNewMeal.getText()));
        }

        try {
            connection.setAutoCommit(false);
            QueryOperations.AddMealToBase(Meal, connection);
            for (TextField t : allNames) {
                t.setText("");
            }
            for (TextField t : allWeights) {
                t.setText("");
            }
            Info.setText("Added meal " + nameOfNewMeal.getText());
            nameOfNewMeal.setText("");
            categoryOfNewMeal.setText("");
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
                Info.setText("Something went wrong. No records were added. Ensure that data is proper.");
            } catch (SQLException rollbackEx) {
                Info.setText("Error during rollback");
            }
            Info.setText("Something went wrong, ensure that data is proper");
        }finally {
            // Re-enable auto-commit mode
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Info.setText("Something went wrong, ensure that data is proper");

            }
        }
    }

    //edits record of meal
    public void SearchEditChosenMealB(ActionEvent actionEvent) {
        try
        {
            ArrayList<Ingredient> mealToEdit = QueryOperations.SearchForMealToEdit(nameOfEditedMeal.getText(),connection);
            FillEditedMealTextfields(mealToEdit);
        } catch (Exception e) {
            Info.setText("Something went wrong when searching for meal to edit ensure that data is proper");
        }
    }

    public void FillEditedMealTextfields(ArrayList<Ingredient> meal) {
        categoryOfEditedMeal.setText(meal.get(0).getMealCategory());
        TextField[] productsTable = {mealEditProductOne,
                mealEditProductTwo,
                mealEditProductThree,
                mealEditProductFour,
                mealEditProductFive};
        TextField[] weightTable = {mealEditWeightProductOne,
                mealEditWeightProductTwo,
                mealEditWeightProductThree,
                mealEditWeightProductFour,
                mealEditWeightProductFive};


        for(int i =0;i<meal.size();i++)
        {
            productsTable[i].setText(meal.get(i).getProductName());
            weightTable[i].setText(String.valueOf(meal.get(i).getProductWeight()));
        }
    }

    public void EditChosenMealB(ActionEvent actionEvent) {
        try {
            TextField[] productsTable = {mealEditProductOne,
                    mealEditProductTwo,
                    mealEditProductThree,
                    mealEditProductFour,
                    mealEditProductFive};
            TextField[] weightTable = {mealEditWeightProductOne,
                    mealEditWeightProductTwo,
                    mealEditWeightProductThree,
                    mealEditWeightProductFour,
                    mealEditWeightProductFive};

            for (int i = 0; i < 5; i++) {
                if (productsTable[i].getText() != null || !productsTable[i].getText().isEmpty()) {
                    if (weightTable[i].getText().isEmpty()) {
                        QueryOperations.EditMeal(nameOfEditedMeal.getText(), productsTable[i].getText(), 0, connection);
                    } else {
                        QueryOperations.EditMeal(nameOfEditedMeal.getText(), productsTable[i].getText(), Integer.parseInt(weightTable[i].getText()), connection);
                    }
                    Info.setText("Edited meal " + nameOfEditedMeal.getText());
                }
            }
            for (TextField t : productsTable) {
                t.setText("");
            }
            for (TextField t : weightTable) {
                t.setText("");
            }
            nameOfEditedMeal.setText("");
            categoryOfEditedMeal.setText("");
        } catch (Exception e) {
            Info.setText("Couldn't edit meal, ensure that data is proper");
        }
    }
    //deletes chosen meal
    public void DeleteChosenMealB(ActionEvent actionEvent) throws SQLException {
        try {
            QueryOperations.DeleteMeal(nameOfMealToDelete.getText(), connection);
            Info.setText("Deleted Meal " + nameOfMealToDelete.getText());
            nameOfMealToDelete.setText("");
        } catch (Exception e) {
            Info.setText("Something went wrong when deleting meal ensure that data is proper");
        }
    }

    //pdf generating
    public void GeneratePDFButton(ActionEvent actionEvent) {
        GenerateIngredientsPDF(connection,"C:\\\\Users\\\\jakmi\\\\Desktop\\\\output.pdf");
    }

    public  void GenerateIngredientsPDF(Connection connection, String pdfFilePath) {
        try {
            // Summarize ingredients by category
            HashMap<String, HashMap<String, Integer>> summary = QueryOperations.SummarizeIngredientsByProductCategory(connection);

            // Generate the PDF using Aspose
            PDFGenerator.GeneratePDF(summary, pdfFilePath);
            Info.setText("PDF generated successfully at " + pdfFilePath);
        } catch (Exception e) {
            Info.setText("Something went wrong when greeting PDF, check data");
        }
    }

    public void SetInfoText(String txt)
    {
        Info.setText(txt);
    }
    private static Controller instance;
    public Controller() {
        instance = this; // Set the static reference in the constructor
    }

    public static Controller getInstance() {
        return instance; // Provide access to the instance
    }
}
