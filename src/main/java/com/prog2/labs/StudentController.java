package com.prog2.labs;

import java.util.List;
import java.util.Map;

public class StudentController {
	private Student model;
	private StuMain view;
	
	public StudentController() {
		this.model = new Student();
	}
	
	public StudentController(Student model, StuMain view) {
		this.model = model;
		this.view = view;
	}
	
	public Map<String, String> viewCatalog() {
		return model.viewCatalog();
	}
	
	public Map<String, String> viewMyBooks() {
		return model.viewMyBooks();
	}
	
	public Map<String, String> getCatalog() {
		return model.getCatalog();
	}
	
	public boolean returnBook(String bookSN, String studID) {
		return model.returnBook(bookSN, studID);
	}
	public List<Book> searchBookByTitle (String title){
		return model.searchBookByTitle(title);
	}
	public List<Book> searchBookByName (String name){
		return model.searchBookByName(name);
	}
	public List<Book> searchBookByPublisher (String publisher){
		return model.searchBookByPublisher(publisher);
	}
	public boolean issueBook (Book b, Student s) {
		return model.issueBook(b, s);
	}
}
