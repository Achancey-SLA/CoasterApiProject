package com.example.coasterapiproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class IDfinder {
    static ArrayList<Integer> validIDs;
    static String validIDstring;
    static ArrayList<Integer> possibleIDs;

    public static void initializePossibleIDs(){
        possibleIDs = new ArrayList<>(Arrays.asList(170,174,175,176,178,181,182,183,184,186,187,189,193,194,195,196,197,199,201,202,208,212,216,217,218,228,234,236,245,256,260,261,263,264,265,269,270,272,275,277,282,283,284,285,288,289,302,303,304,305,306,307,311,314,315,324,326,358,360,361,362,363,364,365,370,380,381,390,392,397,398,399,400,401,402,403,404,409,410,411,412,414,418,419,420,421,422,423,424,425,427,428,429,430,432,438,440,448,449,451,456,458,459,460,461,462,464,465,467,468,469,470,471,472,473,475,482,483,484,486,487,489,490,491,492,494,495,496,501,502,504,505,506,516,517,520,521,522,524,525,526,527,529,530,532,533,534,535,536,537,538,559,560,566,567,568,569,570,571,574,575,576,577,579,580,582,584,587,590,592,593,594,595,596,613,614,615,617,619,620,621,622,624,632,633,651,652,656,657,661,663,688,689,690,693,717,718,721,722,728,729,730,731,732,733,751,752,758,760,799,801,835,840,842,843,844,845,848,849,861,892,898,916,917,918,919,920,932,935,960,961,964,977,979,981,1002,1003,1006,1007,1008,1018,1019,1021,1022,1023,1024,1027,1030,1031,1032,1062,1076,1100,1106,1141,1145,1147,1149,1150,1151,1152,1153,1155,1157,1183,1196,1197,1204,1206,1207,1220,1271,1272,1311,1333,1353,1356,1359,1390,1391,1393,1396,1402,1404,1405,1406,1439,1440,1446,1448,1457,1504,1506,1514,1517,1536,1556,1562,1564,1565,1567,1571,1572,1588,1619,1622,1633,1634,1665,1761,1782,1783,1785,1792,1793,1800,1880,1908,1909,1910,1915,1918,1922,1964,1966,1976,1983,2054,2055,2061,2063,2066,2067,2088,2091,2092,2093,2108,2109,2111,2129,2130,2131,2136,2155,2157,2158,2164,2169,2188,2201,2204,2205,2206,2208,2209,2210,2211,2229,2233,2268,2269,2270,2280,2286,2296,2297,2323,2335,2336,2337,2343,2347,2350,2361,2364,2365,2367,2368,2369,2370,2371,2372,2373,2375,2376,2377,2380,2386,2388,2469,2478,2479,2480,2586,2588,2589,2596,2600,2602,2607,2744,2746,2770,2775,2776,2778,2801,2803,2804,2805,2807,2818,2819,2820,2825,2849,2850,2872,2891,2945,2946,2948,3012,3020,3047,3048,3049,3055,3056,3066,3067,3068,3069,3082,3122,3123,3139,3141,3144,3154,3165,3182,3183,3184,3197,3226,3228,3229,3275,3284,3286,3455,3461,3486,3492,3687,3750,3759,3766,3846,3880,3897,3905,4376,4401,4543,4643,5348,5356,5386,5387,5436,5574,5579,5609,5644,5702,5709,5713,5767,5774,5825,5829,5850,5892,5921,5928,5995,6026,6033,6048,6059,6060,6080,6182,6218,6253,6254,6256,6257,6261,6262,6265,6270,6271,6281,6282,6290,6291,6328,6333,6334,6371,6420,6421,6457,6458,6461,6462,6465,6466,6467,6504));

    }

    public static void findIDs() throws Exception{
        Random random = new Random();
        validIDstring = "";
        validIDs = new ArrayList<>();

        for(Integer i : possibleIDs) {
            System.out.println(i);

            try{
                String output = APISearch("https://captaincoaster.com/api/coasters/" + i);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(output);
                String country = jsonNode.get("park").get("country").get("name").asText();
                System.out.println(jsonNode.get("name").asText());
                int length = jsonNode.get("length").asInt();
                int height = jsonNode.get("height").asInt();
                int speed = jsonNode.get("speed").asInt();
                String imageFileName = jsonNode.get("mainImage").get("filename").asText();

                if((country.equals("country.usa"))&&(length>0) &&(height>13)&&(speed>0)&&imageFileName!=null){
                System.out.println("success");
                validIDs.add(i);
                validIDstring+=i+",";
                }
                else{
                    System.out.println("ineligible");
                }

            }
            catch(Exception e){
                System.out.println("fail"+e.toString());
            }
            System.out.println(validIDstring);
            Thread.sleep(random.nextInt(0,300));
        }
    }

    public static void findPossibleIDs() throws Exception{
        String IDSListString = "";
        for(int i =1; i<200;i++){
            String output = APISearch("https://captaincoaster.com/api/coasters?page="+i+"&order%5Bid%5D=asc&order%5Brank%5D=asc");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(output);

            JsonNode jsonCoasterArray = jsonNode.get("member");
            for (JsonNode eachCoaster : jsonCoasterArray) {

                int id = eachCoaster.get("id").asInt();
                IDSListString+= id+",";
                System.out.println(eachCoaster.get("name").asText() + id);

            }
            System.out.println(IDSListString);
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
