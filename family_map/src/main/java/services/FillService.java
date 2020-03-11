package services;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import daos.EventsDAO;
import daos.PersonsDAO;
import daos.UserDAO;
import database.DatabaseException;
import helper.GenerateEventData;
import helper.GeneratePersonData;
import models.EventsModel;
import models.PersonsModel;
import models.UserModel;
import results.FillResult;

/**
 *  Populates the Server's database with generated data for
 *  the specified user name. The required "username" parameter
 *  must be a user already registered with the Server. If there is any data
 *  in the database already associated with the given user name, it is deleted.
 *  The optional generations parameter lets the caller specify the number of
 *  generations of ancestors to be generated, and must be a non-negative integer
 *  (the default is 4, which results in 31 new persons each with associated events).
 */
public class FillService {

    /**
     * Method that populates database with info from user with a specific amount
     * of generations to generate
     * @param userName name of the User to fill
     * @param generations number of generations to generate
     * @return the corresponding fillResult
     * Success Response Body:
     *        message: Successfully added X persons and Y events to the database.
     *
     *   Error Response Body:
     *        message: Description of the error
     */
    public FillResult fill (String userName, int generations) throws SQLException, DatabaseException, FileNotFoundException {
        FillResult result = new FillResult();

        //Calculate the amount of persons to create
        int numofPersons = 0;
        int tempGenerations = generations;
        while (tempGenerations > 0) {
            numofPersons += Math.pow(2, tempGenerations);
            tempGenerations -= 1;
        }

        //Query database to get the user from given userName
        UserDAO userDao = new UserDAO();
        UserModel user;
        if (userDao.userExists(userName)) {
            user = userDao.getUser(userName, "something", false);
            if (user == null) {
                result.setMessage("Incorrect password");
                return result;
            }
        }
        else {
            result.setMessage("User does not exist");
            return result;
        }

        //If generation is less than 0, the user has put in an incompatible number for generations
        //set the result message to "Incorrect generation amount" and return
        if (generations < 0) {
                result.setMessage("Incorrect generation amount");
                return result;
        }


        //Delete all persons and events in database that are related to the user
        userDao.deleteEventsAndPersonsRelatedToUser(user.getUserName());

        //Generate mother and father IDs for user
        GeneratePersonData dataGenerator = new GeneratePersonData();
        String[] idArray = new String[(int)(Math.pow(2, generations + 2))];
        int parentIdIndex = 1;

        //Create two parent IDs and place them in the parent ID array
        //the index of the array corresponds to the "node number"
        //that the person would have in the family tree if we were
        //creating a binary search tree
        String motherID = UUID.randomUUID().toString();
        idArray[parentIdIndex++] = motherID;

        String fatherID = UUID.randomUUID().toString();
        idArray[parentIdIndex++] = fatherID;


        //Create person object for user with generated mother and father userNames
        PersonsModel person;
        if (generations > 0)
            person = new PersonsModel(user.getPersonID(), user.getUserName(), user.getFirstName(),
                user.getLastName(), user.getGender(), fatherID, motherID, "n/a");
        //If user only selected 1 generation, then make a persons Object for only
        //the user.  Don't assign them a father or mother
        else
            person = new PersonsModel(user.getPersonID(), user.getUserName(), user.getFirstName(),
                    user.getLastName(), user.getGender(), null, null, "n/a");





        //Use personDAO to add the current user's personModel to the person table
        PersonsDAO personDAO = new PersonsDAO();
        personDAO.addPerson(person);

        //Create the events for the current user
        //eventArr initialized aas follows:
        //[0]: birth
        //[1]: baptism
        //[2]: marriage
        //[3]: death
        GenerateEventData eventGenerator = new GenerateEventData();
        EventsDAO eventsDAO = new EventsDAO();
        int startYear = 1990;
        EventsModel[] eventArr = eventGenerator.generateNewEvents(user.getUserName(), user.getPersonID(), startYear, false, false);
        for (EventsModel r : eventArr) {
            if (r != null) {
                //if (r.getDescendant().equals(user.getUserName()))
                //    r.setDescendant("n/a");
                eventsDAO.addEvent(r);
            }
        }


        //Create all the generation of data including person and event models
        int personIndexInTree = 1;
        int birthdate = 0;
        Random random = new Random();
        String[] firstAndLastNames;
        String firstName;
        String descendant = user.getUserName();
        int eventCount = 0;

        for (int i = 1; i <= generations; i++) {
            //Calculate the birth year for each row of the tree
            startYear -= 22;


            for (int j = 1; j <= Math.pow(2, i); j++) {
                birthdate = startYear - (random.nextInt(4));

                //If j is odd, create a mother
                //else if j is even, create a father
                if ((j & 1) == 1) {
                    //Create mother

                    //Generate a random firstAndLastNames for her
                    firstAndLastNames = dataGenerator.generateMotherName().split(" ");

                    //If this is the last generation, assign the parentID to be null
                    if (i == generations) {
                        motherID = null;
                        fatherID = null;
                    }
                    else {
                        //Create two parent IDs and place them in the parent ID array
                        motherID = UUID.randomUUID().toString();
                        idArray[parentIdIndex++] = motherID;
                        fatherID = UUID.randomUUID().toString();
                        idArray[parentIdIndex++] = fatherID;
                    }

                    //Create a person object with the created information
                    person = new PersonsModel(idArray[personIndexInTree], descendant, firstAndLastNames[0],
                            firstAndLastNames[1], "f", fatherID, motherID, idArray[personIndexInTree + 1]);

                    //Add the newly created person object to the database
                    personDAO.addPerson(person);


                }

                else {
                    //Create father

                    //Generate a random firstAndLastNames for him
                    firstName = dataGenerator.generateFatherName();

                    //If this is the last generation, assign the parentID to be null
                    if (i == generations) {
                        motherID = null;
                        fatherID = null;
                    }
                    else {
                        //Create two parent IDs and place them in the parent ID array
                        motherID = UUID.randomUUID().toString();
                        idArray[parentIdIndex++] = motherID;
                        fatherID = UUID.randomUUID().toString();
                        idArray[parentIdIndex++] = fatherID;
                    }

                    //Create a person object with the created information
                    person = new PersonsModel(idArray[personIndexInTree], descendant, firstName,
                            user.getLastName(), "m", fatherID, motherID, idArray[personIndexInTree - 1]);

                    //Add the newly created person object to the database
                    personDAO.addPerson(person);

                }

                //Create events for the persons object
                if (person.getSpouse() == null)
                    eventArr = eventGenerator.generateNewEvents(user.getUserName(),
                            idArray[personIndexInTree], birthdate, false, true);
                if (birthdate > (1990 - 78))
                    eventArr = eventGenerator.generateNewEvents(user.getUserName(),
                            idArray[personIndexInTree], birthdate, true, false);
                else
                    eventArr = eventGenerator.generateNewEvents(user.getUserName(),
                            idArray[personIndexInTree], birthdate, true, true);

                //Add the created events to the database
                for (EventsModel r : eventArr) {
                    if (r != null) {
                        eventsDAO.addEvent(r);
                        eventCount++;
                    }
                }

                //Increment the index in the personTree for the next person that is
                // created to work with
                personIndexInTree++;
            }
        }


        result.setMessage("Successfully added " + personIndexInTree + " persons and " + eventCount + " events to the database.");
        return result;
    }

    public FillResult fillError() {
        FillResult result = new FillResult();
        result.setMessage("Incorrect argument length");
        return result;
    }
}
