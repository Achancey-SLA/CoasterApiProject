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

        score = 0;
        random = new Random();
        viableIDs = new ArrayList<>(Arrays.asList(170,174,175,176,178,181,183,184,186,187,189,193,194,195,196,197,199,201,202,208,212,217,218,228,234,
                236,245,256,260,261,263,264,265,269,270,272,275,277,282,283,284,285,288,289,302,303,304,305,306,307,311,314,315,324,326,358,360,361,362,
                363,364,365,370,381,390,392,400,403,404,409,410,411,412,414,418,419,420,423,424,425,427,428,429,430,432,438,440,448,449,451,456,458,459,
                460,461,462,464,465,467,468,469,470,471,472,473,475,489,490,492,494,495,496,501,504,505,506,516,517,520,521,522,524,525,526,527,529,530,
                533,534,535,536,537,538,559,560,566,568,569,570,571,574,575,576,577,580,582,584,587,590,593,594,595,596,613,614,615,617,619,620,621,622,
                624,632,633,651,652,656,657,661,663,688,689,690,693,717,718,721,722,728,729,730,732,733,751,752,758,760,799,801,835,840,842,843,844,845,
                848,849,861,892,898,916,917,918,919,920,932,935,960,961,964,977,979,981,1002,1003,1006,1007,1008,1018,1019,1021,1022,1023,1024,1027,1030,
                1031,1032,1100,1106,1141,1145,1147,1149,1150,1151,1152,1153,1155,1157,1183,1196,1204,1206,1207,1271,1272,1311,1333,1356,1359,1390,1391,1393
                ,1396,1402,1404,1405,1446,1448,1457,1506,1514,1517,1556,1562,1564,1565,1567,1571,1572,1588,1619,1622,1633,1634,1761,1782,1783,1785,1792,1793,
                1800,1880,1908,1909,1910,1915,1918,1966,1976,1983,2054,2055,2061,2063,2066,2067,2091,2092,2093,2108,2109,2111,2129,2130,2131,2136,2155,2157,
                2158,2164,2169,2188,2201,2204,2205,2206,2208,2209,2210,2211,2229,2233,2268,2269,2270,2280,2286,2296,2297,2323,2335,2336,2337,2343,2347,2350,
                2361,2364,2365,2367,2368,2369,2370,2371,2372,2373,2375,2376,2377,2380,2388,2469,2478,2479,2480,2586,2588,2589,2596,2600,2602,2607,2744,2746,
                2770,2775,2776,2778,2801,2803,2804,2805,2807,2818,2819,2820,2825,2849,2850,2891,2945,2946,2948,3012,3020,3047,3048,3049,3055,3056,3066,3067,
                3068,3069,3082,3122,3123,3139,3141,3144,3154,3165,3182,3183,3197,3226,3228,3229,3275,3284,3286,3455,3461,3486,3492,3687,3750,3759,3766,3846,
                3880,3897,4376,4401,4543,5348,5356,5386,5387,5436,5579,5609,5644,5702,5709,5767,5774,5850,5892,5921,5928,5995,6026,6033,6048,6059,6060,6080,
                6218,6253,6254,6256,6257,6261,6262,6328,6333,6371,6420,6421,6461,6462,6465,6504));
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
            rightCoaster.park = jsonNode.get("park").get("name").asText();
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
        rightLabel.setText(rightCoaster.name+"\n("+rightCoaster.park+")");

        try{
            leftView.setImage(leftCoaster.image);
            leftLabel.setText(leftCoaster.name+ "\n("+leftCoaster.park+")");
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


}

