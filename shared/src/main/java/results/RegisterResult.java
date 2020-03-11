package results;

/**
 * Errors: Request property missing or has invalid
 * value, Username already taken by another user,
 * Internal Server error
 *
 * Success Response Body: { authToken: cf7a368f, // Non-empty auth token string
 * userName: susan, // User name passed in with request
 * personID: 39f9fe46 // Non-empty string containing the Person ID of the
 * //    user’s generated Person object }
 *
 * Error Response Body:
 * { message: Description of the error }
 */
public class RegisterResult {
    public String authToken;
    private String userName;
    private String personID;
    private String message;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
