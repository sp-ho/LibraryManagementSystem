package com.prog2.labs;
// Student class

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class studentAdrian {
	// instance variables
	private int stId;
	private String name;
	private String contact;
	
	public studentAdrian() {}

	public studentAdrian(int stId, String name, String contact) {
		super();
		this.stId = stId;
		this.name = name;
		this.contact = contact;
	}
	
	// searchBookByTitle method
	public List<Book> searchBookByTitle(String title) {
	    Map<String, String> bookStringMap = Book.viewCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                return new Book(values[0], values[1], values[2]);
	            })
	            .filter(book -> book.getTitle().equals(title))
	            .collect(Collectors.toList());
	    return matchingBooks;
	}
	
	// searchBookByName method
	public List<Book> searchBookByName(String name) {
	    Map<String, String> bookStringMap = Book.viewCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                return new Book(values[0], values[1], values[2]);
	            })
	            .filter(book -> book.getAuthor().equals(name))
	            .collect(Collectors.toList());
	    return matchingBooks;
	}
	// serahcBookByPublisher method
	public List<Book> searchBookByPublisher(String publisher) {
	    Map<String, String> bookStringMap = Book.viewCatalog();
	    List<Book> matchingBooks = bookStringMap.values()
	            .stream()
	            .map(value -> {
	                String[] values = value.split(",");
	                return new Book(values[0], values[1], values[2]);
	            })
	            .filter(book -> book.getPublisher().equals(publisher))
	            .collect(Collectors.toList());
	    return matchingBooks;
	}

	// viewCatalog method
	public Map<String, String> viewCatalog () {
		return Book.viewCatalog();
		
	}

	// borrow method
	public boolean borrow (Book b) {
		// if book is available decreasehttps://marketplace.eclipse.org/marketplace-client-intro?mpc_install=5012014 quantity by 1, increase issued by 1
		if(b.getQte() != 0) {
			
			b.setQte(b.getQte() - 1);
			b.setIssuedQte(b.getIssuedQte() + 1);
			// after that, add book to issued book DB table (maybe call a issueBook
			// method here that will be a clone of issue book in book class.
			Book.addBook(b, "issuedBooks");
			return true;
		}
		return false;
	}
	
	// toReturn method
	public boolean toReturn (Book b) {
		return false;
		
	}
}

