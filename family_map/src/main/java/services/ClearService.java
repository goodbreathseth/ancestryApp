package services;


import java.sql.SQLException;

import daos.AuthtokenDAO;
import daos.UserDAO;
import database.DatabaseException;
import results.ClearResult;

/**
 * Deletes ALL data from the database, including user accounts,
 * auth tokens, and generated person and event data.
 */
public class ClearService {

    /**
     * Deletes ALL data from the database, including user accounts,
     * auth tokens, and generated person and event data
     * @return the corresponding ClearResult
     * Success Response Body:
     *        message: Clear succeeded.
     *
     *    Error Response Body:
     *        message: Description of the error
     */
    public ClearResult clear() throws SQLException, DatabaseException {
        UserDAO userDAO = new UserDAO();
        userDAO.deleteAll();

        ClearResult result = new ClearResult();
        result.setMessage("Clear succeeded");
        return result;
    }


}
