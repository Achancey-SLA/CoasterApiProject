package com.example.coasterapiproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IDfinder {
    ArrayList<Integer> validIDs;


    public static void findIDs() throws Exception{

        for(int i=0;i<100;i++) {
            System.out.println(i);

            try{
                String output = APISearch("https://captaincoaster.com/api/coasters/" + 170);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(output);
                String country = jsonNode.get("park").get("country").get("name").asText();
                int length = jsonNode.get("length").asInt();
                int height = jsonNode.get("height").asInt();
                int speed = jsonNode.get("speed").asInt();


                if((country.equals("country.usa"))&&(length>0) &&(height>13)&&(speed>0)){
System.out.println("success");
                }
            }
            catch(Exception e){
                System.out.println("fail");
            }
            Thread.sleep(1000);
        }
    }


    public static String APISearch(String urlString) throws Exception{
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
}
