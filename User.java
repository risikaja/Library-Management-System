package libraryManagementSystem;

import java.util.ArrayList;

public class User {

    private String username;
    private String id;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User(String username, String id) {
        this.username = username;
        this.id = id;
    }
    
    public User() {
    	
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    
    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
    	this.borrowedBooks = borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public String toString() {
        return getUsername() + "," + getId() + "," + getBorrowedBooks();
    }

    
}
