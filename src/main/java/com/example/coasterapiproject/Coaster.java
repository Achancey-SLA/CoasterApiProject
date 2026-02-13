package com.example.coasterapiproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;

@JsonIgnoreProperties(value = {"@context","@id","@type","materialType","seatingType","model",
"manufacturer","restraint","launchs","park","status","openingDate","totalRatings","validDuels","score","rank",
"mainImage","closingDate"})
public class Coaster {
    @JsonProperty("height")
    public float height;
    @JsonProperty("length")
    public float length;
    @JsonProperty("inversionsNumber")
    public int inversionsNumber;
    @JsonProperty("id")
    public int id;
    @JsonProperty("speed")
    public float speed;
    @JsonProperty("name")
    public String name;

    public String imageFileName;
    public String park;
    public String manufacturer;
    public String country;
    public Image image;

    public boolean includesDetails;

    public static ArrayList<Coaster> coasters;

    public Coaster() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getInversionsNumber() {
        return inversionsNumber;
    }

    public void setInversionsNumber(int inversionsNumber) {
        this.inversionsNumber = inversionsNumber;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " ("+park+")";
    }


    public String getInformation() {
        return "Coaster{" +
                "height=" + height +
                ", length=" + length +
                ", inversionsNumber=" + inversionsNumber +
                ", id=" + id +
                ", speed=" + speed +
                ", name='" + name + '\'' +
                ", park='" + park + '\'' +
                ", manufacturer='" + manufacturer + '\'' +" country: "+country+
                '}';
    }
}
