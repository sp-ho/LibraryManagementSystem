package com.prog2.labs;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AdminLogin extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsername;
	private JPasswordField passwordField;
	public JButton btnLogin;
	private JLabel lblNMessage;
	private static String dbName = "db_lib"; 
	static String username;
	private static String password;
	private static DbConn dbConnection;
	public JRadioButton rdbtnLibrarian;
	public JRadioButton rdbtnStudent;
	static AdminLogin frame;
	public Map<Integer, String> stuMap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AdminLogin();
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
	public AdminLogin() {
		setTitle("Library Portal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 321);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Login");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitle.setBounds(125, 11, 81, 50);
		contentPane.add(lblTitle);
		
		JLabel lblUser = new JLabel("Username");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUser.setBounds(22, 107, 68, 14);
		contentPane.add(lblUser);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(22, 125, 248, 20);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPass.setBounds(22, 156, 68, 14);
		contentPane.add(lblPass);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(22, 175, 248, 20);
		contentPane.add(passwordField);
		
		lblNMessage= new JLabel("");
		lblNMessage.setForeground(new Color(255, 0, 0));
		lblNMessage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNMessage.setBounds(22, 206, 248, 14);
		contentPane.add(lblNMessage);
		
		JRadioButton rdbtnLibrarian = new JRadioButton("Librarian");
		rdbtnLibrarian.setBounds(55, 61, 88, 23);
		contentPane.add(rdbtnLibrarian);
		
		JRadioButton rdbtnStudent = new JRadioButton("Student");
		rdbtnStudent.setBounds(164, 61, 109, 23);
		contentPane.add(rdbtnStudent);
		
		// login button
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get instance of the created connection
				dbConnection = DbConn.getInstance(dbName);
				
				// retrieve values of username and password entered by the user
				username = tfUsername.getText();
				password = String.valueOf(passwordField.getPassword());				
				
				// check if the fields are empty
				if (username.trim().equals("") || password.trim().equals("")) { 	
					JOptionPane.showMessageDialog(null, "Enter your username and password.", "Empty field(s)", 2);
					tfUsername.requestFocus();
				} else {  // if the fields are not empty
					// librarian: check if username and password are correct
					if (rdbtnLibrarian.isSelected() && username.equals("lib") && password.equals("123")) {		
						AdminMain m = new AdminMain(); // open the AdminMain window
						m.setVisible(true);
						Connection connection = dbConnection.getConnection(); // connect to the database
						frame.dispose(); // close AdminLogin window after librarian login successfully
					} else if (rdbtnStudent.isSelected() && password.equals("123")) {
						// student: check if username and password are correct
						StuMain s = new StuMain();  // open the StuMain window
						s.setVisible(true);					
						Connection connection = dbConnection.getConnection(); // connect to the database	
						frame.dispose(); // close AdminLogin window after student login successfully
					} else { // if the username and password do not match
						JOptionPane.showMessageDialog(null, "Username and password do not match.", "Unmatched field(s)", 2);
						tfUsername.setText("");
						passwordField.setText("");
						tfUsername.requestFocus();
					}	
				}
			}
		});
		btnLogin.setBounds(182, 217, 88, 23);
		contentPane.add(btnLogin);
	}
}


