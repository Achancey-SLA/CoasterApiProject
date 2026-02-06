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
    public Image image;

    public boolean includesDetails;

    public static ArrayList<Coaster> coasters;

    //{
    //  "@context": "/api/contexts/Coaster",
    //  "@id": "/api/coasters/1",
    //  "@type": "Coaster",
    //  "id": 1,
    //  "name": "Goudurix",
    //  "materialType": {
    //    "@type": "MaterialType",
    //    "@id": "/api/.well-known/genid/e7ae9c250cbe0612b4b5",
    //    "name": "Steel"
    //  },
    //  "seatingType": {
    //    "@type": "SeatingType",
    //    "@id": "/api/.well-known/genid/946b88801295b26f6e76",
    //    "name": "Sit Down"
    //  },
    //  "model": {
    //    "@id": "/api/models/43",
    //    "@type": "Model",
    //    "name": "Vekoma MK-1200"
    //  },
    //  "speed": 90,
    //  "height": 36,
    //  "length": 950,
    //  "inversionsNumber": 7,
    //  "manufacturer": {
    //    "@type": "Manufacturer",
    //    "@id": "/api/.well-known/genid/22da3b97f28a15a27715",
    //    "name": "Vekoma"
    //  },
    //  "restraint": {
    //    "@id": "/api/restraints/1",
    //    "@type": "Restraint",
    //    "name": "restraint.shoulder"
    //  },
    //  "launchs": [
    //    {
    //      "@id": "/api/launches/1",
    //      "@type": "Launch",
    //      "name": "launch.lift.chain"
    //    }
    //  ],
    //  "park": {
    //    "@id": "/api/parks/49",
    //    "@type": "Park",
    //    "name": "Parc Astérix",
    //    "country": {
    //      "@type": "Country",
    //      "@id": "/api/.well-known/genid/a7b9229d81d90b5621ff",
    //      "name": "country.france"
    //    }
    //  },
    //  "status": {
    //    "@id": "/api/statuses/1",
    //    "@type": "Status",
    //    "name": "status.operating"
    //  },
    //  "openingDate": "1989-04-30T00:00:00+02:00",
    //  "totalRatings": 3055,
    //  "validDuels": 1860,
    //  "score": "20.32258064516",
    //  "rank": 1462,
    //  "mainImage": {
    //    "@id": "/api/images/10312",
    //    "@type": "Image",
    //    "filename": "fb9e0178-982f-466b-b71a-71f1c03a6f0c.jpeg"
    //  }
    //}


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
                '}';
    }
}
