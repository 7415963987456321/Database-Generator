// Authors: Hrafnkell Sigurðarson, Kári Viðar Jónsson.
//
// Generates a random testing database.
//
// Forritið má þýða og keyra svona á Windows:
//   javac Database.java
//   java -cp .;sqlite-jdbc-....jar Database
//
//   Unix:
//   javac Database.java
//   java -cp .:sqlite-jdbc-....jar Database
//
//   Or use the makefile to compile, test, and run the database

import java.sql.*;
import java.util.Random;

public class Database {

    static Statement         stmt;
    static PreparedStatement pstmt;
    static Connection        conn;
    static Random            rand = new Random();

    // Data, don't fuck with this, needs to be in this order.
    static String[] Airlines = { "Air Iceland Connect", "Icelandair", "Eagle Air",
        "Norlandair", "Finnair", "Scandinavian Airlines",
        "Ryanair", "Lufthansa Group", "easyJet", "SAS", "Wizz Air" };
    static String[] Countries = { "Iceland", "Germany", "Germany", "Italy", "Italy", "Italy", "Germany", "France", "France", "Germany",
        "Germany", "Ireland", "Ireland", "Italy", "Italy", "Italy", "Italy", "Italy", "Italy", "Italy",
        "Georgia", "Georgia", "Memmingen", "Italy", "Italy", "Italy", "Italy", "Germany", "Germany", "Germany",
        "Germany", "Germany", "Germany", "Germany", "Germany", "Germany", "Greece", "Greece", "Greece", "Greece",
        "Greece", "Greece", "Greece", "Greece", "Greece", "Greece", "Hungary", "Hungary", "Iceland", "Ireland",
        "Ireland", "Albania", "Austria", "Austria", "Austria", "Austria", "Austria", "Austria", "Azerbaijan", "Belarus",
        "Belgium", "Belgium", "Belgium", "Belgium", "Belgium", "Bosnia-Herzegovina", "Bosnia-Herzegovina", "Bulgaria", "Bulgaria", "Bulgaria",
        "Croatia", "Croatia", "Croatia", "Croatia", "Croatia", "Cyprus", "Cyprus", "Czech-Republic", "Czech-Republic", "Denmark",
        "Denmark", "Denmark", "Denmark", "Denmark", "Estonia", "Finland", "Finland", "Finland", "Finland", "Finland",
        "Finland", "France", "France", "France", "France", "France", "France", "France", "France", "France", "France", "France", "USA" };


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

        String accessibility;
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
            "Johanna", "Sveinn", "Konstantin", "Lindsay", "Forbes",
            "Jaakob", "Viljo", "Jenna", "Kári", "Boyd", "Yannig",
            "Ellar", "Päivi", "Máel", "Coluim", "Arnar", "Borghildur",
            "Guðríður", "Einar", "Tryggvi","Hrafnkell", "Erskine", "Gordon",
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
        try {
            stmt.executeUpdate("drop table if exists Company");
            stmt.executeUpdate("CREATE TABLE Company (compname VARCHAR(128), compid INTEGER, PRIMARY KEY(compname))");

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

        // Fix later, generate better seats
        String[] Seats = { "01A", "14D", "48I"};
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
                pstmt.setString(2, genFlightNumber());
                pstmt.setString(3, classtype);
                pstmt.setInt(   4,    price);
                pstmt.setString(5, "NULL");      //Fix  later?

                // For debugging:
                System.out.println(i + " : " +  Seats[i]);
                // System.out.print("FLightnr: " + FlightNR[i] + "\n" );

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

    public static String genFlightNumber(){
        // Flight number is a two letter prefix with a 3 digit number
        String[] FlightPrefix = { "5X", "AA", "AC", "AM", "AS", "AY", "B6", "BA",
            "BC", "BG", "CI", "CP", "D5", "D7", "DJ", "DL",
            "EK", "EY", "F",  "FX", "HA", "JL", "KE", "LA",
            "LH", "LJ", "LO", "LY", "MH", "MM", "NH", "NK",
            "NU", "NZ", "QF", "QK", "QR", "SG", "SK", "SQ",
            "TK", "TN", "UA", "VA", "WN", "WS",
        };
        int r = rand.nextInt(FlightPrefix.length);

        return "" + FlightPrefix[r] + rand.nextInt(999);
    }

    public static String genRandomAircraft(){
        String[] Aircraft = { "Airbus A220", "Airbus A319/A320/A321", "Airbus A330", "Airbus A350 XWB",
            "Airbus A380", "Antonov An-148/An-158", "Boeing 737",
            "Boeing 747", "Boeing 767", "Boeing 777",
            "Boeing 787 Dreamliner", "Bombardier CRJ700/CRJ705/CRJ900/CRJ1000", "Comac ARJ21 Xiangfeng",
            "Comac C919", "Embraer ERJ 135/ERJ 140/ERJ 145", "Embraer E-170/E-175/E-190/E-195",
            "Ilyushin Il-96", "Irkut MC-21", "Mitsubishi Regional Jet",
            "Sukhoi Superjet SSJ100", "Tupolev Tu-204/Tu-214" };

        int r = rand.nextInt(Aircraft.length);

        return Aircraft[r];
    }

    public static String genRandomAmenities () {
        String[] Amenities = { "In-flight meal", "Drinks",
            "Music", "Television",
            "Coffee/tea", "Free blankets",
            "Wi-fi", "Boutique",
        };

        String amen ="";

        int r = rand.nextInt(Amenities.length);

        for (int i = 0; i < r; i++ ){
            amen += Amenities[rand.nextInt(Amenities.length)] + ", ";
        }
        return amen;
    }


    public static void generateFlights() {
        // Hold on to your hats, we are about to get the current time in Java
        long time = System.currentTimeMillis();

        int numberOfFlights = 20;

        // Generate flight number test.
        for(int i = 0; i < numberOfFlights ; i++){
            System.out.println("TEST: " + genFlightNumber());
        }

        try {
            // conn.setAutoCommit(true); // Remove later
            stmt.executeUpdate("drop table if exists Flight");
            stmt.executeUpdate("CREATE TABLE Flight (number VARCHAR(24), takeOff DATETIME, landing DATETIME, origin STRING, dest STRING, aircraft VARCHAR(128), compname VARCHAR(128), amenities STRING, PRIMARY KEY (number, takeOff, origin), FOREIGN KEY(origin) REFERENCES Airport (airportname), FOREIGN KEY(dest) REFERENCES Airport (airportname), FOREIGN KEY(compname) REFERENCES Company (compname))");

            pstmt = conn.prepareStatement("insert into  Flight values(?, ?, ?, ?, ?, ?, ?, ?)");
            // Insert flights into db
            for (int i = 0; i < numberOfFlights; i++){
                // Generate takeoff and landing with random offset, might need adjustment:
                java.sql.Timestamp takeofftime = new java.sql.Timestamp(time + (1800000 * rand.nextInt(10)));
                java.sql.Timestamp landingtime = new java.sql.Timestamp(time + (1800000 * rand.nextInt(30)));

                pstmt.setString(1, genFlightNumber() );   // Flightnumber

                // Need to adjust time on this.
                pstmt.setTimestamp(2, takeofftime); // takeofftime
                pstmt.setTimestamp(3, landingtime); // landingtime

                // Whatever random origin and destination
                pstmt.setString(4, Countries[rand.nextInt(Countries.length)]); // origin
                pstmt.setString(5, Countries[rand.nextInt(Countries.length)]); // dest

                pstmt.setString(6, genRandomAircraft()); // Random aircraft
                pstmt.setString(7, Airlines[rand.nextInt(Airlines.length)]); // Random airline
                pstmt.setString(8, genRandomAmenities() ); // Generates random amenities

                // For debugging:
                System.out.println("Takeoff: " + takeofftime);
                System.out.println("Landing:" + landingtime);

                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();

        } catch(SQLException e) {
            System.out.println("Error in generateFlights");
            System.err.println(e.getMessage());
        }
        return;

    }

    public static void main(String[] args) throws ClassNotFoundException {
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
            generateFlights();

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
