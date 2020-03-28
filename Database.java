// SQLite s�niforrit sta�f�rt af Snorra Agnarssyni 2012
// Upphaflega fengi� fr� SQLite vefs��unni.
//
// Forriti� m� ���a og keyra svona � Windows:
//   javac Sample.java
//   java -cp .;sqlite-jdbc-....jar Database
// � Unix:
//   javac Sample.java
//   java -cp .:sqlite-jdbc-....jar Database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
  public static void main(String[] args) throws ClassNotFoundException
  {
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");
    
    Connection connection = null;
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:database.db");
      Statement statement = connection.createStatement();
      //statement.setQueryTimeout(30);  // set timeout to 30 sec.
      //statement.executeUpdate("drop table if exists person");

      // company 
      statement.executeUpdate("drop table if exists Company");
      statement.executeUpdate("CREATE TABLE Company (compname VARCHAR(128), compid INTEGER, PRIMARY KEY(compname))");
      statement.executeUpdate("insert into Company values('ICELANDAIR', 1944)");

      // Customer
      statement.executeUpdate("drop table if exists Customer");
      statement.executeUpdate("CREATE TABLE Customer (username STRING, userid INTEGER, PRIMARY KEY(userid))");
      statement.executeUpdate("insert into Customer values('Arnar', 11111)");
      statement.executeUpdate("insert into Customer values('Arttu', 22222)");
      statement.executeUpdate("insert into Customer values('Hrafnkell', 33333)");
      statement.executeUpdate("insert into Customer values('Kari', 44444)");

      // Airport
      statement.executeUpdate("drop table if exists Airport");
      statement.executeUpdate("CREATE TABLE Airport ('airportname' STRING, accessability STRING, country STRING ,PRIMARY KEY(airportname))");
      statement.executeUpdate("insert into Airport values('Leifstod KEF', 'wheelchair accessable', 'ICE')");
      statement.executeUpdate("insert into Airport values('Schönefeld BER', 'wheelchair accessable', 'GER')");
      statement.executeUpdate("insert into Airport values('John F. Kennedy NY', 'wheelchair accessable', 'USA')");

      // Seat
      statement.executeUpdate("drop table if exists Seat");
      statement.executeUpdate("CREATE TABLE Seat (seatnumber VARCHAR(4), flightnumber VARCHAR(8), class STRING, price VARCHAR(28), reservation STRING , PRIMARY KEY(seatnumber, flightnumber ), FOREIGN KEY(flightnumber) REFERENCES Flight(number), FOREIGN KEY(reservation) REFERENCES Customer(userid))");
      statement.executeUpdate("insert into Seat values('01A', 'FI614', 'FIRST', 64.999, NULL)");
      statement.executeUpdate("insert into Seat values('14D', 'FI614', 'BUS', 38.765, NULL)");
      statement.executeUpdate("insert into Seat values('48I', 'FI614', 'ECO', 13.250, NULL)");

      // Flight
      statement.executeUpdate("drop table if exists Flight");
      statement.executeUpdate("CREATE TABLE Flight (number VARCHAR(24), takeOff DATETIME, landing DATETIME, origin STRING, dest STRING, aircraft VARCHAR(128), compname VARCHAR(128), amenities STRING, PRIMARY KEY (number, takeOff, origin), FOREIGN KEY(origin) REFERENCES Airport (airportname), FOREIGN KEY(dest) REFERENCES Airport (airportname), FOREIGN KEY(compname) REFERENCES Company (compname))");
      statement.executeUpdate("insert into Flight values('FI614',  '2020-03-27 22:30:45', '2020-03-28 05:00:00', 'Leifstod KEF', 'John F. Kennedy NY', 'Boeing737max', 'ICELANDAIR', 'Food drinks and onboard shop')");

      //statement.executeUpdate("insert into Flights values('FI602', '2020-03-27', 'boeing 777', 'PARIS', 'KEF')");
      /*ResultSet rs = statement.executeQuery("select * from person");
      while(rs.next())
      {
        // read the result set
        System.out.println("name = " + rs.getString("name"));
        System.out.println("id = " + rs.getInt("id"));
      }
      rs.close();*/
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
}
