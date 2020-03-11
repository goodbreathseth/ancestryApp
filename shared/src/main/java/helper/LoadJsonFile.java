package helper;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import models.LoadModel;
import models.LocationData;
import models.NamesModel;

public class LoadJsonFile {
    private Gson gson = new Gson();

    private class Location {
        public String country;
        public String city;
        public double latitude;
        public double longitude;
    }

    /**
     * Method to read in the json files that contain data to create names for people
     * @return an array of loadModels containing female names, male names, and surnames
     * @throws FileNotFoundException
     */
    public NamesModel[] readInNamesJsonFiles() throws FileNotFoundException {
        NamesModel[] allNames = new NamesModel[3];

        String filePath = "json";
        String fnamesPath = filePath + "/fnames.json";
        Reader reader = new FileReader(fnamesPath);
        NamesModel fnames = gson.fromJson(reader, NamesModel.class);
        allNames[0] = fnames;

        String mnamesPath = filePath + "/mnames.json";
        reader = new FileReader(mnamesPath);
        NamesModel mnames = gson.fromJson(reader, NamesModel.class);
        allNames[1] = mnames;

        String snamesPath = filePath + "/snames.json";
        reader = new FileReader(snamesPath);
        NamesModel snames = gson.fromJson(reader, NamesModel.class);
        allNames[2] = snames;

        return allNames;
    }

    public LocationData readInLocationJsonFile() throws FileNotFoundException {

        String filePath = "json";
        String locationPath = filePath + "/locations.json";
        Reader reader = new FileReader(locationPath);
        LocationData locData = gson.fromJson(reader, LocationData.class);

        return locData;
    }

    public LoadModel readInJsonString(String json) {
        LoadModel loadModel = gson.fromJson(json, LoadModel.class);
        return loadModel;
    }
}
