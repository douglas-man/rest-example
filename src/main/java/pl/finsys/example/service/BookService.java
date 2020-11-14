package pl.finsys.example.service;

import pl.finsys.example.domain.Book;

import java.util.List;

public interface BookService {

    Book saveBook(final Book book);

    List<Book> getList();

    Book getBook(Long bookId);

    void deleteBook(final Long bookId);
}
