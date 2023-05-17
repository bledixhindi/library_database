package libri;

import java.io.*;
import java.util.*;

public class LibraryDatabase {
    private List<Book> books;
    private Map<String, Author> authors;
    private FileHandler fileHandler;

    public LibraryDatabase() {
        books = new ArrayList<>();
        authors = new HashMap<>();
        fileHandler = new FileHandler(authors);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Add a book");
            System.out.println("2. Add an author");
            System.out.println("3. Remove a book");
            System.out.println("4. Remove an author");
            System.out.println("5. Search for a book");
            System.out.println("6. Search for an author");
            System.out.println("7. Save data to file");
            System.out.println("8. Show text files");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    addAuthor(scanner);
                    break;
                case 3:
                    removeBook(scanner);
                    break;
                case 4:
                    removeAuthor(scanner);
                    break;
                case 5:
                    searchBook(scanner);
                    break;
                case 6:
                    searchAuthor(scanner);
                    break;
                case 7:
                    saveToFile();
                    break;
                case 8:
                    showTextFiles();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (choice != 9);

        scanner.close();
    }

    private void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine();
        System.out.print("Enter book quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter book price: ");
        double price = scanner.nextDouble();

        Author author = authors.get(authorName);
        if (author == null) {
            author = new Author(authorName);
            authors.put(authorName, author);
        }

        Book book = new Book(title, author, quantity, price);
        books.add(book);

        System.out.println("Book added successfully.");
    }

    private void addAuthor(Scanner scanner) {
        System.out.print("Enter author name: ");
        String name = scanner.nextLine();
        System.out.print("Enter author birthdate: ");
        String birthdate = scanner.nextLine();

        Author author = new Author(name);
        authors.put(name, author);

        System.out.println("Author added successfully.");
    }

    private void removeBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        boolean found = false;
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().equals(title)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private void removeAuthor(Scanner scanner) {
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine();

        boolean found = false;
        Iterator<Map.Entry<String, Author>> iterator = authors.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Author> entry = iterator.next();
            if (entry.getKey().equals(authorName)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            // Remove books by the author
            Iterator<Book> bookIterator = books.iterator();
            while (bookIterator.hasNext()) {
                Book book = bookIterator.next();
                if (book.getAuthor().getName().equals(authorName)) {
                    bookIterator.remove();
                }
            }

            System.out.println("Author and associated books removed successfully.");
        } else {
            System.out.println("Author not found.");
        }
    }

    private void searchBook(Scanner scanner) {
        System.out.print("Enter book title to search: ");
        String searchTitle = scanner.nextLine();

        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(searchTitle)) {
                System.out.println("Book found:");
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor().getName());
                System.out.println("Quantity: " + book.getQuantity());
                System.out.println("Price: " + book.getPrice());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    private void searchAuthor(Scanner scanner) {
        System.out.print("Enter author name to search: ");
        String searchAuthor = scanner.nextLine();

        boolean found = false;
        for (Author author : authors.values()) {
            if (author.getName().equalsIgnoreCase(searchAuthor)) {
                System.out.println("Author found:");
                System.out.println("Name: " + author.getName());
                System.out.println("Birthdate: " + author.getBirthdate());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Author not found.");
        }
    }

    private void saveToFile() {
        fileHandler.saveBooksToFile(books);
        fileHandler.saveAuthorsToFile(authors);

        System.out.println("Data saved to file successfully.");
    }

    private void showTextFiles() {
        List<Book> loadedBooks = fileHandler.readBooksFromFile();
        Map<String, Author> loadedAuthors = fileHandler.readAuthorsFromFile();

        System.out.println("Books:");
        for (Book book : loadedBooks) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor().getName());
        }

        System.out.println("Authors:");
        for (Author author : loadedAuthors.values()) {
            System.out.println("Name: " + author.getName() + ", Birthdate: " + author.getBirthdate());
        }
    }

    public static void main(String[] args) {
        LibraryDatabase libraryDatabase = new LibraryDatabase();
        libraryDatabase.run();
    }
}
