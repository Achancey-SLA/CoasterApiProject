package com.example.coasterapiproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class CoasterApiApplication extends Application {
    static Stage mainStage;
    @Override

    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CoasterApiApplication.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        mainStage = stage;
    }

    public static void gameScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoasterApiApplication.class.getResource("coasterdleView.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 800, 600);
        mainStage.setScene(newScene);

    }

    public static void resetScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoasterApiApplication.class.getResource("startView.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 800, 600);
        mainStage.setScene(newScene);

    }
}
