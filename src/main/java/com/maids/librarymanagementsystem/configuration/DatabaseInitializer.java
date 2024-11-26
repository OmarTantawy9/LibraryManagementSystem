package com.maids.librarymanagementsystem.configuration;

import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {


    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {

        List<Book> books = Arrays.asList(
                new Book(null, "Book Title 1", "Author Name 1", 1995, "9781234567801"),
                new Book(null, "Book Title 2", "Author Name 2", 2003, "9781234567812"),
                new Book(null, "Book Title 3", "Author Name 3", 2010, "9789876543203"),
                new Book(null, "Book Title 4", "Author Name 4", 1998, "9784561237904"),
                new Book(null, "Book Title 5", "Author Name 5", 2015, "9783216547705"),
                new Book(null, "Book Title 6", "Author Name 6", 1987, "9786543217806"),
                new Book(null, "Book Title 7", "Author Name 7", 2005, "9787894567107"),
                new Book(null, "Book Title 8", "Author Name 8", 2020, "9783456789108"),
                new Book(null, "Book Title 9", "Author Name 9", 1990, "9780987654309"),
                new Book(null, "Book Title 10", "Author Name 10", 2018, "9785678901200"),
                new Book(null, "Book Title 11", "Author Name 11", 2007, "9781237894501"),
                new Book(null, "Book Title 12", "Author Name 12", 2012, "9784569873202"),
                new Book(null, "Book Title 13", "Author Name 13", 1983, "9787890123403"),
                new Book(null, "Book Title 14", "Author Name 14", 1999, "9788901234504"),
                new Book(null, "Book Title 15", "Author Name 15", 2014, "9781234567105"),
                new Book(null, "Book Title 16", "Author Name 16", 1992, "9784561238706"),
                new Book(null, "Book Title 17", "Author Name 17", 2009, "9786547892107"),
                new Book(null, "Book Title 18", "Author Name 18", 2021, "9789873210908"),
                new Book(null, "Book Title 19", "Author Name 19", 1985, "9781238906709"),
                new Book(null, "Book Title 20", "Author Name 20", 2017, "9785671234100")
        );

        bookRepository.saveAll(books);

    }
}
