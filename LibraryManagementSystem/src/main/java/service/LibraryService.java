package service;

import model.Book;

import java.util.List;

public interface LibraryService {
    void addBook(Book book);

    List<Book> getAllBooks();

    boolean borrowBook(String isbn);

    boolean returnBook(String isbn);

    Book findBookByIsbn(String isbn);
}
