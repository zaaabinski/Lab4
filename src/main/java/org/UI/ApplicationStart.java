package org.UI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.BackEnd.Controller;

import java.io.IOException;


public class ApplicationStart extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/UI.fxml"));
        primaryStage.setTitle("Front End");
        primaryStage.setScene(new Scene(root, 700,500));
        primaryStage.show();
     }

    public static void StartWindowApp()
    {
        launch();
    }
}
