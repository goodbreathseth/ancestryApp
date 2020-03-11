package services;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import daos.EventsDAO;
import daos.PersonsDAO;
import database.DatabaseException;
import models.EventsModel;
import models.PersonsModel;
import requests.LoadRequest;
import requests.RegisterRequest;
import results.LoadResult;

/**
 * Clears all data from the database (just like the /clear API),
 * and then loads the posted user, person, and event data into the database
 */
public class LoadService {
    /**
     * Method to clear the database and repopulate it with the given
     * information from the loadRequest object
     * @param l LoadRequest object holding the users, persons, and event data to
     *          be added to the database
     * @return the corresponding LoadResult
     * Success Response Body:
     *        message: Successfully added X users, Y persons, and Z events to the database.
     * Error Response Body:
     *        message: Description of the error
     */
    public LoadResult load(LoadRequest l) throws DatabaseException, SQLException, FileNotFoundException {

        //Check if the loadRequest contains any empty values that shouldn't be empty
        LoadResult result = new LoadResult();
        if (!l.allFieldsAreFilled()) {
            result.setMessage("Not all fields have been filled");
            return result;
        }

        //Clear the database
        ClearService clearService = new ClearService();
        clearService.clear();

        //Register users by converting the userArray from load request to a userArray
        //and passing in each user in the register method
        Gson gson = new Gson();
        RegisterRequest[] registerRequest = gson.fromJson(gson.toJson(l.getUsers()), RegisterRequest[].class);
        RegisterService registerService = new RegisterService();
        int count = 0;
        for (RegisterRequest r : registerRequest) {
            if (l.getUsers()[count].getPersonID() != null)
                registerService.setPersonID(l.getUsers()[count].getPersonID());
            else
                registerService.setPersonID(null);

            registerService.register(r, false);
            count++;
        }


        //Get array of persons from the load request and insert persons into database
        PersonsModel[] personsModels = gson.fromJson(gson.toJson(l.getPersons()), PersonsModel[].class);
        PersonsDAO personsDAO = new PersonsDAO();
        for (PersonsModel p : personsModels) {
            personsDAO.addPerson(p);
        }


        //Insert events
        EventsModel[] eventsModels = gson.fromJson(gson.toJson(l.getEvents()), EventsModel[].class);
        EventsDAO eventsDAO = new EventsDAO();
        for (EventsModel e : eventsModels) {
            eventsDAO.addEvent(e);
        }


        //When completed, return a message saying how many users, events, and persons were added to database
        result.setMessage("Successfully added " + registerRequest.length + " users, " +
                personsModels.length + " persons, and " + eventsModels.length + " events to the database");
        return result;
    }
}
