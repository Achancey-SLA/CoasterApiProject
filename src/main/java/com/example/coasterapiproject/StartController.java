package com.example.coasterapiproject;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StartController {
    static public int highScore;
    static public int hardHighScore;
    public Label highScoreLabel;
    public CheckBox seedCheck;
    public TextField seedField;
    public Label seedWarning;
    public ImageView logoImage;
    static public boolean useSeed;
    public HBox rightBox;
    static public boolean hardMode;
    static public String seed;
    public CheckBox hardCheck;
    public void initialize() throws Exception{
        hardMode = false;
        logoImage.setImage(new Image(new FileInputStream("src/logo.png")));
        int importedHighScore;
        int importedHardHighScore;
        try {
            importedHighScore = Integer.parseInt(Files.readString(Paths.get("highScore.txt")));
        }
        catch(Exception ex){
            importedHighScore = 0;
        }

        try {
            importedHardHighScore = Integer.parseInt(Files.readString(Paths.get("hardHighScore.txt")));
        }
        catch(Exception ex){
            importedHardHighScore = 0;
        }

        if(importedHighScore>highScore){
            highScore = importedHighScore;
        }
        if(importedHardHighScore>hardHighScore){
            hardHighScore = importedHardHighScore;
        }

        Files.writeString(Paths.get("highScore.txt"), String.valueOf(highScore), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.writeString(Paths.get("hardHighScore.txt"), String.valueOf(hardHighScore), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);


        useSeed = false;
        seedField.setDisable(true);
        seedWarning.setOpacity(0);
        highScoreLabel.setText("High Score: "+highScore);

        seedField.textProperty().addListener((observable, oldValue, newValue) -> {
            seed = seedField.getText();
            System.out.println(seed);
        });

    }

    public void hardChecked(){
        hardMode = hardCheck.isSelected();
        if(hardMode){
            highScoreLabel.setText("Hard High Score: "+ hardHighScore);
        }
        else{
            highScoreLabel.setText("High Score: " + highScore);
        }
    }
    public void seedChecked(){
        useSeed = seedCheck.isSelected();
        seedField.setDisable(!useSeed);
        if(useSeed){
            seedWarning.setOpacity(1);
        }
        else{
            seedWarning.setOpacity(0);
        }
    }

    public void startGame()throws Exception{
        CoasterApiApplication.gameScene();
    }
}
