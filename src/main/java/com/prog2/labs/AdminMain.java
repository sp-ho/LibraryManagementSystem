package com.prog2.labs;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class AdminMain extends JFrame {

	private JPanel contentPane;
	private JTextField tfCatalogBookName;
	private JTextField tfCatalogAuthor;
	static JTable tableCatalog;
	static DefaultTableModel modelCatalog;
	private boolean isCatalogDisplayed = false;
	public Map<String, String> catalog;
	public Map<String, String> issuedBooks;
	public Map<Integer, String> studentMap;
	
	private JTable tableIssued;
	static DefaultTableModel modelIssued;
	static DefaultTableModel modelStudent;
	private static JTable tableStudent;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMain frame = new AdminMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminMain() {
		LibrarianController controller = new LibrarianController();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if (!isCatalogDisplayed) {
					controller.viewCatalog(); // call viewCatalog controller 
					LibrarianController.viewIssuedBooks();
					LibrarianController.viewStudent();
					isCatalogDisplayed = true;
				}
			}
		});
		setTitle("Library Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1059, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBook = new JLabel("Books");
		lblBook.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblBook.setBounds(60, 37, 81, 25);
		contentPane.add(lblBook);
		
		JLabel lblStu = new JLabel("Students");
		lblStu.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStu.setBounds(49, 235, 104, 25);
		contentPane.add(lblStu);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(179, 11, 854, 439);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Books Catalog", null, panel, null);
		panel.setLayout(null);
		
		tfCatalogBookName = new JTextField();
		tfCatalogBookName.setBounds(99, 18, 408, 20);
		panel.add(tfCatalogBookName);
		tfCatalogBookName.setColumns(10);
		
		
		// disable tfCatalogAuthor when typing in tfCatalogBookName
		tfCatalogBookName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				tfCatalogAuthor.setEnabled(false);  // disable tfCatalogAuthor
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				tfCatalogAuthor.setEnabled(false); // disable tfCatalogAuthor
				if (tfCatalogBookName.getText().isEmpty()) {
					tfCatalogAuthor.setEnabled(true); // enable tfCatalogAuthor if tfCatalogBookName is empty
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		});  
		
		tfCatalogAuthor = new JTextField();
		tfCatalogAuthor.setColumns(10);
		tfCatalogAuthor.setBounds(99, 51, 408, 20);
		panel.add(tfCatalogAuthor);
		
		// disable tfCatalogBookName when typing in tfCatalogAuthor
		tfCatalogAuthor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				tfCatalogBookName.setEnabled(false);  // disable tfCatalogBookName
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				tfCatalogBookName.setEnabled(false); // disable tfCatalogBookName
				if (tfCatalogAuthor.getText().isEmpty()) {
					tfCatalogBookName.setEnabled(true); // enable tfCatalogBookName if tfCatalogAuthor is empty
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		}); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 89, 829, 311);
		panel.add(scrollPane);
		
		tableCatalog = new JTable();
		modelCatalog = new DefaultTableModel();
		
		tableCatalog.setModel(modelCatalog);
		scrollPane.setViewportView(tableCatalog);
		
		JButton btnCatalogSearch = new JButton("Search Book");
		btnCatalogSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarianController controller3 = new LibrarianController();
				String title = tfCatalogBookName.getText().trim();
				String author = tfCatalogAuthor.getText().trim();
				
				try {
					// retreive data from database and store into HashMap catalog
					DbConn dbConn = DbConn.getInstance("db_lib");
					Connection connection = dbConn.getConnection();
					
					if (!title.isEmpty()) {
					// query to select the fields from table in database
					String query = "SELECT * FROM books WHERE title = ?";
					PreparedStatement st = connection.prepareStatement(query);
					st.setString(1, title);
					ResultSet rSet = st.executeQuery();
					
					while (rSet.next()) {
						String sn = rSet.getString("sn");
						String bookTitle = rSet.getString("title");
						String bookAuthor = rSet.getString("author");
						String publisher = rSet.getString("publisher");
						double price = rSet.getDouble("price");
						int quantity = rSet.getInt("quantity");
						int issued = rSet.getInt("issued");
						Date addedDate = rSet.getDate("addedDate");
						
						String available = (quantity > 0)? "Y": "N";
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String bookDetail = sn + "," + bookTitle + "," + bookAuthor + "," + publisher + "," 
											+ String.format("%.2f",price) + "," + String.valueOf(quantity) + ","
											+ String.valueOf(issued) + "," + dateFormat.format(addedDate) + ","
											+ available;	
						
						// print on jtable from the database
						Object[] colName = { "SN", "Title", "Author", "Publisher", "Price ($)", "Quantity", "Issued Copies", 
											"Added Date", "Available"};
						AdminMain.modelCatalog.setColumnIdentifiers(colName);
						modelCatalog.setRowCount(0); // clear pre-existing rows
						String[] row = {sn, bookTitle, bookAuthor, publisher, String.format("%.2f",price), String.valueOf(quantity), 
								String.valueOf(issued) , dateFormat.format(addedDate), available};
						AdminMain.modelCatalog.addRow(row);
						}
					}
					if (!author.isEmpty()) {
						String query = "SELECT * FROM books WHERE author = ?";
						PreparedStatement st = connection.prepareStatement(query);
						st.setString(1, author);
						ResultSet rSet = st.executeQuery();
						
						while (rSet.next()) {
							String sn = rSet.getString("sn");
							String bookTitle = rSet.getString("title");
							String bookAuthor = rSet.getString("author");
							String publisher = rSet.getString("publisher");
							double price = rSet.getDouble("price");
							int quantity = rSet.getInt("quantity");
							int issued = rSet.getInt("issued");
							Date addedDate = rSet.getDate("addedDate");
							
							String available = (quantity > 0)? "Y": "N";
							
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String bookDetail = sn + "," + bookTitle + "," + bookAuthor  + "," + publisher + "," 
												+ String.format("%.2f",price) + "," + String.valueOf(quantity) + ","
												+ String.valueOf(issued) + "," + dateFormat.format(addedDate) + ","
												+ available;	
							
							// print on jtable from the database
							Object[] colName = { "SN", "Title", "Author", "Publisher", "Price ($)", "Quantity", "Issued Copies", 
												"Added Date", "Available"};
							AdminMain.modelCatalog.setColumnIdentifiers(colName);
							modelCatalog.setRowCount(0); // clear pre-existing rows
							String[] row = {sn, bookTitle, bookAuthor , publisher, String.format("%.2f",price), String.valueOf(quantity), 
									String.valueOf(issued) , dateFormat.format(addedDate), available};
							AdminMain.modelCatalog.addRow(row);
					}

					}			
				} catch (Exception e1) {
				}
	
			}
		});
		btnCatalogSearch.setBounds(531, 18, 123, 53);
		panel.add(btnCatalogSearch);
		
		JButton btnRefreshCatalog = new JButton("Refresh Catalog");
		btnRefreshCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCatalogDisplayed) {
					// empty the pre-existing JTable
					AdminMain.modelCatalog.setRowCount(0);
					// get the catalog from DB
					LibrarianController controller3 = new LibrarianController();
					catalog = controller3.viewCatalog();
					
					return;
				}
			}
		});
		btnRefreshCatalog.setBounds(672, 17, 153, 54);
		panel.add(btnRefreshCatalog);
		
		JLabel lblCatalogBookName = new JLabel("Book Name");
		lblCatalogBookName.setBounds(17, 21, 86, 14);
		panel.add(lblCatalogBookName);
		
		JLabel lblCatalogAuthor = new JLabel("Author");
		lblCatalogAuthor.setBounds(17, 54, 110, 14);
		panel.add(lblCatalogAuthor);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Issued Books Details", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 829, 341);
		panel_1.add(scrollPane_1);
		
		tableIssued = new JTable();
		modelIssued = new DefaultTableModel();

		tableIssued.setModel(modelIssued);
		scrollPane_1.setViewportView(tableIssued);
		
		JButton btnRefreshIssuedList = new JButton("Refresh Book List");
		btnRefreshIssuedList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (isCatalogDisplayed) {
					// empty the pre-existing JTable
					AdminMain.modelIssued.setRowCount(0);
					// get the catalog from DB
					issuedBooks = LibrarianController.viewIssuedBooks();
					
					return;// clear the current rows in the table model
				}
			}
		});
		btnRefreshIssuedList.setBounds(664, 363, 175, 37);
		panel_1.add(btnRefreshIssuedList);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Students List", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 829, 342);
		panel_2.add(scrollPane_2);
		
		tableStudent = new JTable();
		modelStudent = new DefaultTableModel();
		Object[] columnStudent = { "Student ID", "Student Name", "Book ID", "Book Name", "Issue Date" };
		modelStudent.setColumnIdentifiers(columnStudent);
		tableStudent.setModel(modelStudent);
		scrollPane_2.setViewportView(tableStudent);
		
		JButton btnRefreshStudentList = new JButton("Refresh Students List");
		btnRefreshStudentList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (isCatalogDisplayed) {
					// empty the pre-existing JTable
					AdminMain.modelStudent.setRowCount(0);
					// get the catalog from DB
					studentMap = LibrarianController.viewStudent();
					
					return;// clear the current rows in the table model
				}
			}
		});
		btnRefreshStudentList.setBounds(658, 364, 181, 36);
		panel_2.add(btnRefreshStudentList);
		
		// open AddBook window when Add Book is clicked
		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBook addBook = new AddBook();
				addBook.setVisible(true);
			}
		});
		btnAddBook.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAddBook.setBounds(24, 73, 134, 23);
		contentPane.add(btnAddBook);
		
		JButton btnDelBook = new JButton("Delete Book");
		btnDelBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnDelBook.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDelBook.setBounds(24, 106, 134, 23);
		contentPane.add(btnDelBook);
		
		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnBook returnBook = new ReturnBook();
				returnBook.setVisible(true);
			}
		});
		btnReturnBook.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReturnBook.setBounds(24, 172, 134, 23);
		contentPane.add(btnReturnBook);
		
		JButton btnIssueBook = new JButton("Issue Book");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IssueBook issueBook = new IssueBook();
				issueBook.setVisible(true);
			}
		});
		btnIssueBook.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnIssueBook.setBounds(24, 139, 134, 23);
		contentPane.add(btnIssueBook);
		
		JButton btnDelStu = new JButton("Delete Student");
		btnDelStu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDelStu.setBounds(23, 306, 135, 23);
		contentPane.add(btnDelStu);
		
		JButton btnAddStu = new JButton("Add Student");
		btnAddStu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddStudent addStudent = new AddStudent();
				addStudent.setVisible(true);
			}
		});
		btnAddStu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAddStu.setBounds(23, 273, 135, 23);
		contentPane.add(btnAddStu);
		
	}
}
