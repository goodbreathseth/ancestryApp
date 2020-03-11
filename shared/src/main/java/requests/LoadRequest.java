package requests;

import models.EventsModel;
import models.PersonsModel;
import models.UserModel;

/**
 * Holds all the necessary info for a load request such as:
 * arrays of users, persons, events
 */
public class LoadRequest {
    private UserModel users[];
    private PersonsModel persons[];
    private EventsModel events[];

    public LoadRequest(UserModel[] users, PersonsModel[] persons, EventsModel[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public LoadRequest(){}

    public UserModel[] getUsers() {
        return users;
    }

    public PersonsModel[] getPersons() {
        return persons;
    }

    public EventsModel[] getEvents() {
        return events;
    }

    public boolean allFieldsAreFilled() {
        boolean filled = true;

        if      (users == null || users.length == 0 ||
                persons == null || persons.length == 0 ||
                events == null || events.length == 0)
            filled = false;

        return filled;
    }



}
