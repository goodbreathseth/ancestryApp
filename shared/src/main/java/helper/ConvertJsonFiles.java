package helper;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class ConvertJsonFiles {
    private Gson gson = new Gson();

    public ConvertJsonFiles (boolean runFiles) throws FileNotFoundException {
        Reader reader = new FileReader("json/locations.json");
        LocationData locData = gson.fromJson(reader, LocationData.class);

    }
    class Location {
        String latitude;
        String longitude;
        String city;
        String country;
    }

    class LocationData {
        Location[] data;
    }



}
