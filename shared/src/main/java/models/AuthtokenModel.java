package models;

/**
 * Model class to hold all necessary info for an Authtoken
 */
public class AuthtokenModel {

    private String userName;
    private String token;

    public AuthtokenModel(){}

    public AuthtokenModel(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
