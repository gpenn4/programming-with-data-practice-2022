package src;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.time.YearMonth;

//class to populate the database -- adapted from the one Al showed in his boats example
public class PopulateDB {

    // initializing SQL create statements
    private static final String CREATE_TAX = "INSERT or REPLACE INTO tax (taxClass, taxRate6Months, taxRate12Months) VALUES (?,?,?)";
    private static final String CREATE_CARS = "INSERT or REPLACE INTO cars (make, COIntake, manufacturer, taxClass) VALUES (?,?,?,?)";
    private static final String CREATE_OWNERS = "INSERT or REPLACE INTO owner (ownerID, lastName, firstName, address) VALUES (?,?,?,?)";
    private static final String CREATE_REGISTRATION = "INSERT or REPLACE INTO registration (referenceNum, color, purchaseDate, MOTStatus, dateLastMOTed, renewalDate, ownerID, make) VALUES (?,?,?,?,?,?,?,?)";

    public PopulateDB() {

    }

    public void populateDB() throws SQLException, IOException {

        File databaseFile = new File(DBConfiguration.DATABASE_FILENAME);

        // calling to populate databse if it already exists
        if (databaseFile.exists()) {
            String url = "jdbc:sqlite:" + DBConfiguration.DATABASE_FILENAME;
            Connection connection = DriverManager.getConnection(url);

            System.out.println("Populating database...");

            populateTaxes(connection);
            populateCars(connection);
            populateOwners(connection);
            populateRegistrations(connection);

            System.out.println("Database populated!");

            connection.close();

        } else
            // error message if databse does not exist
            System.out
                    .println("The database " + DBConfiguration.DATABASE_FILENAME + " does not exist. Run InitialiseDB");
    }

    private void populateTaxes(Connection connection) throws SQLException, IOException {
        System.out.println("Populating with data from: " + DBConfiguration.TAX_CSV + " ...");

        // creating list of lines from tax csv file
        List<List<String>> lines = CSVReader.read(DBConfiguration.TAX_CSV, true, false);

        // loops through lines and adds to tax
        for (List<String> line : lines) {
            populateTax(connection, line);
        }

        System.out.println("Added " + lines.size() + " records from " + DBConfiguration.TAX_CSV + " to the database.");

    }

    private void populateCars(Connection connection) throws SQLException, IOException {
        System.out.println("Populating with data from: " + DBConfiguration.CARS_CSV + " ...");

        // creating list of lines from tax csv file
        List<List<String>> lines = CSVReader.read(DBConfiguration.CARS_CSV, true, false);

        // loops through lines and adds to cars
        for (List<String> line : lines) {
            populateCar(connection, line);
        }

        System.out.println("Added " + lines.size() + " records from " + DBConfiguration.CARS_CSV + " to the database.");
    }

    private void populateOwners(Connection connection) throws SQLException, IOException {
        System.out.println("Populating with data from: " + DBConfiguration.OWNERS_CSV + " ...");

        // creating list of lines from tax csv file
        List<List<String>> lines = CSVReader.read(DBConfiguration.OWNERS_CSV, true, false);

        // loops through lines and adds to owner
        for (List<String> line : lines) {
            populateOwner(connection, line);
        }

        System.out.println(
                "Added  " + lines.size() + " records from " + DBConfiguration.OWNERS_CSV + " to the database.");

    }

    private void populateRegistrations(Connection connection) throws SQLException, IOException {
        System.out.println("Populating with data from: " + DBConfiguration.REGISTRATION_CSV + " ...");

        // creating list of lines from tax csv file
        List<List<String>> lines = CSVReader.read(DBConfiguration.REGISTRATION_CSV, true, false);

        // loops through lines and adds to registration
        for (List<String> line : lines) {
            populateRegistration(connection, line);
        }

        System.out.println(
                "Added " + lines.size() + " records from " + DBConfiguration.REGISTRATION_CSV + " to the database.");

    }

    private void populateTax(Connection connection, List<String> fields) throws SQLException {

        // initializing fields
        PreparedStatement statement = connection.prepareStatement(CREATE_TAX);

        statement.setString(1, fields.get(0));                          // tax class
        statement.setDouble(2, Double.parseDouble(fields.get(1)));      // tax rate 6 months
        statement.setDouble(3, Double.parseDouble(fields.get(2)));      // tax rate 12 months

        statement.executeUpdate();
    }

    private void populateCar(Connection connection, List<String> fields) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREATE_CARS);

        // initializing fields
        statement.setString(1, fields.get(0));                          // make
        statement.setDouble(2, Double.parseDouble(fields.get(1)));      // carbon dioxide intake
        statement.setString(3, fields.get(2));                          // manufacturer
        statement.setString(4, fields.get(3));                          // tax class

        statement.executeUpdate();

    }

    private void populateOwner(Connection connection, List<String> fields) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREATE_OWNERS);

        // initializing fields
        statement.setInt(1, Integer.parseInt(fields.get(0)));   // owner ID
        statement.setString(2, fields.get(1));                  // last name
        statement.setString(3, fields.get(2));                  // first name
        statement.setString(4, fields.get(3));                  // address

        statement.executeUpdate();

    }

    private void populateRegistration(Connection connection, List<String> fields) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREATE_REGISTRATION);

        // initializing fields
        statement.setInt(1, Integer.parseInt(fields.get(0)));       // reference number
        statement.setString(2, fields.get(1));                      // color
        statement.setObject(3, YearMonth.parse(fields.get(2)));     // purchase date
        statement.setString(4, fields.get(3));                      // MOT status
        statement.setObject(5, YearMonth.parse(fields.get(4)));     // date last MOTed
        statement.setObject(6, YearMonth.parse(fields.get(5)));     // renewal date
        statement.setInt(7, Integer.parseInt(fields.get(6)));       // owner ID
        statement.setString(8, fields.get(7));                      // make

        statement.executeUpdate();

    }

    public static void main(String[] args) throws SQLException, IOException {
        PopulateDB populater = new PopulateDB();
        populater.populateDB();

    }
}
