package com.example.coasterapiproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class CoasterdleController {


    public TextField searchField;
    public ListView<Coaster> resultsList;
    public Label parkLabel;
    public Label speedLabel;
    public Label inversionsLabel;
    public Label heightLabel;
    public Label lengthLabel;
    public ImageView selectedImageView;
    Image missingImage;

    public void initialize() throws Exception {
        missingImage = new Image(new FileInputStream("src/noImage.jpg"));
        Coaster.coasters = new ArrayList<>();
        String output = APISearch("https://captaincoaster.com/api/coasters?page=1&order%5Bid%5D=asc&order%5Brank%5D=asc");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(output);

        JsonNode jsonCoasterArray = jsonNode.get("member");
        for (JsonNode eachCoaster : jsonCoasterArray) {
            Coaster newCoaster = new Coaster();
            newCoaster.name = eachCoaster.get("name").asText();
            newCoaster.id = eachCoaster.get("id").asInt();
            newCoaster.park = eachCoaster.get("park").get("name").asText();
            newCoaster.includesDetails = false;

            System.out.println("OBJECT: " + newCoaster);
            Coaster.coasters.add(newCoaster);
            resultsList.getItems().add(newCoaster);

        }



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

    public void searchCoasters() throws Exception{
        String searchTerm = searchField.getText().replace(" ","%20");
        String output = APISearch("https://captaincoaster.com/api/coasters?page=1&name="+searchTerm+"&order%5Bid%5D=asc&order%5Brank%5D=asc");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(output);
        Coaster.coasters.clear();
        resultsList.getItems().clear();
        JsonNode jsonCoasterArray = jsonNode.get("member");
        for (JsonNode eachCoaster : jsonCoasterArray) {
            Coaster newCoaster = new Coaster();
            newCoaster.name = eachCoaster.get("name").asText();
            newCoaster.id = eachCoaster.get("id").asInt();
            newCoaster.park = eachCoaster.get("park").get("name").asText();
            newCoaster.includesDetails = false;

            System.out.println("OBJECT: " + newCoaster);
            Coaster.coasters.add(newCoaster);
            resultsList.getItems().add(newCoaster);

        }

    }

    public void getDetails() throws Exception{

        Coaster selectedCoaster = resultsList.getSelectionModel().getSelectedItem();
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
                selectedCoaster.imageFileName = jsonNode.get("mainImage").get("filename").asText();
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

        try {
            URL imageURL = new URL("https://pictures.captaincoaster.com/1440x1440/" + selectedCoaster.imageFileName);
            Image image = new Image(imageURL.openStream());
            selectedImageView.setImage(image);
        }
        catch(Exception ex){
            selectedImageView.setImage(missingImage);
        }


        parkLabel.setText("Park: "+selectedCoaster.park);

        if(selectedCoaster.length==0){
            lengthLabel.setText("N/A");
        }
        else{
            lengthLabel.setText("Length: "+String.valueOf(selectedCoaster.length)+"m");
        }
        if(selectedCoaster.height==0){
            heightLabel.setText("Height: N/A");
        }
        else{
            heightLabel.setText("Height: "+String.valueOf(selectedCoaster.height)+" m");
        }
        if(selectedCoaster.speed==0){
            speedLabel.setText("Speed: N/A");
        }
        else{
            speedLabel.setText("Speed: "+String.valueOf(selectedCoaster.speed+" Km/h"));
        }
        inversionsLabel.setText("Inversions: "+String.valueOf(selectedCoaster.inversionsNumber));

    }


}

