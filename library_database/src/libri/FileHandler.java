package libri;

import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String BOOKS_FILE = "C:\\Users\\xhind\\eclipse-workspace\\New folder\\libri\\src\\libri\\books.txt";
    private static final String AUTHORS_FILE = "C:\\Users\\xhind\\eclipse-workspace\\New folder\\libri\\src\\libri\\authors.txt";
    private Map<String, Author> authors;

    public FileHandler(Map<String, Author> authors) {
        this.authors = authors;
    }

    public void saveBooksToFile(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor().getName() + "," + book.getQuantity() + "," + book.getPrice());
                writer.newLine();
            }
            System.out.println("Books saved to file: " + BOOKS_FILE);
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }

    public void saveAuthorsToFile(Map<String, Author> authors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AUTHORS_FILE))) {
            for (Author author : authors.values()) {
                writer.write(author.getName() + "," + author.getBirthdate());
                writer.newLine();
            }
            System.out.println("Authors saved to file: " + AUTHORS_FILE);
        } catch (IOException e) {
            System.out.println("Error saving authors to file: " + e.getMessage());
        }
    }

    public List<Book> readBooksFromFile() {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String title = data[0];
                    String authorName = data[1];
                    int quantity = Integer.parseInt(data[2]);
                    double price = Double.parseDouble(data[3]);

                    Author author = getAuthorByName(authorName);
                    if (author == null) {
                        author = new Author(authorName);
                        authors.put(authorName, author);
                    }

                    Book book = new Book(title, author, quantity, price);
                    books.add(book);
                }
            }
            System.out.println("Books loaded from file: " + BOOKS_FILE);
        } catch (IOException e) {
            System.out.println("Error reading books from file: " + e.getMessage());
        }

        return books;
    }

    public Map<String, Author> readAuthorsFromFile() {
        Map<String, Author> authors = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(AUTHORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String name = data[0];
                    String birthdate = data[1];

                    Author author = new Author(name);
                    authors.put(name, author);
                }
            }
            System.out.println("Authors loaded from file: " + AUTHORS_FILE);
        } catch (IOException e) {
            System.out.println("Error reading authors from file: " + e.getMessage());
        }

        return authors;
    }

    private Author getAuthorByName(String authorName) {
        for (Author author : authors.values()) {
            if (author.getName().equalsIgnoreCase(authorName)) {
                return author;
            }
        }
        return null;
    }
}
