package com.prog2.labs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StuMain extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField tfBookName;
	private JTextField tfAuthor;
	private JTextField tfPublisher;
	private JTable tableSearchBook;
	private JTable tableMyBook;
	private boolean isCatalogDisplayed = false;
	static DefaultTableModel modelMyBook; 
	static DefaultTableModel modelSearchBook; 
	static JLabel lblWelcome;
	public Map<String, String> myBooks;
	public Map<Integer, String> stuMap;
	public Map<String, String> catalog;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StuMain frame = new StuMain();
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
	public StuMain() {
		StudentController controller = new StudentController();
		LibrarianController controller2 = new LibrarianController();
		
		// test 
//		LibrarianController controller4 = new LibrarianController();
		
//		Map<Integer, String> stuMap = ;
//		String student = controller2.getStudentMap().get(Integer.parseInt(AdminLogin.username));
//		System.out.println(student);
//		String[] stuData = student.split(",");
//		System.out.println(stuData.toString());
//		StuMain.lblWelcome.setText("Welcome, " + stuData[1]);
		//
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if (!isCatalogDisplayed) {					
					controller.viewMyBooks();
					controller.viewCatalog();
					controller2.welcomeStudent();
					isCatalogDisplayed = true;
				}	
			}
		});
		setTitle("Library Portal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblWelcome = new JLabel();
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblWelcome.setBounds(637, 10, 222, 28);
		contentPane.add(lblWelcome);
		
		JPanel panel = new JPanel();
		panel.setBounds(517, 10, 1, 1);
		panel.setLayout(null);
		contentPane.add(panel);
		
		JRadioButton rdbtnsearchBkName = new JRadioButton("Search by Book Name");
		rdbtnsearchBkName.setBounds(17, 17, 153, 23);
		panel.add(rdbtnsearchBkName);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(171, 18, 350, 20);
		panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(171, 51, 350, 20);
		panel.add(textField_1);
		
		JRadioButton rdbtnsearchBkAuthor = new JRadioButton("Search by Author");
		rdbtnsearchBkAuthor.setBounds(17, 50, 153, 23);
		panel.add(rdbtnsearchBkAuthor);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 89, 829, 311);
		panel.add(scrollPane);
		
		JButton btnSearchName = new JButton("Search");
		btnSearchName.setBounds(538, 17, 89, 23);
		panel.add(btnSearchName);
		
		JButton btnSearchAuthor = new JButton("Search");
		btnSearchAuthor.setBounds(538, 50, 89, 23);
		panel.add(btnSearchAuthor);
		
		JButton btnRefresh = new JButton("Refresh Catalog");
		btnRefresh.setBounds(672, 17, 153, 54);
		panel.add(btnRefresh);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 22, 849, 485);
		contentPane.add(tabbedPane);
		
		JPanel tabMyBooks = new JPanel();
		tabbedPane.addTab("My Books", null, tabMyBooks, null);
		tabMyBooks.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 824, 377);
		tabMyBooks.add(scrollPane_2);
		
		tableMyBook = new JTable();
		modelMyBook = new DefaultTableModel();
		tableMyBook.setModel(modelMyBook);
		scrollPane_2.setViewportView(tableMyBook);
		
		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnBook returnBook = new ReturnBook();
				returnBook.setVisible(true);
			}
		});
		btnReturnBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnReturnBook.setBounds(655, 399, 179, 41);
		tabMyBooks.add(btnReturnBook);
		
		JButton btnRefreshMyBooks = new JButton("Refresh");
		btnRefreshMyBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCatalogDisplayed) {
					// empty the pre-existing JTable
					StuMain.modelMyBook.setRowCount(0);
					// get the catalog from DB
					StudentController controller2 = new StudentController();
					myBooks = controller2.viewMyBooks();
					
					return;
				}				
			}
		});
		btnRefreshMyBooks.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRefreshMyBooks.setBounds(458, 399, 179, 41);
		tabMyBooks.add(btnRefreshMyBooks);
		
		JPanel tabSearchBook = new JPanel();
		tabbedPane.addTab("Search Books", null, tabSearchBook, null);
		tabSearchBook.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(10, 11, 822, 454);
		tabSearchBook.add(panel_1);
		
		tfBookName = new JTextField();
		tfBookName.setColumns(10);
		tfBookName.setBounds(118, 17, 365, 20);
		panel_1.add(tfBookName);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(118, 50, 365, 20);
		panel_1.add(tfAuthor);
		
		tfPublisher = new JTextField();
		tfPublisher.setColumns(10);
		tfPublisher.setBounds(118, 81, 365, 20);
		panel_1.add(tfPublisher);
		
		// disable tfAuthor and tfPublisher when typing in tfBookName
		tfBookName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				tfAuthor.setEnabled(false);  // disable tfAuthor
				tfPublisher.setEnabled(false);  // disable tfPublisher 
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				tfAuthor.setEnabled(false); // disable tfAuthor 
				tfPublisher.setEnabled(false);  // disable tfPublisher
				if (tfBookName.getText().isEmpty()) {
					tfAuthor.setEnabled(true); // enable tfAuthor if tfBookName is empty
					tfPublisher.setEnabled(true);  // enable tfPublisher if tfBookName is empty
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		}); 
		
		// disable tfBookName and tfPublisher when typing in tfAuthor
		tfAuthor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				tfBookName.setEnabled(false);  // disable tfBookName
				tfPublisher.setEnabled(false);  // disable tfPublisher 
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				tfBookName.setEnabled(false); // disable tfBookName 
				tfPublisher.setEnabled(false);  // disable tfPublisher
				if (tfAuthor.getText().isEmpty()) {
					tfBookName.setEnabled(true); // enable tfBookName if tfAuthor is empty
					tfPublisher.setEnabled(true);  // enable tfPublisher if tfAuthor is empty
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		}); 
		
		// disable tfBookName and tfAuthor when typing in tfPublisher
		tfPublisher.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				tfBookName.setEnabled(false);  // disable tfBookName
				tfAuthor.setEnabled(false);  // disable tfAuthor 
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				tfBookName.setEnabled(false); // disable tfBookName 
				tfAuthor.setEnabled(false);  // disable tfAuthor 
				if (tfPublisher.getText().isEmpty()) {
					tfBookName.setEnabled(true); // enable tfBookName if tfPublisher is empty
					tfAuthor.setEnabled(true);  // enable tfAuthor if tfPublisher is empty
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		}); 
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentController controller3 = new StudentController();
				String title = tfBookName.getText().trim();
				String author = tfAuthor.getText().trim();
				String publisher = tfPublisher.getText().trim();
				
				List<Book> matchBooks = null;
				
				if (!title.isEmpty()) {
					
					matchBooks = controller3.searchBookByTitle(title);
					System.out.println(matchBooks.toString());
				}
				
				if (!author.isEmpty()) {
					matchBooks = controller3.searchBookByName(author);
				}
				
				if (!publisher.isEmpty()) {
					matchBooks = controller3.searchBookByPublisher(publisher);
				}
				
				Object[] colName = { "SN", "Title", "Author", "Publisher", "Price", "Quantity", "Issued Copies", 
						"Added Date"};
				StuMain.modelSearchBook.setColumnIdentifiers(colName);
				
				modelSearchBook.setRowCount(0); // clear pre-existing rows
				
				
				for (Book book: matchBooks) {
					
//					// check the addedDate
					StuMain.modelSearchBook.addRow(new Object[] {book.getSn(), book.getTitle(), book.getAuthor(),
													book.getPublisher(), book.getPrice(), book.getQte(),
													book.getIssuedQte(), book.getDateOfPurchase()});
				}
				
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(505, 17, 89, 84);
		panel_1.add(btnSearch);
		
		JButton btnBorrowBook = new JButton("Borrow Book");
		btnBorrowBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBorrowBook.setBounds(626, 64, 168, 37);
		panel_1.add(btnBorrowBook);
		
		JLabel lblBookName = new JLabel("Book Name");
		lblBookName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBookName.setBounds(27, 21, 81, 14);
		panel_1.add(lblBookName);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAuthor.setBounds(27, 52, 81, 14);
		panel_1.add(lblAuthor);
		
		JLabel lblPublisher = new JLabel("Publisher");
		lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPublisher.setBounds(27, 83, 81, 14);
		panel_1.add(lblPublisher);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 119, 802, 304);
		panel_1.add(scrollPane_1);
		
		modelSearchBook = new DefaultTableModel();
		tableSearchBook = new JTable();
		tableSearchBook.setModel(modelSearchBook);
		scrollPane_1.setViewportView(tableSearchBook);
		
		JButton btnRefreshBookList = new JButton("Refresh Book List");
		btnRefreshBookList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCatalogDisplayed) {
					// empty the pre-existing JTable
					StuMain.modelSearchBook.setRowCount(0);
					// get the catalog from DB
					StudentController controller3 = new StudentController();
					catalog = controller3.viewCatalog();
					
					return;
				}
				
				
			}
		});
		btnRefreshBookList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRefreshBookList.setBounds(626, 16, 168, 37);
		panel_1.add(btnRefreshBookList);
	}
}
