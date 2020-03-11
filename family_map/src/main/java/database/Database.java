package database;

import java.sql.*;
import java.util.*;

public class Database {
    protected Connection conn;
    protected PreparedStatement stmt;
    protected ResultSet results;

    //Constructor to create the four tables in the database
    public Database() throws SQLException {
        conn = null;
        stmt = null;
        results = null;

        // Open a Database Connection
        String dbname = "jdbc:sqlite:database.db";
        conn = DriverManager.getConnection(dbname); // throws SQLException


        // Create tables
	    String create1 = "CREATE TABLE if not exists user (\n" +
                "  userName text PRIMARY KEY,\n" +
                "  password text NOT NULL,\n" +
                "  email text NOT NULL,\n" +
                "  firstName text NOT NULL,\n" +
                "  lastName text NOT NULL,\n" +
                "  gender text NOT NULL,\n" +
                "  personID text NOT NULL UNIQUE\n" +
                ");";

	    String create2 = "CREATE TABLE if not exists authtoken (\n" +
                "  userName text NOT NULL,\n" +
                "  authtoken text PRIMARY KEY\n" +
                ");";

	    String create3 = "CREATE TABLE if not exists persons (\n" +
                "  personID text PRIMARY KEY,\n" +
                "  descendant text NOT NULL,\n" +
                "  firstName text NOT NULL,\n" +
                "  lastName text NOT NULL,\n" +
                "  gender text NOT NULL,\n" +
                "  father text,\n" +
                "  mother text,\n" +
                "  spouse text\n" +
                ");";

	    String create4 = "CREATE TABLE if not exists events (\n" +
                "  eventID text PRIMARY KEY,\n" +
                "  descendant text NOT NULL,\n" +
                "  personID text NOT NULL,\n" +
                "  latitude text NOT NULL,\n" +
                "  longitude text NOT NULL,\n" +
                "  country text NOT NULL,\n" +
                "  city text NOT NULL,\n" +
                "  eventType text NOT NULL,\n" +
                "  year integer NOT NULL\n" +
                ");";


	    //Place tables into database
	    stmt = conn.prepareStatement(create1);       // throws SQLException
	    stmt.executeUpdate();                             // throws SQLException
	    stmt.close();                                     // throws SQLException

        stmt = conn.prepareStatement(create2);       // throws SQLException
        stmt.executeUpdate();                             // throws SQLException
        stmt.close();                                     // throws SQLException

        stmt = conn.prepareStatement(create3);       // throws SQLException
        stmt.executeUpdate();                             // throws SQLException
        stmt.close();                                     // throws SQLException

        stmt = conn.prepareStatement(create4);       // throws SQLException
        stmt.executeUpdate();                             // throws SQLException
        stmt.close();                                     // throws SQLException

        conn.close();                               // throws SQLException
        conn = null;
    }

    private void dropTables() throws SQLException {
        // Drop all tables
        String drop = "drop table if exists user";
        stmt = conn.prepareStatement(drop);         // throws SQLException
        stmt.executeUpdate();                             // throws SQLException
        stmt.close();                                     // throws SQLException

        drop = "drop table if exists authtoken";
        stmt = conn.prepareStatement(drop);         // throws SQLException
        stmt.executeUpdate();                             // throws SQLException
        stmt.close();

        drop = "drop table if exists events";
        stmt = conn.prepareStatement(drop);         // throws SQLException
        stmt.executeUpdate();                             // throws SQLException
        stmt.close();

        drop = "drop table if exists persons";
        stmt = conn.prepareStatement(drop);         // throws SQLException
        stmt.executeUpdate();                             // throws SQLException
        stmt.close();

    }

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Open a connection to the database to allow
    //the database to be changed
    public Connection openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:database.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);

            return conn;
        }
        catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
    }

    //Close the database to commit what changes have been made
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
    }
}
