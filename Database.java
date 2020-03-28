// Forritið má þýða og keyra svona á Windows:
//   javac Sample.java
//   java -cp .;sqlite-jdbc-....jar Database
// � Unix:
//   javac Sample.java
//   java -cp .:sqlite-jdbc-....jar Database

import java.sql.*;

public class Database
{
    static Statement stmt;
    static PreparedStatement pstmt;
    static Connection conn;

    public static void generateFlights() {

    }

    public static void generateAirports() {
        String[] Airports = {};
        String[] Countries = {};

        try {
            stmt.executeUpdate("drop table if exists Airport");
            stmt.executeUpdate("CREATE TABLE Airport ('airportname' STRING, accessability STRING, country STRING ,PRIMARY KEY(airportname))");

            pstmt.executeUpdate("insert into Airport values(?,?,?)");

            // Insert airports into db
            for (int i = 0; i < Airports.length; i++){
                pstmt.setString(1, Airports[i]);   //Note index starts with 1
                pstmt.setString(2, Airports[i]);
                pstmt.setInt(2, i);

                // For debugging:
                // System.out.println(Airports[i]);
                // System.out.println(i);

                pstmt.executeUpdate();
            }

            // pstmt.close();
            // conn.close();

        } catch(SQLException e) {
            System.out.println("Error in generateCompany");
        }
    }

    public static void generateCustomer() {
        // List of random names
        String[] Names = {"Arttu", "Lotta", "Evander", "Elva", "Helga",
            "Johanna", "Arzhel", "Konstantin", "Lindsay", "Forbes",
            "Jaakob", "Viljo", "Jenna", "Kári", "Boyd", "Yannig",
            "Ellar", "Päivi", "Máel", "Coluim", "Arnar", "Borghildur",
            "Guðríður", "Greer", "Tryggvi","Hrafnkell", "Erskine", "Gordon",
            "Anniina", "Sylvi", "Snorri", "Matthias" };

        try {
            stmt.executeUpdate("drop table if exists Customer");
            stmt.executeUpdate("CREATE TABLE Customer (username STRING, userid INTEGER, PRIMARY KEY(userid))");

            pstmt = conn.prepareStatement ("insert into Customer values(?, ?)");

            // Insert names into db
            for (int i = 0; i < Names.length; i++){
                pstmt.setString(1, Names[i]);   //Note index starts with 1
                pstmt.setInt(2, i);

                // For debugging:
                // System.out.println(Names[i]);
                // System.out.println(i);

                pstmt.executeUpdate();
            }

            // pstmt.close();
            // conn.close();

        } catch(SQLException e) {
            System.out.println("Error in generateCompany");
        }
    }

    public static void generateCompany() {
        String[] Airlines = { "Air Iceland Connect", "Icelandair", "Eagle Air",
            "Norlandair", "Finnair", "Scandinavian Airlines",
            "Ryanair", "Lufthansa Group", "easyJet",
            "SAS", "Wizz Air" };

        try {
            stmt.executeUpdate("drop table if exists Company");
            stmt.executeUpdate("CREATE TABLE Company (compname VARCHAR(128), compid INTEGER, PRIMARY KEY(compname))");
            stmt.executeUpdate("insert into Company values('ICELANDAIR', 1944)");

            pstmt = conn.prepareStatement ("insert into Company values(?, ?)");

            // Insert companies into db
            for (int i = 0; i < Airlines.length; i++){
                pstmt.setString(1, Airlines[i]);   //Note index starts with 1
                pstmt.setInt(2, i);

                // For debugging;
                // System.out.println(Airlines[i]);
                // System.out.println(i);
                pstmt.executeUpdate();
            }

            // pstmt.close();
            // conn.close();
            return;

        } catch(SQLException e) {
            System.out.println("Error in generateCompany");
        }

    }
    public static void generateSeat() {

    }

    public static void main(String[] args) throws ClassNotFoundException
    {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try {
            // Create a database connection:
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            stmt = conn.createStatement();

            //statement.setQueryTimeout(30);  // set timeout to 30 sec.
            //statement.executeUpdate("drop table if exists person");

            // Company:
            generateCompany();

            // Customer:
            generateCustomer();

            // Airport:
            stmt.executeUpdate("drop table if exists Airport");
            stmt.executeUpdate("CREATE TABLE Airport ('airportname' STRING, accessability STRING, country STRING ,PRIMARY KEY(airportname))");
            stmt.executeUpdate("insert into Airport values('Leifstod KEF', 'wheelchair accessable', 'ICE')");
            stmt.executeUpdate("insert into Airport values('Schönefeld BER', 'wheelchair accessable', 'GER')");
            stmt.executeUpdate("insert into Airport values('John F. Kennedy NY', 'wheelchair accessable', 'USA')");

            // Seat:
            stmt.executeUpdate("drop table if exists Seat");
            stmt.executeUpdate("CREATE TABLE Seat (seatnumber VARCHAR(4), flightnumber VARCHAR(8), class STRING, price VARCHAR(28), reservation STRING , PRIMARY KEY(seatnumber, flightnumber ), FOREIGN KEY(flightnumber) REFERENCES Flight(number), FOREIGN KEY(reservation) REFERENCES Customer(userid))");
            stmt.executeUpdate("insert into Seat values('01A', 'FI614', 'FIRST', 64.999, NULL)");
            stmt.executeUpdate("insert into Seat values('14D', 'FI614', 'BUS', 38.765, NULL)");
            stmt.executeUpdate("insert into Seat values('48I', 'FI614', 'ECO', 13.250, NULL)");

            // Flight:
            stmt.executeUpdate("drop table if exists Flight");
            stmt.executeUpdate("CREATE TABLE Flight (number VARCHAR(24), takeOff DATETIME, landing DATETIME, origin STRING, dest STRING, aircraft VARCHAR(128), compname VARCHAR(128), amenities STRING, PRIMARY KEY (number, takeOff, origin), FOREIGN KEY(origin) REFERENCES Airport (airportname), FOREIGN KEY(dest) REFERENCES Airport (airportname), FOREIGN KEY(compname) REFERENCES Company (compname))");
            stmt.executeUpdate("insert into Flight values('FI614',  '2020-03-27 22:30:45', '2020-03-28 05:00:00', 'Leifstod KEF', 'John F. Kennedy NY', 'Boeing737max', 'ICELANDAIR', 'Food drinks and onboard shop')");

            pstmt.close();
            conn.close();
            
            //statement.executeUpdate("insert into Flights values('FI602', '2020-03-27', 'boeing 777', 'PARIS', 'KEF')");
            /*ResultSet rs = statement.executeQuery("select * from person");
              while(rs.next())
              {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
            System.out.println("id = " + rs.getInt("id"));
              }
              rs.close();*/
        } catch(SQLException e)
        {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(conn != null)
                    conn.close();
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
