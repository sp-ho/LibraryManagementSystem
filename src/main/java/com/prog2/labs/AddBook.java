package com.prog2.labs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class AddBook extends JFrame {

	private JPanel contentPane;
	private JTextField tfABBkId;
	private JTextField tfABBkName;
	private JTextField tfABPublisher;
	private JTextField tfABAuthor;
	private JTextField tfABBkPrice;
	private JTextField tfABQte;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBook frame = new AddBook();
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
	public AddBook() {
		
		LibrarianController controller = new LibrarianController(new Book(), null, null, null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // EXITONCLOSE
		setBounds(100, 100, 368, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddBook = new JLabel("Add Book");
		lblAddBook.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAddBook.setBounds(21, 21, 124, 34);
		contentPane.add(lblAddBook);
		
		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBookId.setBounds(21, 66, 93, 14);
		contentPane.add(lblBookId);
		
		JLabel lblBookName = new JLabel("Book Name");
		lblBookName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBookName.setBounds(21, 93, 93, 14);
		contentPane.add(lblBookName);
		
		JLabel lblPublisher = new JLabel("Publisher");
		lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPublisher.setBounds(21, 149, 93, 14);
		contentPane.add(lblPublisher);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAuthor.setBounds(21, 120, 93, 14);
		contentPane.add(lblAuthor);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrice.setBounds(21, 176, 93, 14);
		contentPane.add(lblPrice);
		
		tfABBkId = new JTextField();
		tfABBkId.setColumns(10);
		tfABBkId.setBounds(134, 64, 192, 20);
		contentPane.add(tfABBkId);
		
		tfABBkName = new JTextField();
		tfABBkName.setColumns(10);
		tfABBkName.setBounds(134, 91, 192, 20);
		contentPane.add(tfABBkName);
		
		tfABPublisher = new JTextField();
		tfABPublisher.setColumns(10);
		tfABPublisher.setBounds(134, 145, 192, 20);
		contentPane.add(tfABPublisher);
		
		tfABAuthor = new JTextField();
		tfABAuthor.setColumns(10);
		tfABAuthor.setBounds(134, 118, 192, 20);
		contentPane.add(tfABAuthor);
		
		tfABBkPrice = new JTextField();
		tfABBkPrice.setColumns(10);
		tfABBkPrice.setBounds(134, 174, 192, 20);
		contentPane.add(tfABBkPrice);
		
		JButton btnAddBk = new JButton("Add Book");
		btnAddBk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sn = tfABBkId.getText();
				String title = tfABBkName.getText();
				String author = tfABAuthor.getText();
				String publisher = tfABPublisher.getText();
				Double price = Double.parseDouble(tfABBkPrice.getText());
				int qte = Integer.parseInt(tfABQte.getText());
				int issued = 0;
				LocalDate dateOfPurchase = LocalDate.now();
								
				// create a Book instance (set issued quantity to zero (7th arg))
				Book newBook = new Book(sn, title, author, publisher, price, qte, issued, dateOfPurchase);
				
				// add newbook to Book HashMap
				LibrarianController.addBook(newBook, "books");				
			}
		});
		btnAddBk.setBounds(134, 246, 105, 23);
		contentPane.add(btnAddBk);
		
		// close window when Cancel is clicked
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(249, 246, 77, 23);
		contentPane.add(btnCancel);
		
		tfABQte = new JTextField();
		tfABQte.setColumns(10);
		tfABQte.setBounds(134, 201, 192, 20);
		contentPane.add(tfABQte);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblQuantity.setBounds(21, 203, 93, 14);
		contentPane.add(lblQuantity);
	}
}
