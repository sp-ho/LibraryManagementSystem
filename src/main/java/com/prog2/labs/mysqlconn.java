package com.prog2.labs;

//Java Database Connectivity (JDBC)

/*
*  1. import packages --> java.sql
*  2. load and register the driver --> com.mysql.cj.jdbc.Driver
*  3. create connection --> Connection interface
*  4. create a statement --> Statement interface
*  5. execute the query
*  6. process the results
*  7. close
*/
//import java.sql.*; // also can import all 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// create a connection between the project and database
public class mysqlconn {
	// declare the url link, username and password of mysql 
	private static String server = "localhost";
    private static final String USER = "root";
    private static final String PASS = ""; // no password
    
    // testing 1
    public static Connection dbConnection(String dbName) {
    	Connection connection = null;
    	
    	// partial link excluding the database name
    	String url1 = "jdbc:mysql://" + server + "/";  
    	
    	// full link including the  database name
    	String url2 = "jdbc:mysql://" + server + "/" + dbName;  
    	
    	try {
	    	// connect to the mysql without specifying a database
	    	connection = DriverManager.getConnection(url1, USER, PASS);
	    	Statement stmt = connection.createStatement();
	    	
	    	// check if the database exists    	
	    	ResultSet rSet = connection.getMetaData().getCatalogs(); // get catalogs in the connected database
	    	boolean dbExists = false;
	    	
	    	while (rSet.next()) {
	    		String dbName2 = rSet.getString(1); 
	    		// true if there is a database with the same name
	    		if (dbName2.equalsIgnoreCase(dbName)) {
	    			dbExists = true;
	    			break;
	    		}
	    	}
	    	rSet.close();
	    	
	    	if (!dbExists) {
	    		// create the database
	    		String query = "CREATE DATABASE " + dbName;
	    		stmt.executeUpdate(query);
	    		System.out.println("Database created successfully...");
	    	} else {
	    		System.out.println("Database already exists...");
	    	}
	    	    	
	    	// connect to the database
	    	connection = DriverManager.getConnection(url2, USER, PASS);
	    	System.out.println("Connected to database " + dbName + "...");
    
    	} catch (SQLException e) {
    	
    	}
    	return connection;
    }

}

