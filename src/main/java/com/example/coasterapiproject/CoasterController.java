package com.example.coasterapiproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CoasterController {


    public TextField searchField;
    public ListView<Coaster> resultsList;

    public void initialize() throws Exception {
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
        String output = APISearch("https://captaincoaster.com/api/coasters?page=1&name="+searchField.getText()+"&order%5Bid%5D=asc&order%5Brank%5D=asc");
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
            selectedCoaster.height = (float) jsonNode.get("height").asDouble();
            selectedCoaster.speed = (float) jsonNode.get("speed").asDouble();
            selectedCoaster.length = (float) jsonNode.get("length").asDouble();
            selectedCoaster.inversionsNumber = jsonNode.get("inversionsNumber").asInt();
            selectedCoaster.includesDetails = true;
            System.out.println(selectedCoaster.getInformation());
        }

    }


}

