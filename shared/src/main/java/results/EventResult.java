package results;

/**
 * Errors: Invalid auth token, Invalid eventID parameter, Requested event does not belong to this
 * user, Internal Server error
 * Success Response Body:
 *      descendant: susan // Name of user account this event belongs to (non-empty
 *                              //    string)
 *      eventID: 251837d7, // Event’s unique ID (non-empty string)
 *      personID: 7255e93e, // ID of the person this event belongs to (non-empty string)
 *      latitude: 65.6833, // Latitude of the event’s location (number)
 *      longitude: -17.9, // Longitude of the event’s location (number)
 *      country: Iceland, // Name of country where event occurred (non-empty
 *                          //    string)
 *      city: Akureyri, // Name of city where event occurred (non-empty string)
 *      eventType: birth, // Type of event (birth, baptism, etc.) (non-empty string)
 *      year: 1912, // Year the event occurred (integer)
 *
 * Error Response Body:
 *      message: Description of the error
 */
public class EventResult {
    private String descendant;
    private String eventID;
    private String personID;
    private double latitude;

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    private String message;
}
