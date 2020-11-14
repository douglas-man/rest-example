package pl.finsys.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finsys.example.domain.Book;
import pl.finsys.example.repository.BookRepository;
import pl.finsys.example.service.exception.BookAlreadyExistsException;


import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(final BookRepository respository) {
        this.repository = respository;
    }

    @Override
    @Transactional
    public Book saveBook(final Book book) {
        LOGGER.debug("Creating {}", book);
        Optional<Book> existing = repository.findById(book.getId());
        if(existing.isPresent()) {
            throw new BookAlreadyExistsException(
                    String.format("There already exists a book with id=%s", book.getId()));
        }
        return repository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getList() {
        LOGGER.debug("Retrieving the list of all users");
        return repository.findAll();
    }

    @Override
    public Book getBook(Long bookId) {
        return repository.findById(bookId).get();
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        LOGGER.debug("deleting {}", bookId);

        repository.delete(repository.findById(bookId).get());
    }
}
