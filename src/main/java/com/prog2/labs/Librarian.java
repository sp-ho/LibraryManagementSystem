package com.prog2.labs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Librarian implements Person {
	int empId;
	String empName;
	String empContact;
	private static LocalDate dateOfPurchase;
	String name;
	
	// for testing
	public static void main(String[] args) {
	}
	
	public boolean returnBook(String bookSN, String studID) {
		return false;
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
		}
		
		return catalog;
	}
	
	public static Map<String, String> viewIssuedBooks() {
		Map<String, String> issuedBooks = new HashMap<>();
		try {
			// retreive data from database and store into HashMap catalog
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			// query to select the fields from table in database
			String query = "SELECT * FROM issuedBooks";
			// reusable prepared statement to execute the query more than once
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rSet = st.executeQuery();
					
			while (rSet.next()) {
				int id = rSet.getInt("id");
				String sn = rSet.getString("sn");
				String stuId = rSet.getString("stID");
				String stuName = rSet.getString("stName");
				String stuContact = rSet.getString("stContact");
				Date issueDate = rSet.getDate("issueDate");
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String issueBookDetail = String.valueOf(id) + "," + sn + "," + stuId + "," 
									+ stuName + "," + stuContact + "," + dateFormat.format(issueDate);
				issuedBooks.put(sn, issueBookDetail);		

			}

		} catch (Exception e) {
		}
		
		// print on jtable from the issuedBooks hashmap, the list is sorted by sn using Treemap
		Map<String, String> sortedIssuedBooks = new TreeMap<>(issuedBooks);
		String[] colName = { "Issue ID", "Book SN", "Student ID", "Student Name", "Student Contact", "Issue Date"};
		AdminMain.modelIssued.setColumnIdentifiers(colName);
		
		for (Map.Entry<String, String> entry: sortedIssuedBooks.entrySet()) {
			String[] row = entry.getValue().split(","); // split the value by comma
			AdminMain.modelIssued.addRow(row);
		}
		return issuedBooks;
	}
		
	public Map<String, String> viewCatalog() {
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
									+ String.format("%.2f",price) + "," + String.valueOf(quantity) + ","
									+ String.valueOf(issued) + "," + dateFormat.format(addedDate) + ","
									+ available;
				catalog.put(sn, bookDetail);		
				
				// print on jtable from the database
				Object[] colName = { "SN", "Title", "Author", "Publisher", "Price ($)", "Quantity", "Issued Copies", 
									"Added Date", "Available"};
				AdminMain.modelCatalog.setColumnIdentifiers(colName);
				String[] row = {sn, title, author, publisher, String.format("%.2f",price), String.valueOf(quantity), 
						String.valueOf(issued) , dateFormat.format(addedDate), available};
				AdminMain.modelCatalog.addRow(row);

			}			
		} catch (Exception e) {
		}

		return catalog;
	}
	
	public static Map<Integer, String> viewStudent () {
		// key: sn, value: book's details
		Map<Integer, String> stuList = new HashMap<>();
		
		try {
			// retreive data from database and store into HashMap catalog
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			// query to select the fields from table in database
			String query = "SELECT * FROM students";
			// reusable prepared statement to execute the query more than once
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rSet = st.executeQuery();
					
			while (rSet.next()) {
				int stuId = rSet.getInt("studentId");
				String name = rSet.getString("name");
				String contact = rSet.getString("contact");
				
				String stuDetail = String.valueOf(stuId) + "," + name + "," + contact;
				stuList.put(stuId, stuDetail);		
				
				Object[] colName = { "ID", "Name", "Contact"};
				AdminMain.modelStudent.setColumnIdentifiers(colName);
				String[] row = {String.valueOf(stuId), name, contact};
				AdminMain.modelStudent.addRow(row);
				
				

			}			
		} catch (Exception e) {
		}
		return stuList;	
	}
	
	// controller: addBook method
	public static void addBook(Book book, String tableName) {
		try {
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			
			// query to check if the same book (same sn) exists in the database
			String checkQuery = "SELECT quantity FROM " + tableName + " WHERE sn = ?";
			
			/* creata a prepared statement to execute the parameterized query
			 * multiple times (reusable) with different parameter values (?) */
			PreparedStatement checkSt = connection.prepareStatement(checkQuery);
			
			// set the value of the 1st parameter (1st ?) in prepared statement
			checkSt.setString(1, book.getSn());
			
			/* execute the query in pepared statement and store ResultSet instance to rSet.
			 * it contain the rows that match the query.
			 */
			ResultSet rSet = checkSt.executeQuery();
			
			// if book already exists, update book quantity
			if (rSet.next()) { 
				int existQ = rSet.getInt("quantity");
				int newQ = existQ + book.getQte();
				
				String updateQuery = "UPDATE " + tableName + " SET quantity = ? WHERE sn = ?";
				PreparedStatement updateSt = connection.prepareStatement(updateQuery);
				updateSt.setInt(1, newQ);
				updateSt.setString(2, book.getSn());
				
				updateSt.executeUpdate();
				System.out.println("Book quantity updated: " + newQ);
			} else { // book does not exist, insert the book to the table
				String query = "INSERT INTO " + tableName + " (sn, title, author, publisher, price, quantity, issued, addedDate)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; 
				PreparedStatement st = connection.prepareStatement(query);
				st.setString(1, book.getSn());
				st.setString(2, book.getTitle());
				st.setString(3, book.getAuthor());
				st.setString(4, book.getPublisher());
				st.setDouble(5, book.getPrice());
				st.setInt(6, book.getQte());
				st.setInt(7, book.getIssuedQte()); // will be set as 0 in AddBook.java 
				dateOfPurchase = LocalDate.now();
				st.setString(8, dateOfPurchase.toString()); // current date
				
				st.executeUpdate();
				System.out.println("Book added");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void addStudent(Student student) {
		try {
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			
			// query to check if the same book (same sn) exists in the database
			String checkQuery = "SELECT * FROM students WHERE studentId = ?";
			
			/* creata a prepared statement to execute the parameterized query
			 * multiple times (reusable) with different parameter values (?) */
			PreparedStatement checkSt = connection.prepareStatement(checkQuery);
			
			// set the value of the 1st parameter (1st ?) in prepared statement
			checkSt.setInt(1, student.getStId());
			
			/* execute the query in pepared statement and store ResultSet instance to rSet.
			 * it contain the rows that match the query.
			 */
			ResultSet rSet = checkSt.executeQuery();
			
			// if book already exists, update book quantity
			if (rSet.next()) { 
				System.out.println("Student already exists.");
			} else { // book does not exist, insert the book to the table
				String query = "INSERT INTO students (studentId, name, contact)"
						+ "VALUES (?, ?, ?)"; 
				PreparedStatement st = connection.prepareStatement(query);
				st.setInt(1, student.getStId());
				st.setString(2, student.getName());
				st.setString(3, student.getContact());
				
				st.executeUpdate();
				System.out.println("Student added");	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean issueBook(Book book, Student student) {
		// check if studentid exists in the studentTable
		 Map<Integer, String> studentMap = getStudentMap();
		 
		 if(studentMap.containsKey(student.getStId())) {		
			 	
			if(book.getQte() > 0) {
				book.setQte(book.getQte() - 1);
				book.setIssuedQte(book.getIssuedQte() + 1);
			
				try {
					DbConn dbConn = DbConn.getInstance("db_lib");
					Connection connection = dbConn.getConnection();
					
					// query to check if the same book (same sn) exists in the database
					String selectQuery = "SELECT quantity, issued FROM books WHERE sn = ?";
					
					/* creata a prepared statement to execute the parameterized query
					 * multiple times (reusable) with different parameter values (?) */
					PreparedStatement checkSt = connection.prepareStatement(selectQuery);
					
					// set the value of the 1st parameter (1st ?) in prepared statement
					checkSt.setString(1, book.getSn());
					
					/* execute the query in pepared statement and store ResultSet instance to rSet.
					 * it contain the rows that match the query.
					 */
					ResultSet rSet = checkSt.executeQuery();
					
					// if book already exists, update book quantity
					while (rSet.next()) { 
						int quantity = rSet.getInt("quantity");
						int issued = rSet.getInt("issued");
						
						// update books table
						String updateQuery = "UPDATE books SET quantity = ?, issued = ? WHERE sn = ?";
						PreparedStatement updateSt = connection.prepareStatement(updateQuery);
						updateSt.setInt(1, quantity - 1);
						updateSt.setInt(2, issued + 1);
						updateSt.setString(3, book.getSn());
						
						updateSt.executeUpdate();
					} 		
					
					System.out.println("Book is issued successfully.");
					
					// add issued book to issuedBooks table
					String insertQuery = "INSERT INTO issuedBooks (sn, stID, stName, stContact)"
							+ "VALUES (?, ?, ?, ?)"; 
					PreparedStatement st = connection.prepareStatement(insertQuery);
					st.setString(1, book.getSn());
					st.setInt(2, student.getStId());
					st.setString(3, student.getName());
					st.setString(4, student.getContact());
					
					st.executeUpdate();
					System.out.println("Book is added to issuedBooks table.");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}  
		} 		
		return false;
	}
	
	public Map<Integer, String> getStudentMap () {
		// key: sn, value: book's details
		Map<Integer, String> stuMap = new HashMap<>();

		try {
			// retrieve data from database and store into HashMap catalog
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			
			// query to select the fields from table in database
			String query = "SELECT studentId, name, contact FROM students";
			
			// reusable prepared statement to execute the query more than once
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rSet = st.executeQuery();
			
			Object[] colName = { "ID", "Name", "Contact" };
			AdminMain.modelStudent.setColumnIdentifiers(colName);

			while(rSet.next()) {
				int stuId = rSet.getInt("studentId");
				String name = rSet.getString("name");
				String contact = rSet.getString("contact");
				
				String stuDetail = stuId + "," + name + "," + contact;
				stuMap.put(stuId, stuDetail);
				

			}			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	return stuMap;
	}

	@Override
	public List<Book> searchBookByTitle(String title) {

		
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

	@Override
	public List<Book> searchBookByName(String name) {
		Map<String, String> bookStringMap = getCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                return new Book(values[0], values[1], values[2], values[3], Double.parseDouble(values[4]), 
            				Integer.parseInt(values[5]), Integer.parseInt(values[6]), 
            				LocalDate.parse(values[7]));
	            })
	            .filter(book -> book.getAuthor().equals(name) || name == null)
	            .collect(Collectors.toList());
	    return matchingBooks;
	}

	@Override
	public List<Book> searchBookByPublisher(String publisher) {
		Map<String, String> bookStringMap = getCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                return new Book(values[0], values[1], values[2], values[3], Double.parseDouble(values[4]), 
            				Integer.parseInt(values[5]), Integer.parseInt(values[6]), 
            				LocalDate.parse(values[7]));
	            })
	            .filter(book -> book.getPublisher().equals(publisher) || publisher == null)
	            .collect(Collectors.toList());
	    return matchingBooks;
	}

	public String welcomeStudent() {
		
		try {
			DbConn dbConn = DbConn.getInstance("db_lib");
			Connection connection = dbConn.getConnection();
			
			// query to check if the same book (same sn) exists in the database
			String checkQuery = "SELECT name FROM students WHERE studentId = ?";
			
			/* creata a prepared statement to execute the parameterized query
			 * multiple times (reusable) with different parameter values (?) */
			PreparedStatement checkSt = connection.prepareStatement(checkQuery);
			
			// set the value of the 1st parameter (1st ?) in prepared statement
			checkSt.setInt(1,Integer.parseInt(AdminLogin.username));
			
			/* execute the query in pepared statement and store ResultSet instance to rSet.
			 * it contain the rows that match the query.
			 */
			ResultSet rSet = checkSt.executeQuery();
			
			name = "";
			// if book already exists, update book quantity
			while (rSet.next()) { 
				name = rSet.getString("name");
				
				System.out.println("Student name: " + name);
				StuMain.lblWelcome.setText("Welcome, " + name + "!");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return name;
	}

}
