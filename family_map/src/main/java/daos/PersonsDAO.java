package daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
import database.DatabaseException;
import models.PersonsModel;

/**
 * Persons data access object to create, read, update, delete persons
 */
public class PersonsDAO extends Database {
    private Database db;

    {
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PersonsDAO() throws SQLException {
    }

    /**
     * Method to get a person from the Server using their personID
     * @param personID unique string used to search for and identify person
     * @param authtoken to identify user from table
     * @return the corresponding Person object
     */
    public PersonsModel getPerson(String personID, String authtoken) {
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

            //Find out how many persons are in the persons table to know the
            //max size of what to initialize the array to
            insert = "SELECT Count(*) AS total FROM persons WHERE descendant = ? ";
            stmt = conn.prepareStatement(insert);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            int sizeOfArr = 0;
            rs.next();
            sizeOfArr = rs.getInt("total");

            //If there are no persons in persons table,
            //close database and personsModel with personID saying there are no persons
            if (sizeOfArr == 0) {
                // Close database connection
                db.closeConnection(true);
                PersonsModel personsModel = new PersonsModel();
                personsModel.setPersonID("No persons in database for current user");
                return personsModel;
            }


            //Query persons table for the specific personID and check if it's descendant matches
            //with the userName of the user if it wasn't, set personID as "no persons related
            //to user", else return the found personsModel
            PersonsModel personsModel = new PersonsModel();
            insert = "SELECT personID, descendant, firstName, lastName, gender, " +
                    "father, mother, spouse from persons WHERE personID = ? ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            boolean rsWasEmpty = true;
            while (rs.next()) {
                if (rs.getString("descendant").equals(userName)) {
                    rsWasEmpty = false;
                    personsModel.setPersonID(rs.getString("personID"));
                    personsModel.setDescendant(rs.getString("descendant"));
                    personsModel.setFirstName(rs.getString("firstName"));
                    personsModel.setLastName(rs.getString("lastName"));
                    personsModel.setGender(rs.getString("gender"));
                    personsModel.setFather(rs.getString("father"));
                    personsModel.setMother(rs.getString("mother"));
                    personsModel.setSpouse(rs.getString("spouse"));
                }
            }

            //If the rs was null, meaning that the query did not provide any data,
            //write "no persons related to user found in database" in personID
            if (rsWasEmpty) {
                // Close database connection
                db.closeConnection(true);

                personsModel.setPersonID("No persons related to user found in database");
                return personsModel;
            }

            // Close database connection
            db.closeConnection(true);
            return personsModel;
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
     * Method to return all family members of the current user
     * @param authtoken unique String to determine the user
     * @return Array of the PersonsModels
     */
    public PersonsModel[] getAllFamilyMembersOfUser (String authtoken) {
        //Using the provided authtoken, query the database in the authtoken table
        //for any entries with this token, if it finds one, use the UserID provided
        //in the same row to call all family members related to that user
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

            //Find out how many persons are in the persons table to know the
            //max size of what to initialize the array to
            insert = "SELECT Count(*) AS total FROM persons WHERE descendant = ? ";
            stmt = conn.prepareStatement(insert);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            int sizeOfArr = 0;
            rs.next();
            sizeOfArr = rs.getInt("total") + 1;

            //If there are no persons in persons table,
            //close database and return PersonsModel array of size 0
            if (sizeOfArr == 0) {
                // Close database connection
                db.closeConnection(true);
                PersonsModel[] personsModels = new PersonsModel[0];
                return personsModels;
            }


            //With the username that was found, query database for ALL family members of the user
            //Use "count" variable to keep track of where to place the new personModels
            PersonsModel[] personsModels = new PersonsModel[sizeOfArr];
            int count = 0;
            insert = "SELECT personID, descendant, firstName, lastName, gender, " +
                    "father, mother, spouse from persons WHERE descendant = ? ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                personsModels[count] = new PersonsModel();
                personsModels[count].setPersonID(rs.getString("personID"));
                personsModels[count].setDescendant(rs.getString("descendant"));
                personsModels[count].setFirstName(rs.getString("firstName"));
                personsModels[count].setLastName(rs.getString("lastName"));
                personsModels[count].setGender(rs.getString("gender"));
                personsModels[count].setFather(rs.getString("father"));
                personsModels[count].setMother(rs.getString("mother"));
                personsModels[count].setSpouse(rs.getString("spouse"));
                count++;
            }

            // Close database connection
            db.closeConnection(true);

            return personsModels;
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
     * Method adds a person to the database
     * @param p Person object to add
     * @return True if person successfully added, false if otherwise
     */
    public boolean addPerson(PersonsModel p) {

        try {

            // Open database connection
            conn = db.openConnection();

            // Insert a user Tuple
            String insert = "insert into persons values ( ?, ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, p.getPersonID());                              // throws SQLException
            stmt.setString(2, p.getDescendant());                       // throws SQLException
            stmt.setString(3, p.getFirstName());            // throws SQLException
            stmt.setString(4, p.getLastName());
            stmt.setString(5, p.getGender());
            stmt.setString(6, p.getFather());
            stmt.setString(7, p.getMother());
            stmt.setString(8, p.getSpouse());
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
