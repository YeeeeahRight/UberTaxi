package com.epam.uber.data.parser.implementation;

import com.epam.uber.data.parser.HumanParser;
import com.epam.uber.logic.Human;
import com.epam.uber.logic.UberCabCompany;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHumanParser implements HumanParser {
    private static final Logger LOGGER = Logger.getLogger(JsonHumanParser.class);
    private static final String JSON_HUMANS = "humans";
    private static final String JSON_LOCATION_X = "locationCoordinateX";
    private static final String JSON_LOCATION_Y = "locationCoordinateY";
    private static final String JSON_DESTINATION_X = "destinationPointX";
    private static final String JSON_DESTINATION_Y = "destinationPointY";
    private static final String JSON_HUMAN_NAME = "name";

    private final UberCabCompany uberCabCompany;

    public JsonHumanParser(UberCabCompany uberCabCompany) {
        this.uberCabCompany = uberCabCompany;
    }

    @Override
    public List<Human> parseHumans(String dataLines) {
        LOGGER.info("Starting parsing humans...");
        JSONObject allHumansObject = new JSONObject(dataLines);
        JSONArray humanArray = allHumansObject.getJSONArray(JSON_HUMANS);
        List<Human> humans = new ArrayList<>();
        for (int i = 0; i < humanArray.length(); i++) {
            JSONObject humanObject = humanArray.getJSONObject(i);
            Human human = createHuman(humanObject);
            humans.add(human);
            LOGGER.info("Parsed human: " + human);
        }
        LOGGER.info("All humans was parsed!");
        return humans;
    }

    private Human createHuman(JSONObject humanObject) {
        int locationCoordinateX = humanObject.getInt(JSON_LOCATION_X);
        int locationCoordinateY = humanObject.getInt(JSON_LOCATION_Y);
        int destinationX = humanObject.getInt(JSON_DESTINATION_X);
        int destinationY = humanObject.getInt(JSON_DESTINATION_Y);
        String name = humanObject.getString(JSON_HUMAN_NAME);
        return new Human(locationCoordinateX, locationCoordinateY,
                destinationX, destinationY, name, uberCabCompany);
    }
}
