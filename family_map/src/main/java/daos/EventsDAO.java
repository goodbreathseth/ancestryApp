package daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import database.DatabaseException;
import models.EventsModel;

/**
 * Events data access object to create, read, update, delete Events
 */
public class EventsDAO extends Database {

    private Database db;

    {
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventsDAO() throws SQLException {
    }

    /**
     * Method to get an event from Server by eventID
     * @param eventID unique String to search for the Event by
     * @param authtoken
     * @return corresponding Event object
     */
    public EventsModel getEvent(String eventID, String authtoken) {
        try {

            // Open database connection
            conn = db.openConnection();

            // Query database for userName
            String insert = "SELECT userName from authtoken WHERE authtoken = ? ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, authtoken);    // throws SQLException

            String userName = "";
            ResultSet rs = stmt.executeQuery();                             // throws SQLException
            while (rs.next())
                userName = rs.getString("userName");

            //If a user wasn't found from the authtoken, return null
            if (userName == null || userName.equals("")) {
                // Close database connection
                db.closeConnection(true);
                return null;
            }

            //Find out how many events are in the events table to know the
            //max size of what to initialize the array to
            insert = "SELECT Count(*) AS total FROM events WHERE descendant = ? ";
            stmt = conn.prepareStatement(insert);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            int sizeOfArr = 0;
            rs.next();
            sizeOfArr = rs.getInt("total");

            //If there are no events in events table,
            //close database and eventsModel with eventID saying there are no events
            if (sizeOfArr == 0) {
                // Close database connection
                db.closeConnection(true);
                EventsModel eventsModel = new EventsModel();
                eventsModel.setEventID("No event in database for current user");
                return eventsModel;
            }


            //Query events table for the specific eventID and check if it's descendant matches
            //with the userName of the user if it wasn't, set eventID as "no events related
            //to user", else return the found eventsModel
            EventsModel eventsModel = new EventsModel();
            insert = "SELECT eventID, descendant, personID, latitude, longitude, " +
                    "country, city, eventType, year from events WHERE eventID = ? ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            boolean rsWasEmpty = true;
            while (rs.next()) {
                if (rs.getString("descendant").equals(userName)) {
                    rsWasEmpty = false;
                    eventsModel.setEventID(rs.getString("eventID"));
                    eventsModel.setDescendant(rs.getString("descendant"));
                    eventsModel.setPersonID(rs.getString("personID"));
                    eventsModel.setLatitude(rs.getDouble("latitude"));
                    eventsModel.setLongitude(rs.getDouble("longitude"));
                    eventsModel.setCountry(rs.getString("country"));
                    eventsModel.setCity(rs.getString("city"));
                    eventsModel.setEventType(rs.getString("eventType"));
                    eventsModel.setYear(rs.getInt("year"));
                }
            }

            //If the rs was null, meaning that the query did not provide any data,
            //write "no events related to user found in database" in EventID
            if (rsWasEmpty) {
                // Close database connection
                db.closeConnection(true);

                eventsModel.setEventID("No events related to user found in database");
                return eventsModel;
            }

            // Close database connection
            db.closeConnection(true);
            return eventsModel;
        }

        catch (SQLException ex) {
            try {
                db.closeConnection(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Method to return all events for all family members of the user
     * @param authtoken unique string used to identify the current user
     * @return Arraylist of all the events related to user's family
     */
    public EventsModel[] getAllEventsForUser (String authtoken) {
        //Using the provided authtoken, query the database in the authtoken table
        //for any entries with this token, if it finds one, use the UserID provided
        //in the same row to call all events related to that user
        try {

            // Open database connection
            conn = db.openConnection();

            // Query database for userName
            String insert = "SELECT userName from authtoken WHERE authtoken = ? ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, authtoken);    // throws SQLException

            String userName = "";
            ResultSet rs = stmt.executeQuery();                             // throws SQLException
            while (rs.next())
                userName = rs.getString("userName");

            //If a user wasn't found from the authtoken, return null
            if (userName == null || userName.equals("")) {
                // Close database connection
                db.closeConnection(true);
                return null;
            }

            //Find out how many events are in the events table to know the
            //max size of what to initialize the array to
            insert = "SELECT Count(*) AS total FROM events WHERE descendant = ? ";
            stmt = conn.prepareStatement(insert);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            int sizeOfArr = 0;
            rs.next();
            sizeOfArr = rs.getInt("total");// + 2;

            //If there are no events in events table,
            //close database and return EventsModel array of size 0
            if (sizeOfArr == 0) {
                // Close database connection
                db.closeConnection(true);
                EventsModel[] eventsModels = new EventsModel[0];
                return eventsModels;
            }


            //With the username that was found, query database for ALL events of the user
            //by searching for their name in the descendant line
            // Use "count" variable to keep track of where to place the new eventsModels
            EventsModel[] eventsModels = new EventsModel[sizeOfArr];
            int count = 0;
            insert = "SELECT eventID, descendant, personID, latitude, longitude, " +
                    "country, city, eventType, year from events WHERE descendant = ? ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                eventsModels[count] = new EventsModel();
                eventsModels[count].setEventID(rs.getString("eventID"));
                eventsModels[count].setDescendant(rs.getString("descendant"));
                eventsModels[count].setPersonID(rs.getString("personID"));
                eventsModels[count].setLatitude(rs.getDouble("latitude"));
                eventsModels[count].setLongitude(rs.getDouble("longitude"));
                eventsModels[count].setCountry(rs.getString("country"));
                eventsModels[count].setCity(rs.getString("city"));
                eventsModels[count].setEventType(rs.getString("eventType"));
                eventsModels[count].setYear(rs.getInt("year"));
                count++;
            }

            //Query database again for the user's event objects.
            //There should be two remaining events to retrieve, which
            // are the user's birth and baptism
            insert = "SELECT eventID, descendant, personID, latitude, longitude, " +
                    "country, city, eventType, year from events WHERE descendant = ? ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, "n/a");
            rs = stmt.executeQuery();
            while (rs.next()) {
                eventsModels[count] = new EventsModel();
                eventsModels[count].setEventID(rs.getString("eventID"));
                eventsModels[count].setDescendant(rs.getString("descendant"));
                eventsModels[count].setPersonID(rs.getString("personID"));
                eventsModels[count].setLatitude(rs.getDouble("latitude"));
                eventsModels[count].setLongitude(rs.getDouble("longitude"));
                eventsModels[count].setCountry(rs.getString("country"));
                eventsModels[count].setCity(rs.getString("city"));
                eventsModels[count].setEventType(rs.getString("eventType"));
                eventsModels[count].setYear(rs.getInt("year"));
                count++;
            }

            // Close database connection
            db.closeConnection(true);

            return eventsModels;
        }

        catch (SQLException ex) {
            try {
                db.closeConnection(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }


        return null;
    }

    /**
     * Method to add an Event to the database
     * @param m Event object to add
     * @return True if event successfully added.  False if otherwise
     */
    public boolean addEvent(EventsModel m) {
        try {

            // Open database connection
            conn = db.openConnection();

            // Insert a user Tuple
            String insert = "insert into events values ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, m.getEventID());                              // throws SQLException
            stmt.setString(2, m.getDescendant());                       // throws SQLException
            stmt.setString(3, m.getPersonID());            // throws SQLException
            stmt.setDouble(4, m.getLatitude());
            stmt.setDouble(5, m.getLongitude());
            stmt.setString(6, m.getCountry());
            stmt.setString(7, m.getCity());
            stmt.setString(8, m.getEventType());
            stmt.setInt(9, m.getYear());
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();                                     // throws SQLException

            // Close database connection
            db.closeConnection(true);
        }

        catch (SQLException ex) {
            try {
                db.closeConnection(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }

        return true;
    }

}
