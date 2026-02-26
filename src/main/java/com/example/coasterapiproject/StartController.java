package com.example.coasterapiproject;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartController {
    public int highScore;
    public Label highScoreLabel;
    public void initialize(){


        highScoreLabel.setText("High Score: "+highScore);

    }

    public void startGame()throws Exception{
        CoasterApiApplication.gameScene();


    }

}
