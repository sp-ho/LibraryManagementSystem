package com.prog2.labs;
// Student class

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Student implements Person{
	// instance variables
	private int stId;
	private String name;
	private String contact;
	
	public Student() {}

	public Student(int stId, String name, String contact) {
		super();
		this.stId = stId;
		this.name = name;
		this.contact = contact;
	}
	
	// searchBookByTitle method
	public List<Book> searchBookByTitle (String title){
	    Map<String, String> bookStringMap = getCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                // need to improve the Book Constructor in viewCatalog()
	                return new Book(values[0], values[1], values[2], values[3], Double.parseDouble(values[4]), 
            				Integer.parseInt(values[5]), Integer.parseInt(values[6]), 
            				LocalDate.parse(values[7]));
	            })
	            .filter(book -> book.getTitle().equals(title))
	            .collect(Collectors.toList());
	    return matchingBooks;
	}
	
	// searchBookByName method
	public List<Book> searchBookByName (String name){
		Map<String, String> bookStringMap = getCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                return new Book(values[0], values[1], values[2], values[3], Double.parseDouble(values[4]), 
            				Integer.parseInt(values[5]), Integer.parseInt(values[6]), 
            				LocalDate.parse(values[7]));
	            })
	            .filter(book -> book.getAuthor().equals(name))
	            .collect(Collectors.toList());
	    return matchingBooks;
	}
	
	// serahcBookByPublisher method
	public List<Book> searchBookByPublisher (String publisher){
		Map<String, String> bookStringMap = getCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                return new Book(values[0], values[1], values[2], values[3], Double.parseDouble(values[4]), 
            				Integer.parseInt(values[5]), Integer.parseInt(values[6]), 
            				LocalDate.parse(values[7]));
	            })
	            .filter(book -> book.getPublisher().equals(publisher))
	            .collect(Collectors.toList());
	    return matchingBooks;
	}
	
	
	// viewCatalog method
	public  Map<String, String> viewCatalog () {
		// key: sn, value: book's details
		Map<String, String> catalog = new HashMap<>();
		
		try {
			// retreive data from database and store into HashMap catalog
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			// query to select the fields from table in database
			String query = "SELECT * FROM books";
			// reusable prepared statement to execute the query more than once
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rSet = st.executeQuery();
					
			while (rSet.next()) {
				String sn = rSet.getString("sn");
				String title = rSet.getString("title");
				String author = rSet.getString("author");
				String publisher = rSet.getString("publisher");
				int quantity = rSet.getInt("quantity");
				int issued = rSet.getInt("issued");
				String available = (quantity > 0)? "Yes": "No";
				
				String bookDetail = sn + "," + title + "," + author + "," + publisher + "," + available;
				catalog.put(sn, bookDetail);		
				
				Object[] colName = { "SN", "Title", "Author", "Publisher", "Available"};
				StuMain.modelSearchBook.setColumnIdentifiers(colName);
				String[] row = {sn, title, author, publisher, available};
				StuMain.modelSearchBook.addRow(row);

			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return catalog;
		
	}

	public Map<String, String> viewMyBooks () {
		Map<String, String> myBooks = new HashMap<>();
		
		try {
			// retreive data from database and store into HashMap catalog
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			// query to select the fields from table in database
			String query = 
					"SELECT books.sn, books.title, books.author, issuedBooks.issueDate "
					+ "FROM issuedBooks "
					+ "INNER JOIN books ON issuedBooks.sn = books.sn "
					+ "WHERE issuedBooks.stID = ?";
			// reusable prepared statement to execute the query more than once
			PreparedStatement st = connection.prepareStatement(query);
			
			// set the value of the 1st parameter (1st ?) in prepared statement
			st.setInt(1, Integer.parseInt(AdminLogin.username)); // test with stuID 1, Adam Smith
			
			ResultSet rSet = st.executeQuery();
					
			while (rSet.next()) {
				String sn = rSet.getString("sn");
				String title = rSet.getString("title");
				String author = rSet.getString("author");
				Date issueDate = rSet.getDate("issueDate");
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// set the expired date
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(issueDate);
				calendar.add(Calendar.MONTH, 1);
				Date expiredDate = calendar.getTime();
				
				// value of myBooks map
				String bookDetail = sn + "," + title + "," + author + "," + dateFormat.format(issueDate);
				myBooks.put(sn, bookDetail);		
				
				Object[] colName = { "SN", "Title", "Author", "Borrowed Date", "Expired Date"};
				StuMain.modelMyBook.setColumnIdentifiers(colName);
				String[] row = {sn, title, author, dateFormat.format(issueDate), dateFormat.format(expiredDate)};
				StuMain.modelMyBook.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return myBooks;
	}
	
	public Map<String, String> getCatalog() {
		// key: sn, value: book's details
		Map<String, String> catalog = new HashMap<>();
		
		try {
			// retreive data from database and store into HashMap catalog
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			// query to select the fields from table in database
			String query = "SELECT * FROM books";
			// reusable prepared statement to execute the query more than once
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rSet = st.executeQuery();
					
			while (rSet.next()) {
				String sn = rSet.getString("sn");
				String title = rSet.getString("title");
				String author = rSet.getString("author");
				String publisher = rSet.getString("publisher");
				double price = rSet.getDouble("price");
				int quantity = rSet.getInt("quantity");
				int issued = rSet.getInt("issued");
				Date addedDate = rSet.getDate("addedDate");
				String available = (quantity > 0)? "Y": "N";
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String bookDetail = sn + "," + title + "," + author + "," + publisher + "," 
									+ String.valueOf(price) + "," + String.valueOf(quantity) + ","
									+ String.valueOf(issued) + "," + dateFormat.format(addedDate) + ","
									+ available;
				catalog.put(sn, bookDetail);		
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return catalog;
	}
	
	// borrow method
	public boolean issueBook (Book b, Student s) {
		// if book is available decreasehttps://marketplace.eclipse.org/marketplace-client-intro?mpc_install=5012014 quantity by 1, increase issued by 1
		if(b.getQte() != 0) {
			
			b.setQte(b.getQte() - 1);
			b.setIssuedQte(b.getIssuedQte() + 1);
			// after that, add book to issued book DB table (maybe call a issueBook
			// method here that will be a clone of issue book in book class.
			Librarian.addBook(b, "issuedBooks");
			return true;
		}
		
		return false;
	}
	
	// toReturn method
	public boolean returnBook (String bookSN, String studID) {
		return false;
	}

	public int getStId() {
		return stId;
	}

	public void setStId(int stId) {
		this.stId = stId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
