// Authors: Hrafnkell Sigurðarson, Kári Viðar Jónsson.
//
// Forritið má þýða og keyra svona á Windows:
//   javac Sample.java
//   java -cp .;sqlite-jdbc-....jar Database
//
//   Unix:
//   javac Sample.java
//   java -cp .:sqlite-jdbc-....jar Database

import java.sql.*;
import java.util.Random;

public class Database {

    static Statement         stmt;
    static PreparedStatement pstmt;
    static Connection        conn;

    public static void generateFlights() {
        
    }

    public static void generateAirports() {
        // Giant string mess
        String[] Airports = { "Leifstod Airport, KEF", "Schönefeld Airport, BER", "Stuttgart Airport, STR", "Comiso Airport, CIY",
            "Florence Airport, FLR", "Genoa Airport, GOA", "Munich Airport, MUC", "Strasbourg Airport, SXB",
            "Toulon-Hyères Airport, TLN", "Bremen Airport, BRE", "Nuremberg Airport, NUE", "Kerry Airport, KIR",
            "Shannon Airport, SNN", "Alghero Airport, AHO", "Ancona Airport, AOI", "Bari Airport, BRI",
            "Bergamo Airport, BGY", "Bologna Airport, BLQ", "Brindisi Airport, BDS", "Cagliari Airport, CAG",
            "Kutaisi Airport, KUT", "Tbilisi Airport, TBS", "Allgäu Airport, FMM", "Palermo Airport, PMO",
            "Perugia Airport, PEG", "Pescara Airport, PSR", "Pisa Airport, PSA", "Dortmund Airport, DTM",
            "Dresden Airport, DRS", "Düsseldorf Airport, DUS", "Frankfurt Airport, FRA", "Frankfurt-Hahn Airport, HHN",
            "Friedrichshafen Airport, FDH", "Hamburg Airport, HAM", "Hanover Airport, HAJ", "Weeze Airport, NRN",
            "Athens Airport, ATH", "Chania Airport, CHQ", "Corfu Airport, CFU", "Heraklion Airport, HER",
            "Kos Airport, KGS", "Mykonos Airport, JMK", "Rhodes Airport, RHO", "Santorini Airport, JTR",
            "Thessaloniki Airport, SKG", "Zante Airport, ZTH", "Budapest Airport, BUD", "Debrecen Airport, DEB",
            "Keflavik Airport, KEF", "Cork Airport, ORK", "Dublin Airport, DUB", "Tirana Airport, TIA",
            "Graz Airport, GRZ", "Innsbruck Airport, INN", "Klagenfurt Airport, KLU", "Linz Airport, LNZ",
            "Salzburg Airport, SZG", "Vienna Airport, VIE", "Baku Airport, GYD", "Minsk Airport, MSQ",
            "Antwerp Airport, ANR", "Brussels Airport, BRU", "Charleroi Airport, CRL", "Liege Airport, LGG",
            "Ostend-Bruges Airport, BOS", "Sarajevo Airport, SJJ", "Tuzla Airport, TZL", "Burgas Airport, BOJ",
            "Sofia Airport, SOF", "Varna Airport, VAR", "Dubrovnik Airport, DBV", "Pula Airport, PUY",
            "Split Airport, SPU", "Zadar Airport, ZAD", "Zagreb Airport, ZAG", "Larnaca Airport, LCA",
            "Paphos Airport, PFO", "Brno Airport, BRQ", "Prague Airport, PRG", "Aalborg Airport, AAL",
            "Aarhus Airport, AAR", "Billund Airport, BLL", "Copenhagen Airport, CPH", "Vágar Airport, FAE",
            "Tallinn Airport, TLL", "Helsinki Airport, HEL", "Oulu Airport, OUL", "Rovaniemi Airport, RVN",
            "Tampere Airport, TMP", "Turku Airport, TKU", "Vaasa Airport, VAA", "Ajaccio Airport, AJA",
            "Bastia Airport, BIA", "Bergerac Airport, EGC", "Biarritz Airport, BIQ", "Bordeaux Airport, BOD",
            "Brest-Bretagne Airport, BES", "Lille Airport, LIL", "Marseille Airport, MRS", "Montpellier Airport, MPL",
            "Nantes Airport, NTE", "Nice Airport, NCE", "John F. Kennedy Airport, NY" };

        String[] Countries = { "Iceland", "Germany", "Germany", "Italy", "Italy", "Italy", "Germany", "France", "France", "Germany",
            "Germany", "Ireland", "Ireland", "Italy", "Italy", "Italy", "Italy", "Italy", "Italy", "Italy",
            "Georgia", "Georgia", "Memmingen", "Italy", "Italy", "Italy", "Italy", "Germany", "Germany", "Germany",
            "Germany", "Germany", "Germany", "Germany", "Germany", "Germany", "Greece", "Greece", "Greece", "Greece",
            "Greece", "Greece", "Greece", "Greece", "Greece", "Greece", "Hungary", "Hungary", "Iceland", "Ireland",
            "Ireland", "Albania", "Austria", "Austria", "Austria", "Austria", "Austria", "Austria", "Azerbaijan", "Belarus",
            "Belgium", "Belgium", "Belgium", "Belgium", "Belgium", "Bosnia-Herzegovina", "Bosnia-Herzegovina", "Bulgaria", "Bulgaria", "Bulgaria",
            "Croatia", "Croatia", "Croatia", "Croatia", "Croatia", "Cyprus", "Cyprus", "Czech-Republic", "Czech-Republic", "Denmark",
            "Denmark", "Denmark", "Denmark", "Denmark", "Estonia", "Finland", "Finland", "Finland", "Finland", "Finland",
            "Finland", "France", "France", "France", "France", "France", "France", "France", "France", "France", "France", "France", "USA" };

        Random rand = new Random(); String accessibility;
        int n = 0;
        try {
            stmt.executeUpdate("drop table if exists Airport");
            stmt.executeUpdate("CREATE TABLE Airport ('airportname' STRING, accessibility STRING, country STRING ,PRIMARY KEY(airportname))");

            pstmt = conn.prepareStatement("insert into Airport values(?,?,?)");

            // Insert airports into db
            for (int i = 0; i < Airports.length; i++){

                n = rand.nextInt(2);
                switch (n) {
                   case 0:
                       accessibility = "Wheelchair-accessible";
                       break;
                   case 1:
                       accessibility = "Not wheelchair-accessible";
                       break;
                   default:
                       accessibility = "Unknown";
                }

                pstmt.setString(1, Airports[i]   ); //Note index starts with 1
                pstmt.setString(2, accessibility );
                pstmt.setString(3, Countries[i]  );

                // For debugging:
                System.out.println(Airports[i] + " -- " + Countries[i]);
                System.out.println(accessibility);

                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();

        } catch(SQLException e) {
            System.out.println("Error in generateAirport");
            System.err.println(e.getMessage());
        }
        return;
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
                System.out.println(i + " : " +  Names[i]);

                // pstmt.executeUpdate();
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();

        } catch(SQLException e) {
            System.out.println("Error in generateCustomer");
            System.err.println(e.getMessage());
        }
        return;
    }

    public static void generateCompany() {
        String[] Airlines = { "Air Iceland Connect", "Icelandair", "Eagle Air",
            "Norlandair", "Finnair", "Scandinavian Airlines",
            "Ryanair", "Lufthansa Group", "easyJet", "SAS", "Wizz Air" };

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
                System.out.println(Airlines[i]);
                System.out.println(i);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();

        } catch(SQLException e) {
            System.out.println("Error in generateCompany");
            System.err.println(e.getMessage());
        }
        return;
    }

    public static void generateSeat() {
        // Generate random Seats
        int price, n, r, r2 = 0;
        String classtype;

        Random rand = new Random();

        String[] Seats = { "01A", "14D", "48I"};
        String[] FlightNR = new String[Seats.length];
        String[] FlightPrefix = { "5X", "AA", "AC", "AM", "AS", "AY", "B6", "BA",
            "BC", "BG", "CI", "CP", "D5", "D7", "DJ", "DL",
            "EK", "EY", "F",  "FX", "HA", "JL", "KE", "LA",
            "LH", "LJ", "LO", "LY", "MH", "MM", "NH", "NK",
            "NU", "NZ", "QF", "QK", "QR", "SG", "SK", "SQ",
            "TK", "TN", "UA", "VA", "WN", "WS",
        };

        for (int i = 0; i < Seats.length; i++){
            r  = rand.nextInt(FlightPrefix.length);

            r2 = rand.nextInt(999);
            FlightNR[i] = "" + FlightPrefix[r] + (1+r2);
            System.out.print("FLightnr: " + FlightNR[i] + "\n" );
        };

        try {
            stmt.executeUpdate("drop table if exists Seat");
            stmt.executeUpdate("CREATE TABLE Seat (seatnumber VARCHAR(4), flightnumber VARCHAR(8), class STRING, price VARCHAR(28), reservation STRING , PRIMARY KEY(seatnumber, flightnumber ), FOREIGN KEY(flightnumber) REFERENCES Flight(number), FOREIGN KEY(reservation) REFERENCES Customer(userid))");

            pstmt = conn.prepareStatement("insert into Seat values(?,?,?,?,?)");

            // Insert seats into db
            for (int i = 0; i < Seats.length; i++){
                // Generate random class
                n = rand.nextInt(3);
                switch (n) {
                   case 0:
                       classtype = "Economy";
                       break;
                   case 1:
                       classtype = "First-class";
                       break;
                   case 2:
                       classtype = "Business";
                       break;
                   default:
                       classtype = "Unknown";
                }

                // Generate random price with lower bound 1000
                price = 1000 + rand.nextInt(2999);

                pstmt.setString(1, Seats[i]);    //Note index  starts with 1
                pstmt.setString(2, FlightNR[i]);
                pstmt.setString(3, classtype);
                pstmt.setInt(   4,    price);
                pstmt.setString(5, "NULL");      //Fix  later?

                // For debugging:
                System.out.println(i + " : " +  Seats[i]);

                // pstmt.executeUpdate();
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();

        } catch(SQLException e) {
            System.out.println("Error in generateSeat");
            System.err.println(e.getMessage());
        }
        return;

    }

    public static void main(String[] args) throws ClassNotFoundException
    {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try {
            // Create a database connection:
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            //statement.setQueryTimeout(30);  // set timeout to 30 sec.
            //statement.executeUpdate("drop table if exists person");

            // Company:
            generateCompany();

            // Customer:
            generateCustomer();

            // Airport:
            generateAirports();


            // Seat:
            generateSeat();

            // Flight:
            // conn.setAutoCommit(true); // Remove later
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
        } catch(SQLException e) {
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
