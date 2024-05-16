package libraryManagementSystem;

import java.util.*;

public class Book {
	
	private String title;
	private String author;
	private String ISBN;
	private int quantity;
	
	public Book(String title, String author, String ISBN, int quantity) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.quantity = quantity;
	}
	
	public Book() {
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getISBN() {
		return ISBN;
	}
	
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString() {
		return getTitle() + "," + getAuthor() + "," + getISBN() + 
				"," + getQuantity();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Book otherBook = (Book) obj;
	    return Objects.equals(ISBN, otherBook.ISBN);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(ISBN);
	}
	
}
