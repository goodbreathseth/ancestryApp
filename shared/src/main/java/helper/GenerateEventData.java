package helper;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.UUID;

import models.EventsModel;
import models.LocationData;

public class GenerateEventData {
    private EventsModel[] arrOfEvents;
    private String[] arrayOfCities;
    private EventsModel birth;
    private EventsModel baptism;
    private EventsModel marriage;
    private EventsModel death;
    private LoadJsonFile loader;
    private LocationData locationData;


    public GenerateEventData() throws FileNotFoundException {

        //arrayOfCities = new String[]{"Phoenix", "Dallas", "St. Louis", "Cleveland", "San Fransisco", "Boston", "Baltimore"};
        arrOfEvents = new EventsModel[4];

        loader = new LoadJsonFile();
        locationData = loader.readInLocationJsonFile();

    }

    public EventsModel[] generateNewEvents(String descendant, String personID, int startYear, boolean isMarried, boolean isDead) {

        int randomNum = (int)(Math.random() * locationData.data.length);
        birth = new EventsModel(UUID.randomUUID().toString(), descendant, personID, Double.parseDouble(locationData.data[(randomNum)].latitude),
                Double.parseDouble(locationData.data[(randomNum)].longitude), locationData.data[(randomNum)].country,
                locationData.data[(randomNum)].city, "Birth", startYear);
        arrOfEvents[0] = birth;

        randomNum = (int)(Math.random() * locationData.data.length);
        baptism = new EventsModel(UUID.randomUUID().toString(), descendant, personID, Double.parseDouble(locationData.data[(randomNum)].latitude),
                Double.parseDouble(locationData.data[(randomNum)].longitude), locationData.data[(randomNum)].country,
                locationData.data[(randomNum)].city, "Baptism", (startYear + 8));
        arrOfEvents[1] = baptism;

        if (isMarried) {
            randomNum = (int)(Math.random() * locationData.data.length);
            marriage = new EventsModel(UUID.randomUUID().toString(), descendant, personID, Double.parseDouble(locationData.data[(randomNum)].latitude),
                    Double.parseDouble(locationData.data[(randomNum)].longitude), locationData.data[(randomNum)].country,
                    locationData.data[(randomNum)].city, "Marriage", (startYear + 25));
            arrOfEvents[2] = marriage;
        }

        if (isDead) {
            randomNum = (int)(Math.random() * locationData.data.length);
            death = new EventsModel(UUID.randomUUID().toString(), descendant, personID, Double.parseDouble(locationData.data[(randomNum)].latitude),
                    Double.parseDouble(locationData.data[(randomNum)].longitude), locationData.data[(randomNum)].country,
                    locationData.data[(randomNum)].city, "Death", (startYear + 78));
            arrOfEvents[3] = death;
        }

        return arrOfEvents;
    }
}
