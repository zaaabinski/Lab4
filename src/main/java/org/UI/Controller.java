package org.UI;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Controller {

    // Buttons
    @FXML private Button bHome;
    @FXML private Button bProducts;
    @FXML private Button bMeals;
    @FXML private Button bPDF;
    @FXML private Button ShowAddingProductsPane;
    @FXML private Button ShowEditingProductsPane;
    @FXML private Button ShowDeletingProductsPane;
    @FXML private Button ShowProductsPane;

    // Labels
    @FXML private Label labelMessage;

    // Panels (Pane) for layout switching
    @FXML private Pane MainPane;
    @FXML private Pane ButtonsMenu;
    @FXML private Pane EditProductPane;
    @FXML private Pane AddProductPane;
    @FXML private Pane DeleteProductPane;
    @FXML private Pane ProductPane;

    // TextFields
    @FXML private TextField nameOfEditedProduct;
    @FXML private TextField newValueCarbs;
    @FXML private TextField newValueProtein;
    @FXML private TextField newValueFats;
    @FXML private TextField newValueCategory;

    @FXML private TextField nameOfNewProduct;
    @FXML private TextField carbsOfNewProduct;
    @FXML private TextField proteinOfNewProduct;
    @FXML private TextField fatsOfNewProduct;
    @FXML private TextField categoryOfNewProduct;

    @FXML private TextField nameOfProductToDelete;


    @FXML
    public void ShowPane(Pane paneToShow) {
        // List of all panes
        Pane[] allPanes = {MainPane, ButtonsMenu, EditProductPane, AddProductPane, DeleteProductPane, ProductPane};

        // Loop through all panes and make them invisible
        for (Pane pane : allPanes) {
            if(!pane.getId().equals("MainPane")){
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

    // Additional methods to interact with TextFields, Buttons, etc.
    public void initialize() {
        // Optional: You can perform initialization here if needed
        // For example, setting up default values for text fields or setting default pane visibility
        AddProductPane.setVisible(false);
        EditProductPane.setVisible(false);
        DeleteProductPane.setVisible(false);
        ProductPane.setVisible(false);
    }



}
