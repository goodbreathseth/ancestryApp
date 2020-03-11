package services;

import com.google.gson.Gson;

import java.sql.SQLException;

import daos.EventsDAO;
import models.AuthtokenModel;
import models.EventsModel;
import results.EventAllResult;
import results.EventResult;

/**
 * Returns the single Event object with the specified ID
 */
public class EventService {
    /**
     * Returns the single Event object with the specified ID
     * @param a authToken to verify user
     * @param eventID to search for Event object
     * @return corresponding EventResult
     * Success Response Body:
     *        descendant: susan // Name of user account this event belongs to (non-empty
     *                                //    string)
     *        eventID: 251837d7, // Event’s unique ID (non-empty string)
     *        personID: 7255e93e, // ID of the person this event belongs to (non-empty string)
     *        latitude: 65.6833, // Latitude of the event’s location (number)
     *        longitude: -17.9, // Longitude of the event’s location (number)
     *        country: Iceland, // Name of country where event occurred (non-empty
     *                            //    string)
     *        city: Akureyri, // Name of city where event occurred (non-empty string)
     *        eventType: birth, // Type of event (birth, baptism, etc.) (non-empty string)
     *        year: 1912, // Year the event occurred (integer)
     *
     *   Error Response Body:
     *        message: Description of the error
     */
    public EventResult event(AuthtokenModel a, String eventID) throws SQLException {
        EventResult result = new EventResult();

        //Check if the authtoken was provided.  If it wasn't return a
        //result with a message saying that the authtoken was not given
        if (a.getToken().equals("")) {
            result.setMessage("Authtoken was not given");
            return result;
        }

        //Using the provided authtoken, query the database in the authtoken table
        //for any entries with this token, if it finds one, use the userName provided
        //and call the eventID.  Make sure that event is related to the user
        EventsDAO eventsDAO = new EventsDAO();
        EventsModel eventsModel = eventsDAO.getEvent(eventID, a.getToken());

        //Handle any errors I could have
        if (eventsModel == null) {
            result.setMessage("No user found from authtoken provided");
            return result;
        }
        if (eventsModel.getEventID().equals("No events in database")) {
            result.setMessage(eventsModel.getEventID());
            return result;
        }
        if (eventsModel.getEventID().equals("No specific event related to user found in database")) {
            result.setMessage(eventsModel.getEventID());

            return result;
        }


        //If everything turned out correct, convert the eventsModel to a eventsResult
        //and return
        Gson gson = new Gson();
        result = gson.fromJson(gson.toJson(eventsModel), EventResult.class);
        return result;
    }

    public EventAllResult event(AuthtokenModel a) throws SQLException {
        EventAllResult result = new EventAllResult();

        //Check if the authtoken was provided.  If it wasn't return a
        //result with a message saying that the authtoken was not given
        if (a.getToken().equals("")) {
            result.setMessage("Authtoken was not given");
            return result;
        }

        //Using the provided authtoken, query the database in the authtoken table
        //for any entries with this token, if it finds one, use the EventID provided
        //in the same row to call all events related to that user
        EventsDAO eventsDAO = new EventsDAO();
        EventsModel[] eventsModels = eventsDAO.getAllEventsForUser(a.getToken());
        if (eventsModels == null) {
            result.setMessage("No user found from provided authtoken");
            return result;
        }
        if (eventsModels.length == 0) {
            result.setMessage("No events found related to the user");
            return result;
        }
        result.setData(eventsModels);

        return result;
    }

    public EventResult eventError(String s) {
        EventResult result = new EventResult();
        result.setMessage(s);
        return result;
    }
}
