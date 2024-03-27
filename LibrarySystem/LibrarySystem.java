import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book implements Serializable {
    private String title;
    private String author;
    private String isbn;
    private boolean checkedOut;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.checkedOut = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
}

class Patron implements Serializable {
    private String name;
    private double fines;

    public Patron(String name) {
        this.name = name;
        this.fines = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getFines() {
        return fines;
    }

    public void addFine(double amount) {
        fines += amount;
    }
}

class LibraryTransaction implements Serializable {
    private String bookIsbn;
    private String patronName;
    private LocalDate checkoutDate;
    private LocalDate returnDate;
    private boolean returned;

    public LibraryTransaction(String bookIsbn, String patronName, LocalDate checkoutDate) {
        this.bookIsbn = bookIsbn;
        this.patronName = patronName;
        this.checkoutDate = checkoutDate;
        this.returnDate = null;
        this.returned = false;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getPatronName() {
        return patronName;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}

public class LibrarySystem {
    private List<Book> books;
    private List<Patron> patrons;
    private List<LibraryTransaction> transactions;

    public LibrarySystem() {
        books = new ArrayList<>();
        patrons = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
    }

    public void checkoutBook(String isbn, String patronName, LocalDate checkoutDate) {
        Book book = findBookByISBN(isbn);
        Patron patron = findPatronByName(patronName);

        if (book != null && patron != null && !book.isCheckedOut()) {
            book.setCheckedOut(true);
            transactions.add(new LibraryTransaction(isbn, patronName, checkoutDate));
            System.out.println("Book checked out successfully.");
        } else {
            System.out.println("Unable to check out the book.");
        }
    }

    public void returnBook(String isbn, LocalDate returnDate) {
        Book book = findBookByISBN(isbn);
        if (book != null && book.isCheckedOut()) {
            for (LibraryTransaction transaction : transactions) {
                if (transaction.getBookIsbn().equals(isbn) && !transaction.isReturned()) {
                    transaction.setReturned(true);
                    transaction.setReturnDate(returnDate);
                    book.setCheckedOut(false);
                    System.out.println("Book returned successfully.");
                    return;
                }
            }
            System.out.println("No such transaction found.");
        } else {
            System.out.println("Book is not checked out.");
        }
    }

    public void displayBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (!book.isCheckedOut()) {
                System.out.println(book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getIsbn() + ")");
            }
        }
    }

    public void displayPatrons() {
        System.out.println("Patrons:");
        for (Patron patron : patrons) {
            System.out.println(patron.getName() + " - Fines: $" + patron.getFines());
        }
    }

    private Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private Patron findPatronByName(String name) {
        for (Patron patron : patrons) {
            if (patron.getName().equals(name)) {
                return patron;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        Scanner scanner = new Scanner(System.in);

        try {
            FileInputStream fis = new FileInputStream("library_data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            library = (LibrarySystem) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing data found. Starting with a new library system.");
        }

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Patron");
            System.out.println("3. Checkout Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Available Books");
            System.out.println("6. Display Patrons");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String isbn = scanner.nextLine();
                    library.addBook(new Book(title, author, isbn));
                    break;
                case 2:
                    System.out.print("Enter patron name: ");
                    String patronName = scanner.nextLine();
                    library.addPatron(new Patron(patronName));
                    break;
                case 3:
                    System.out.print("Enter book ISBN to checkout: ");
                    String checkoutIsbn = scanner.nextLine();
                    System.out.print("Enter patron name: ");
                    String checkoutPatron = scanner.nextLine();
                    library.checkoutBook(checkoutIsbn, checkoutPatron, LocalDate.now());
                    break;
                case 4:
                    System.out.print("Enter book ISBN to return: ");
                    String returnIsbn = scanner.nextLine();
                    library.returnBook(returnIsbn, LocalDate.now());
                    break;
                case 5:
                    library.displayBooks();
                    break;
                case 6:
                    library.displayPatrons();
                    break;
                case 7:
                    try {
                        FileOutputStream fos = new FileOutputStream("library_data.ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(library);
                        oos.close();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Exiting program. Data saved.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
