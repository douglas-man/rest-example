package pl.finsys.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.finsys.example.domain.Book;
import pl.finsys.example.service.BookService;
import pl.finsys.example.service.exception.BookAlreadyExistsException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "book", description = "The book API")
public class BookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST, consumes = {"application/json"})
    public Book saveBook(@RequestBody @Valid final Book book) {
        LOGGER.debug("Received request to create the {}", book);
        return bookService.saveBook(book);
    }

    @Operation(
            summary = "Find all books",
            description = "Find all books",
            tags = "book"
    )
    @RequestMapping(value = "/books", method = RequestMethod.GET,
            produces={"application/json"})
    public List<Book> listBooks() {
        LOGGER.debug("Received request to list all books");
        return bookService.getList();
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET,
            produces={"application/json"})
    public Book singleBook(@PathVariable Long id) {
        LOGGER.debug("Received request to list a specific book");
        return bookService.getBook(id);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable Long id) {
        LOGGER.debug("Received request to delete a specific book");
        bookService.deleteBook(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(BookAlreadyExistsException e) {
        return e.getMessage();
    }
}
