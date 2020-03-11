package helper;

import java.io.FileNotFoundException;

import models.NamesModel;

public class GeneratePersonData {

    //arrayOfNames[0] contains String[] of female names
    //arrayOfNames[1] contains String[] of male names
    //arrayOfNames[2] contains String[] of surnames
    private NamesModel[] arrayOfNames;
    private LoadJsonFile loader;

    //Constructor to read in json files to get all the info necessary to create names
    public GeneratePersonData() throws FileNotFoundException {
        loader = new LoadJsonFile();
        arrayOfNames = loader.readInNamesJsonFiles();

    }

    //Method to generate female names using the female and surname String arrays
    public String generateMotherName() {
        String[] fnames = arrayOfNames[0].getData();
        String firstName = fnames[(int )(Math.random() * fnames.length + 1)];

        String[] snames = arrayOfNames[2].getData();
        String surname = snames[(int )(Math.random() * fnames.length + 1)];
        return firstName + " " + surname;
    }

    //Method to generate a random father's first name.  We will be using
    //the same surname as the current personModel so one will not need to be generated
    public String generateFatherName() {
        String[] mnames = arrayOfNames[1].getData();
        String firstName = mnames[(int )(Math.random() * mnames.length + 1)];

        //String[] snames = arrayOfNames[2].getData();
        //String surname = snames[(int )(Math.random() * fnames.length + 1)];
        return firstName;
    }
}
