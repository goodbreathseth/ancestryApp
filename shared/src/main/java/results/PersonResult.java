package results;

/**
 * Errors: Invalid auth token, Invalid personID parameter, Requested person
 * does not belong to this user, Internal Server error
 *
 * Success Response Body:
 *      descendant: susan, // Name of user account this person belongs to
 *      personID: 7255e93e, // Person’s unique ID
 *      firstName: Stuart, // Person’s first name
 *      lastName: Klocke, // Person’s last name
 *      gender: m, // Person’s gender (m or f)
 *      father: 7255e93e // ID of person’s father ​[OPTIONAL, can be missing]
 *      mother: f42126c8 // ID of person’s mother ​[OPTIONAL, can be missing]
 *      spouse:f42126c8 // ID of person’s spouse ​[OPTIONAL, can be missing]
 * Error Response Body:
 *      message: Description of the error
 */
public class PersonResult {
    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String gender;
    private String father;
    private String mother;
    private String spouse;
    private String message;
}
