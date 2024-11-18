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
    private TextField Info;
    @FXML
    private TextArea productBaseShowTextArea;
    public static Connection connection;

    @FXML
    public void ShowPane(Pane paneToShow) {
        // List of all panes
        Pane[] allPanes = {MainPane, ButtonsMenu, EditProductPane, AddProductPane, DeleteProductPane, ProductPane, MealPane, AddMealPane, EditMealPane, DeleteMealPane,ShowProductPane};

        // Loop through all panes and make them invisible
        for (Pane pane : allPanes) {
            if (!pane.getId().equals("MainPane")) {
                pane.setVisible(false);
            }
        }

        // Make the specified pane visible
        ButtonsMenu.setVisible(true);
        paneToShow.setVisible(true);
    }

    // Action handlers (methods to handle button clicks)
    @FXML
    private void ShowAddProductP() {
        // Action to show Add Product Pane
        System.out.println("Show Add Product Pane clicked!");
        ShowPane(AddProductPane);
    }

    @FXML
    private void ShowEditProductP() {
        // Action to show Edit Product Pane
        System.out.println("Show Edit Product Pane clicked!");
        ShowPane(EditProductPane);
    }

    @FXML
    private void ShowDeleteProductP() {
        // Action to show Delete Product Pane
        System.out.println("Show Delete Product Pane clicked!");
        ShowPane(DeleteProductPane);
    }

    @FXML
    private void ShowProductMenu() {
        // Action to show the Product Menu Pane
        System.out.println("Show Product Menu clicked!");
        ShowPane(ProductPane);
    }

    @FXML
    private void handleHomeButton() {
        // Handle the Home button click event
        System.out.println("Home button clicked!");
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
    public void AddChosenProductB(ActionEvent actionEvent) throws SQLException {
        try {
            QueryOperations.AddProductToBase(nameOfNewProduct.getText(), Integer.parseInt(carbsOfNewProduct.getText()), Integer.parseInt(proteinOfNewProduct.getText()), Integer.parseInt(fatsOfNewProduct.getText()), categoryOfNewProduct.getText(), connection);
            System.out.println("InfoLabel: " + Info);

            Info.setText("Added product " + nameOfNewProduct.getText());
        } catch (SQLException e) {
            Info.setText("Something went wrong, ensure that data is proper");
            throw new RuntimeException(e);
        }
    }

    //edits record of existing product
    public void SearchEditChosenProductB(ActionEvent actionEvent) {
        try
        {
            Product productToEdit = QueryOperations.SearchForProductToEdit(nameOfEditedProduct.getText(),connection);
            FillEditedProductTextfields(productToEdit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        } catch (SQLException e) {
            Info.setText("Something went wrong, ensure that data is proper");
            throw new RuntimeException(e);
        }
    }

    //deletes chosen product
    public void DeleteChosenProductB(ActionEvent actionEvent) throws SQLException {
        try {
            QueryOperations.DeleteProduct(nameOfProductToDelete.getText(), connection);
            Info.setText("Deleted product " + nameOfProductToDelete.getText());
        } catch (SQLException e) {
            Info.setText("Something went wrong, ensure that data is proper");
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
            QueryOperations.AddMealToBase(Meal, connection);
            for (TextField t : allNames) {
                t.setText(null);
            }
            for (TextField t : allWeights) {
                t.setText(null);
            }
            Info.setText("Added meal " + nameOfNewMeal.getText());
            nameOfNewMeal.setText(null);
            categoryOfNewMeal.setText(null);

        } catch (SQLException e) {
            Info.setText("Something went wrong, ensure that data is proper");
        }
    }

    //edits record of meal
    public void EditChosenMealB(ActionEvent actionEvent) {

    }

    //deletes chosen meal
    public void DeleteChosenMealB(ActionEvent actionEvent) throws SQLException {
        try {
            QueryOperations.DeleteMeal(nameOfMealToDelete.getText(), connection);
            Info.setText("Deleted Meal " + nameOfMealToDelete.getText());
        } catch (SQLException e) {
            Info.setText("Something went wrong, ensure that data is proper");
        }
    }


    public void SearchEditChosenMealB(ActionEvent actionEvent) {

    }
}
