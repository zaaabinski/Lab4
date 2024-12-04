package org.UI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class ApplicationStart extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/UI.fxml"));
        primaryStage.setTitle("Diet builder");
        primaryStage.setScene(new Scene(root, 700,500));
        primaryStage.show();
     }

    public static void StartWindowApp()
    {
        launch();
    }
}
