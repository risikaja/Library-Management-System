package libraryManagementSystem;

import java.util.*;

public class Library {

	private ArrayList<Book> bookInventory = new ArrayList<>();
	private ArrayList<User> listOfUsers = new ArrayList<>();


	public void addBook(Book book) {
		bookInventory.add(book);
	}
	
	public void removeBook(Book book) {
	    if (bookInventory.remove(book)) {
	        System.out.println("Book removed successfully.");
	    } else {
	        System.out.println("Book not found in inventory.");
	    }
	}
	
	public ArrayList<Book> searchBook(String bookName){
		ArrayList<Book> results = new ArrayList<>();
        for (Book book : bookInventory) {
            if (book.getTitle().contains(bookName)) {
                results.add(book);
            }
        }
        return results;
	}
	
	public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book book : bookInventory) {
            if (book.getQuantity() > 0) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }
	
	public void registerUser(User user) {
		listOfUsers.add(user);
	}
	
	public User getUser(String Username) {
        for (User user : listOfUsers) {
            if (user.getUsername().equals(Username)) {
                return user;
            }

        }
        
        return null; 
    }
	
	public String toString() {
        return "Library with " + bookInventory.size() + " books and " + listOfUsers.size() + " users.";
    }
	
	
	
	public ArrayList<Book> getBooks() {
		return bookInventory;
	}
	
	public ArrayList<User> getUsers() {
		return listOfUsers;
	}
	
	public void setBookInventory(ArrayList<Book> books) {
		this.bookInventory = books;
	}
	
	public void setListOfUsers(ArrayList<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}
	
	
	
}
