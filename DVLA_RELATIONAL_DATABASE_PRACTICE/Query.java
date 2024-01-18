import java.sql.*;
import java.io.*;

//class to Query the database -- adapted from the one Al showed in his boats example
public class Query {

    // array of queries
    private static String[] queries = new String[] {
                /*
                  * list the registrations (reference number, color, purchase date, MOT status,
                  * date last MOTed, renewal date tax due date, make, and owner ID) of a specified person
                  *  
            /* 0 */ "SELECT referenceNum, color, purchaseDate, MOTStatus, datelastMOTed, renewalDate, make, registration.ownerID" +
                    " FROM registration LEFT JOIN owner on registration.ownerID = owner.ownerID WHERE owner.firstName = '?' AND" + 
                    " owner.lastName = '?'",
            
                 /*
                  * list the attributes of owner (owner id, first name, last name and address`)
                  * with a specified reference number (registration number)
                  *  
            /* 1 */ "SELECT owner.ownerID, firstName, lastName, address FROM owner LEFT JOIN registration on owner.ownerID = " +
                        "registration.ownerID WHERE registration.referenceNum = '?'",
                
                 /*
                  * list the MOT status and renewal date of all cars owned by
                  *  a specified person
                  *  
            // /* 2 */ 
            "SELECT MOTStatus, renewalDate FROM registration LEFT JOIN owner on registration.ownerID = owner.ownerID " +
                    "WHERE owner.firstName = '?' AND owner.lastName = '?'",

                 /*
                  * list the registrations, renewal date, and road tax for both 6 and 12 months for
                  *  all cars owned by a specified person
                  *  
            /* 3 */ "SELECT registration.referenceNum,registration.color, registration.purchaseDate, registration.MOTStatus, " +
                        "registration.datelastMOTed, registration.renewalDate, registration.make, tax.taxRate6Months, " +
                        "tax.taxRate12Months FROM (registration INNER JOIN owner, cars, tax ON owner.ownerID = " +
                        "registration.ownerID AND registration.make = cars.make AND cars.taxClass = tax.taxClass) " +
                        "WHERE owner.firstName = '?' AND owner.lastName = '?'",

                 /*
                  * list the reference numbers, owners name and address, CO2 levels, and road tax
                  *  due for all owners in a given month 
                  *  
            /* 4 */ "SELECT registration.referenceNum, owner.firstName, owner.lastName, owner.Address, cars.COIntake, " +
                        "tax.taxRate6Months, tax.taxRate12Months FROM ((registration INNER JOIN owner ON " +
                        "registration.ownerID = owner.ownerID) INNER JOIN cars, tax ON registration.make = cars.make AND " +
                        "cars.taxClass = tax.taxClass) WHERE registration.renewalDate = '?'",

                 /*
                  * list thetax class, tax rate for 6 months, and owner first and last name
                  *  given a owner id
                  *  
            /* 5 */ "SELECT tax.taxClass, tax.taxRate6Months, owner.firstName, owner.lastName FROM (tax INNER JOIN cars, " +
                        "registration, owner ON tax.taxClass = cars.taxClass AND cars.make = registration.make AND " +
                        "registration.ownerID = owner.ownerID) WHERE owner.ownerID = '?'" };

    public Query() {

    }

    //
    public void executeQuery(int queryNum, String[] parameters) throws SQLException, IOException {
        File databaseFile = new File(DBConfiguration.DATABASE_FILENAME);

        /*
                  * checks if database exists, if it does, creates a connection through the jdbc sqlite
                  * driver and calls the do query method based on the command line argument and parameters.
                  * if the database does not exist, runs error message
        */
        if (databaseFile.exists()) {

            String url = "jdbc:sqlite:" + DBConfiguration.DATABASE_FILENAME;
            Connection connection = DriverManager.getConnection(url);

            if (queryNum >= 0 && queryNum <= 5) {
                String query = queries[queryNum];
                for (String parameter : parameters)
                    query = query.replaceFirst("[?]", parameter);
                doQuery(connection, query);
            } else {
                System.out.println("Please write query number (0-5).");
            }
            connection.close();
        } else {
            System.out
                    .println("The database " + DBConfiguration.DATABASE_FILENAME + " does not exist. Run InitialiseDB");
        }
    }

    public void doQuery(Connection connection, String query) throws SQLException {
        //creating a result set for the query
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        //retrieving meta data
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumns = metaData.getColumnCount();

        //prints error message if parameter is not valid
        if (!resultSet.isBeforeFirst()) {
            System.out.println("Enter a valid parameter.");
        }

        //prints data in result set
        while (resultSet.next()) {
            for (int i = 1; i <= numColumns; i++) {
                String columnValue = resultSet.getString(i);
                System.out.println("\n" + metaData.getColumnName(i) + ": " + columnValue);
            }
        }

        statement.close();
        resultSet.close();

    }

    public static void main(String[] args) throws SQLException, IOException {

        //checks correct arg length and either executes query or prints an error message
        if (args.length != 2) {
            System.out
                    .println("Please supply a query number (0-5) as the first argument and a parameter as the second.");
        } else {
            Query queryEngine = new Query();
            try {
                String[] parameters = args[1].split(" ");
                queryEngine.executeQuery(Integer.parseInt(args[0]), parameters);

            } catch (NumberFormatException e) {
                System.out.println("Please supply a number (0-5) as the first argument.");
            }
        }
    }
}
