package com.example.coasterapiproject;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartController {
    static public int highScore;
    public Label highScoreLabel;
    public CheckBox seedCheck;
    public TextField seedField;
    public Label seedWarning;
    static public boolean useSeed;
    static public String seed;
    public void initialize(){
        useSeed = false;
        seedField.setDisable(true);
        seedWarning.setOpacity(0);
        highScoreLabel.setText("High Score: "+highScore);

        seedField.textProperty().addListener((observable, oldValue, newValue) -> {
            seed = seedField.getText();
            System.out.println(seed);
        });

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
