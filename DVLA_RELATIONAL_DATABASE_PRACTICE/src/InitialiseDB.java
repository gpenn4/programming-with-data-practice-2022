package src;
import java.util.*;
import java.io.*;
import java.sql.*;

//class to initialise the database -- adapted from the one Al showed in his boats example
public class InitialiseDB {

    public void createDB() throws SQLException, IOException {
        // intializing connection
        Connection connection = null;

        try {
            // initializing database file
            File db_file = new File(DBConfiguration.DATABASE_FILENAME);
            // deletes databse file if already exists
            if (db_file.exists()) {
                System.out.println("Deleting pre-existing database file...");
                boolean deleted = db_file.delete();
                System.out.println("Pre-existing file deleted " + deleted);
            }

            // initializing driver
            String dbURL = "jdbc:SQLite:" + DBConfiguration.DATABASE_FILENAME;
            connection = DriverManager.getConnection(dbURL);

            // calling the create tables method
            createTables(connection);
            System.out.println("DB created");
            connection.close();

        } catch (SQLException e) {
            System.out.println("DB not created: SQL exception " + e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    // method to create tables
    public void createTables(Connection connection) throws SQLException,
            IOException {
        Statement statement = connection.createStatement();

        //retrieving schema as array list
        ArrayList<String> commands = readSchema();

        //creating tables from schema commands
        for (int i = 0; i < commands.size(); i++) {
            statement.executeUpdate(commands.get(i));
        }

        statement.close();

    }

    private static ArrayList<String> readSchema() throws IOException {
        //initializing array list of commands
        ArrayList<String> commands = new ArrayList<>();

        //reading through ddl file and adding schema to commands list
        try (BufferedReader reader = new BufferedReader(new FileReader(DBConfiguration.SCHEMA_FILENAME));) {

            String line;
            String lines = "";
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    lines += line;
                }
            }
            String str[] = lines.split(";");

            for (String s : str) {
                commands.add(s + ";");
            }
        }
        return commands;
    }

    public static void main(String[] args) throws IOException {
        //calling create databse method
        try {
            InitialiseDB database = new InitialiseDB();
            database.createDB();
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e);
        }

    }
}