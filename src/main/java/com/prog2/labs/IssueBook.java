package com.prog2.labs;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class IssueBook extends JFrame {

	private JPanel contentPane;
	private JTextField tfStuId;
	private JTextField tfStuName;
	private JTextField tfBookSN;
	private JTextField tfStudentContact;
	public Map<String, String> catalog;
	private JTextField tfBookName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IssueBook frame = new IssueBook();
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
	public IssueBook() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 372, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIssueBook = new JLabel("Issue Book");
		lblIssueBook.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblIssueBook.setBounds(21, 11, 124, 34);
		contentPane.add(lblIssueBook);
		
		JLabel lblStuId = new JLabel("Student ID");
		lblStuId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStuId.setBounds(21, 56, 93, 14);
		contentPane.add(lblStuId);
		
		JLabel lblStuName = new JLabel("Student Name");
		lblStuName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStuName.setBounds(21, 83, 93, 14);
		contentPane.add(lblStuName);
		
		JLabel lblBookSN = new JLabel("Book SN");
		lblBookSN.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBookSN.setBounds(21, 139, 93, 14);
		contentPane.add(lblBookSN);
		
		JLabel lblStudentContact = new JLabel("Student Contact");
		lblStudentContact.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStudentContact.setBounds(21, 110, 111, 14);
		contentPane.add(lblStudentContact);
		
		tfStuId = new JTextField();
		tfStuId.setBounds(134, 54, 192, 20);
		contentPane.add(tfStuId);
		tfStuId.setColumns(10);
		
		tfStuName = new JTextField();
		tfStuName.setColumns(10);
		tfStuName.setBounds(134, 81, 192, 20);
		contentPane.add(tfStuName);
		
		tfBookSN = new JTextField();
		tfBookSN.setColumns(10);
		tfBookSN.setBounds(134, 135, 192, 20);
		contentPane.add(tfBookSN);
		
		tfStudentContact = new JTextField();
		tfStudentContact.setColumns(10);
		tfStudentContact.setBounds(134, 108, 192, 20);
		contentPane.add(tfStudentContact);
		
		JButton btnIssueBook = new JButton("Issue Book");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarianController controller2 = new LibrarianController();
				catalog = controller2.getCatalog();
				int stuId = Integer.parseInt(tfStuId.getText());
				String stuName = tfStuName.getText();
				String StuContact = tfStudentContact.getText();
 				String bookSN = tfBookSN.getText();
				String bookName = tfBookName.getText();
				
				// note: add condition for matching student's value
				if (controller2.getStudentMap().containsKey(stuId)) {
					String book = catalog.get(bookSN);
						System.out.println();
						System.out.println("check: " + book); // check
					String studentString = controller2.getStudentMap().get(stuId);
						System.out.println("check: " + studentString); // check
						System.out.println("check available: " + book.substring(book.length() - 1)); // check
						System.out.println();
					if(book.substring(book.length() - 1).equals("Y")) {
						String[] bookData = book.split(",");
						String[] studentData = studentString.split(",");
						Student tempStudent = new Student(Integer.parseInt(studentData[0]), studentData[1], studentData[2]);
						Book tempBook = new Book(bookData[0], bookData[1], bookData[2], bookData[3], Double.parseDouble(bookData[4]), 
													Integer.parseInt(bookData[5]), Integer.parseInt(bookData[6]), 
													LocalDate.parse(bookData[7]));
//													Integer.parseInt(bookData[7]), LocalDate.parse(bookData[8]));	
						controller2.issueBook(tempBook, tempStudent);
						JOptionPane.showMessageDialog(null, "Book is issued successfully.", "Book issued", 2);
					} else JOptionPane.showMessageDialog(null, "Book is not available currently.", "Book Not Avaiable", 2);
				} else JOptionPane.showMessageDialog(null, "Student is not a member.", "Ineligible student", 2);
			}
		});
		btnIssueBook.setBounds(134, 203, 99, 23);
		contentPane.add(btnIssueBook);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(251, 203, 74, 23);
		contentPane.add(btnCancel);
		
		tfBookName = new JTextField();
		tfBookName.setColumns(10);
		tfBookName.setBounds(134, 161, 192, 20);
		contentPane.add(tfBookName);
		
		JLabel lblBookName_1 = new JLabel("Book Name");
		lblBookName_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBookName_1.setBounds(21, 165, 93, 14);
		contentPane.add(lblBookName_1);
	}
}
