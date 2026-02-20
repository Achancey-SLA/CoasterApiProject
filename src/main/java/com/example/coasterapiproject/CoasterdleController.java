package com.example.coasterapiproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CoasterdleController {
    public ImageView backgroundImage;
    public ImageView logoImage;
    public ArrayList<Integer> viableIDs;
    public ArrayList<String> questions;
    public Coaster rightCoaster;
    public Coaster leftCoaster;
    public ImageView leftView;
    public ImageView rightView;
    public Label leftLabel;
    public Label rightLabel;
    public Label questionText;
    public Button leftButton;
    public Button rightButton;
    public int currentQuestion;
    public int score;
    public Label scoreLabel;

    Random random;


    Image missingImage;

    public void initialize() throws Exception {
        //remove later
        IDfinder.findIDs();

        score = 0;
        random = new Random();
        viableIDs = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,170));
        questions = new ArrayList<>(Arrays.asList(
                "Which coaster is taller?",
                "Which coaster is longer?"
                ));
        backgroundImage.setImage(new Image(new FileInputStream("src/background.png")));
        missingImage = new Image(new FileInputStream("src/noImage.jpg"));
        logoImage.setImage(new Image(new FileInputStream("src/logo.png")));

        createQuestion();
        createQuestion();
        System.out.println(leftCoaster);
    }

    public void createQuestion() throws Exception{
        scoreLabel.setText("Score: "+score);

        leftButton.setDisable(true);
        rightButton.setDisable(true);
        currentQuestion = random.nextInt(0,questions.size());
        System.out.println(currentQuestion);
        questionText.setText(questions.get(currentQuestion));


        try{
            leftCoaster = rightCoaster;
        }
        catch (Exception ex){}
        rightCoaster = new Coaster();
        ObjectMapper objectMapper = new ObjectMapper();
        int nextId = viableIDs.get(random.nextInt(0,(viableIDs.size())));

        try{

            while(leftCoaster.id == nextId){
                System.out.println("reroll");
                nextId = viableIDs.get(random.nextInt(0,(viableIDs.size())));

            }
        } catch (Exception e) {
            System.out.println("lmao");
        }
        String output = APISearch("https://captaincoaster.com/api/coasters/"+nextId);
        JsonNode jsonNode = objectMapper.readTree(output);
        rightCoaster.name = jsonNode.get("name").asText();
        rightCoaster.park = jsonNode.get("park").asText();
        rightCoaster.country = jsonNode.get("park").get("country").get("name").asText();
        try {
            rightCoaster.height = (float) jsonNode.get("height").asDouble();
        }
        catch(Exception ex){}
        try {
            rightCoaster.speed = (float) jsonNode.get("speed").asDouble();
        }
        catch(Exception ex){}
        try {
            rightCoaster.length = (float) jsonNode.get("length").asDouble();
        }
        catch(Exception ex){}
        try {
            rightCoaster.inversionsNumber = jsonNode.get("inversionsNumber").asInt();
        }
        catch(Exception ex){}
        try {
            rightCoaster.manufacturer = jsonNode.get("manufacturer").get("name").asText();
        }
        catch(Exception ex){}
        try {
            rightCoaster.imageFileName = jsonNode.get("mainImage").get("filename").asText();
            rightCoaster.image =new Image( new URL("https://pictures.captaincoaster.com/1440x1440/" + rightCoaster.imageFileName).openStream());
        }
        catch(Exception ex){}
        rightCoaster.id = jsonNode.get("id").asInt();
            rightCoaster.includesDetails = true;


            System.out.println(rightCoaster.getInformation());
        rightView.setImage(rightCoaster.image);
        rightLabel.setText(rightCoaster.name);

        try{
            leftView.setImage(leftCoaster.image);
            leftLabel.setText(leftCoaster.name);
        }catch (Exception ex){}

        leftButton.setDisable(false);
        rightButton.setDisable(false);

    }



    public String APISearch(String urlString) throws Exception{
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization", "55533d6a-c6d8-4e83-9c2b-eab302a7dfb7");
        //e.g. bearer token= eyJhbGciOiXXXzUxMiJ9.eyJzdWIiOiPyc2hhcm1hQHBsdW1zbGljZS5jb206OjE6OjkwIiwiZXhwIjoxNTM3MzQyNTIxLCJpYXQiOjE1MzY3Mzc3MjF9.O33zP2l_0eDNfcqSQz29jUGJC-_THYsXllrmkFnk85dNRbAw66dyEKBP5dVcFUuNTA8zhA83kk3Y41_qZYx43T

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");


        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        return response.toString();

}
    public void onLeftButton() throws Exception{
        if(currentQuestion == 0){
            if(leftCoaster.height>=rightCoaster.height){
                score+=1;
            }
            else{
                score = 0;
            }
        }
        if(currentQuestion == 1){
            if(leftCoaster.length>=rightCoaster.length){
                score+=1;
            }
            else{
                score = 0;
            }
        }
        createQuestion();
    }

    public void onRightButton()throws Exception{{
        if(currentQuestion == 0){
            if(leftCoaster.height<=rightCoaster.height){
                score+=1;
            }else{
                score =0;
            }
        }
        if(currentQuestion == 1){
            if(leftCoaster.length<=rightCoaster.length){
                score+=1;
            }
            else{
                score = 0;
            }
        }

        createQuestion();
    }
    }



    public void getDetails(Coaster selectedCoaster) throws Exception{

        System.out.println(selectedCoaster);
        if(!selectedCoaster.includesDetails){
            ObjectMapper objectMapper = new ObjectMapper();
            String output = APISearch("https://captaincoaster.com/api/coasters/"+selectedCoaster.id);
            JsonNode jsonNode = objectMapper.readTree(output);
            try {
                selectedCoaster.height = (float) jsonNode.get("height").asDouble();
            }
            catch(Exception ex){}
            try {
                selectedCoaster.manufacturer = jsonNode.get("manufacturer").get("name").asText();
            }
            catch(Exception ex){}
            try {
                selectedCoaster.speed = (float) jsonNode.get("speed").asDouble();
            }
            catch(Exception ex){}
            try {
            selectedCoaster.length = (float) jsonNode.get("length").asDouble();
            }
            catch(Exception ex){}
            try {
                selectedCoaster.inversionsNumber = jsonNode.get("inversionsNumber").asInt();
            }
            catch(Exception ex){}

            try {
                selectedCoaster.includesDetails = true;
            }
            catch(Exception ex){}
            try {
                System.out.println(selectedCoaster.getInformation());
            }
            catch(Exception ex){}
        }

    }


}

