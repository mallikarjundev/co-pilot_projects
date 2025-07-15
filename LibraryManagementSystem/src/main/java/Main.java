import model.Book;
import service.LibrarySerivceImpl;
import service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LibraryService library = new LibrarySerivceImpl();
        try(Scanner sc = new Scanner(System.in)) {
            int choice=0;
            boolean flag=true;
            while (flag) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Add Book");
                System.out.println("2. View All Books");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. Exit");
                System.out.println("Enter your choice:");
                try {
                    choice=Integer.parseInt(sc.nextLine());
                }catch (NumberFormatException e){
                    System.out.println("Invalid input. Please enter a number b/w 1-5.");
                    continue;
                }
                switch (choice){
                    case 1:
                        System.out.println("Enter ISBN: ");
                        String isbn = sc.nextLine();
                        System.out.println("Enter Title: ");
                        String title=sc.nextLine();
                        System.out.println("Enter Author:");
                        String author=sc.nextLine();
                        System.out.println("Enter Available Copies:");
                        int copies;
                        try {
                            copies=Integer.parseInt(sc.nextLine());
                            if (copies<0){
                                System.out.println("Copies can't be negative.");
                                break;
                            }
                        } catch (NumberFormatException e){
                            System.out.println("Invalid input. Please enter a number b/w 1-5.");
                            break;
                        }
                        sc.nextLine();

                        Book newBook = new Book(isbn,title,author,copies);
                        library.addBook(newBook);
                        System.out.println("Book added successfully");
                        break;

                    case 2:
                        List<Book> books = library.getAllBooks();
                        if (books.isEmpty()){
                            System.out.println("No books available.");
                        }else {
                            System.out.println("Available Books:");
                            for (Book book: books){
                                System.out.println(book);
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Enter ISBN to borrow: ");
                        String isbnBorrowed = sc.nextLine();
                        boolean borrowed = library.borrowBook(isbnBorrowed);
                        if (borrowed){
                            System.out.println("Book borrowed successfully.");
                        }else {
                            System.out.println("Book not found or isbn not found.");
                        }
                        break;
                    case 4:
                        System.out.println("Enter ISBN to return: ");
                        String isbnreturned = sc.nextLine();
                        boolean returned = library.borrowBook(isbnreturned);
                        if (returned){
                            System.out.println("Book returned successfully.");
                        }else {
                            System.out.println("Book not found or isbn not found.");
                        }
                        break;
                    case 5:
                        System.out.println("Exiting... Thank you!");
                        flag=false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter again from 1-5.");
                }
            }
        }
    }
}
