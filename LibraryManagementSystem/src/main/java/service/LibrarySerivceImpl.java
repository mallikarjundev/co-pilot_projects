package service;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibrarySerivceImpl implements LibraryService{

    private List<Book> books;

    public LibrarySerivceImpl(){
        this.books=new ArrayList<>();
    }
    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public boolean borrowBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book!=null && book.getAvailableCopies()>0){
            book.setAvailableCopies(book.getAvailableCopies()-1);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book!=null){
            book.setAvailableCopies(book.getAvailableCopies()+1);
            return true;
        }
        return false;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
//        for (Book book: books){
//            if (book.getIsbn().equalsIgnoreCase(isbn)) return book;
//        }
//        return null;

        Optional<Book> foundBook = books.stream().filter(book->book.getIsbn().equalsIgnoreCase(isbn)).findFirst();

        return foundBook.orElse(null);
    }
}
