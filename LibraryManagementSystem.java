package libraryManagementSystem;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class LibraryManagementSystem {

    static Library library = new Library();
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
     
        library.setBookInventory(readAndSaveToBooks());
        library.setListOfUsers(readAndSaveToUsers());
         int choice;
         do {
             displayMenu();
             System.out.print("Enter your choice: ");
             choice = scanner.nextInt();
             scanner.nextLine(); 

             switch (choice) {
                 case 1:
                     addBook();
                     break;
                 case 2:
                     removeBook();
                     break;
                 case 3:
                     searchBook();
                     break;
                 case 4:
                     displayAvailableBooks();
                     break;
                 case 5:
                     registerUser();
                     break;
                 case 6:
                     borrowBook();
                     break;
                 case 7:
                     returnBook();
                     break;
                 case 8:
                     displayLibraryInfo();
                     break;
                 case 0:
                     System.out.println("Exiting the Library Management System!");
                     saveBooksToFile(library.getBooks());
                     saveUsersToFile(library.getUsers());
                     break;
                 default:
                     System.out.println("Invalid choice. Please try again.");
             }
         } while (choice != 0);
     }


    private static void displayMenu() {
        System.out.println("\nLibrary Management System Menu:");
        System.out.println("1. Add a Book");
        System.out.println("2. Remove a Book");
        System.out.println("3. Search for a Book");
        System.out.println("4. Display Available Books");
        System.out.println("5. Register User");
        System.out.println("6. Borrow a Book");
        System.out.println("7. Return a Book");
        System.out.println("8. Display Library Information");
        System.out.println("0. Exit");
    }

    private static void addBook() {
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();
            System.out.print("Enter book author: ");
            String author = scanner.nextLine();
            System.out.print("Enter book ISBN: ");
            String ISBN = scanner.nextLine();
            System.out.print("Enter book quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); 

            Book book = new Book(title, author, ISBN, quantity);
            library.addBook(book);
            
 }

    private static void removeBook() {
        System.out.print("Enter the title of the book to remove: ");
        String bookTitle = scanner.nextLine();
        ArrayList<Book> searchResults = library.searchBook(bookTitle);

        if (!searchResults.isEmpty()) {
            if (searchResults.size() > 1) {
                System.out.println("Multiple books found. Select the book to remove:");
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.println((i + 1) + ". " + searchResults.get(i));
                }

                System.out.print("Enter the number of the book to remove: ");
                int bookIndex = scanner.nextInt();
                scanner.nextLine(); 

                if (bookIndex > 0 && bookIndex <= searchResults.size()) {
                    Book selectedBook = searchResults.get(bookIndex - 1);
                    library.removeBook(selectedBook);
                } else {
                    System.out.println("Invalid selection. No book removed.");
                }
            } else {
                
                Book selectedBook = searchResults.get(0);
                library.removeBook(selectedBook);
            }
        } else {
            System.out.println("No matching books found. No book removed.");
        }
    }


    private static void searchBook() {
        System.out.print("Enter the title of the book to search: ");
        String bookTitle = scanner.nextLine();
        ArrayList<Book> searchResults = library.searchBook(bookTitle);

        if (!searchResults.isEmpty()) {
            System.out.println("Search results:");
            for (Book book : searchResults) {
                System.out.println(book);
            }
        } else {
            System.out.println("No matching books found.");
        }
    }

    private static void displayAvailableBooks() {
        ArrayList<Book> availableBooks = library.getAvailableBooks();

        if (!availableBooks.isEmpty()) {
            System.out.println("Available Books:");
            for (Book book : availableBooks) {
                System.out.println(book);
            }
        } else {
            System.out.println("No available books.");
        }
    }

    private static void registerUser() {
    	
    		
    	System.out.print("Enter user username: ");
        String username = scanner.nextLine();
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        User user = new User(username, id);
        library.registerUser(user);
        
       
    }

    private static void borrowBook() {
        System.out.print("Enter the username of the borrower: ");
        String username = scanner.nextLine(); 

        User user = library.getUser(username);

        if (user != null) {
            System.out.print("Enter the title of the book to borrow: ");
            String bookTitle = scanner.nextLine(); 
            Book book = library.searchBook(bookTitle).stream().findFirst().orElse(null);

            if (book != null && book.getQuantity() > 0) {
                user.borrowBook(book);
                book.setQuantity(book.getQuantity() - 1);
                System.out.println("Book borrowed successfully.");
            } else {
                if (book == null) {
                    System.out.println("Book not found.");
                } else {
                    System.out.println("Book not available for borrowing.");
                }
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void returnBook() {
        System.out.print("Enter the username of the borrower: ");
        String username = scanner.nextLine();  

        User user = library.getUser(username);

        if (user != null) {
            System.out.print("Enter the title of the book to return: ");
            String bookTitle = scanner.nextLine();  

         
            List<Book> borrowedBooks = user.getBorrowedBooks();
            Optional<Book> optionalBook = borrowedBooks.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle))
                    .findFirst();

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                borrowedBooks.remove(book);  
                book.setQuantity(book.getQuantity() + 1);  
                System.out.println("Book returned successfully.");

                
                library.removeBook(book); 
                library.addBook(book);     
            } else {
                System.out.println("Book not found in the user's borrowed books.");
            }
        } else {
            System.out.println("User not found.");
        }
    }



    private static void displayLibraryInfo() {
        System.out.println(library);
    }
    
    public static void saveBooksToFile(ArrayList<Book> books) {
    	
		try {
			
			Formatter file = new Formatter("booklist.txt");
			
			for(Book book : books) {
				
				file.format("%s%n " , book.toString());

			}
			
			file.close();
			
		} catch( Exception e ) {
			System.out.println("Error " + e.getMessage());
		}
		
	}

    public static void saveUsersToFile(ArrayList<User> users) {
    	
		try {
			
			Formatter file = new Formatter( "userlist.txt");
			
			for(User user : users) {
				
				file.format("%s%n " , user.toString() );

			}
			
			
			file.close();
			
		} catch( Exception e ) {
			System.out.println("Error " + e.getMessage());
		}
		
	}
    
    public static ArrayList<Book> readAndSaveToBooks() {
    ArrayList<Book> bookList = new ArrayList<>();

    try (Scanner scan = new Scanner(Paths.get("C:" + File.separator + "Users" + File.separator + "User" +
            File.separator + "eclipse-workspace" + File.separator + "libraryManagementSystem" +
            File.separator + "booklist.txt"))) {

        while (scan.hasNextLine()) {
            try {
                String line = scan.nextLine();
                
                
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

               
                if (parts.length >= 4) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String ISBN = parts[2].trim();
                    int quantity = Integer.parseInt(parts[3].trim());

                    Book newBook = new Book(title, author, ISBN, quantity);
                    bookList.add(newBook);
                } else {
                    System.out.println("Invalid input line: " + line);
                    
                }

            } catch (NumberFormatException e) {
                System.out.println("Error reading input line: " + e.getMessage());
                
            }
        }
     
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }

    return bookList;
}

    public static ArrayList<User> readAndSaveToUsers() {
        ArrayList<User> userList = new ArrayList<>();

        try (Scanner scan = new Scanner(Paths.get("C:" + File.separator + "Users" + File.separator + "User" +
                File.separator + "eclipse-workspace" + File.separator + "libraryManagementSystem" +
                File.separator + "userlist.txt"))) {

            while (scan.hasNextLine()) {
                try {
                    String line = scan.nextLine();

                    
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] parts = line.split(",");

                   
                    if (parts.length < 2) {
                        System.out.println("Invalid input line: " + line);
                        continue;
                    }

                    String username = parts[0].trim();
                    String id = parts[1].trim();

                    
                    if (parts.length > 2) {
                        
                        String booksPart = line.substring(line.indexOf('[') + 1, line.lastIndexOf(']'));

                        
                        ArrayList<Book> userBooks = new ArrayList<>();

                        
                        if (!booksPart.isEmpty()) {
                            
                            String[] booksArray = booksPart.split(";");

                            
                            for (String bookDetails : booksArray) {
                                String[] bookInfo = bookDetails.split(",");
                                String title = bookInfo[0].trim();
                                String author = bookInfo[1].trim();
                                String ISBN = bookInfo[2].trim();
                                int quantity = Integer.parseInt(bookInfo[3].trim());

                                
                                Book book = new Book(title, author, ISBN, quantity);
                                userBooks.add(book);
                            }
                        }

                        
                        User newUser = new User(username, id);
                        newUser.setBorrowedBooks(userBooks);
                        userList.add(newUser);
                    } else {
                        
                        User newUser = new User(username, id);
                        userList.add(newUser);
                    }

                } catch (Exception e) {
                    System.out.println("Error reading input line: " + e.getMessage());
                    
                }
            }
          
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return userList;
    }
    
}

