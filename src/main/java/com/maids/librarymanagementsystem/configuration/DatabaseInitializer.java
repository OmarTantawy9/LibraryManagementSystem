package com.maids.librarymanagementsystem.configuration;

import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.model.Patron;
import com.maids.librarymanagementsystem.repository.BookRepository;
import com.maids.librarymanagementsystem.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {


    private final BookRepository bookRepository;

    private final PatronRepository patronRepository;

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


        /*******************************************************************************************************/

        List<Patron> patrons = Arrays.asList(
                new Patron(null, "Patron Name 1", "123-456-7890", "patron1@example.com"),
                new Patron(null, "Patron Name 2", "234-567-8901", "patron2@example.com"),
                new Patron(null, "Patron Name 3", "345-678-9012", "patron3@example.com"),
                new Patron(null, "Patron Name 4", "456-789-0123", "patron4@example.com"),
                new Patron(null, "Patron Name 5", "567-890-1234", "patron5@example.com"),
                new Patron(null, "Patron Name 6", "678-901-2345", "patron6@example.com"),
                new Patron(null, "Patron Name 7", "789-012-3456", "patron7@example.com"),
                new Patron(null, "Patron Name 8", "890-123-4567", "patron8@example.com"),
                new Patron(null, "Patron Name 9", "901-234-5678", "patron9@example.com"),
                new Patron(null, "Patron Name 10", "012-345-6789", "patron10@example.com"),
                new Patron(null, "Patron Name 11", "111-222-3333", "patron11@example.com"),
                new Patron(null, "Patron Name 12", "222-333-4444", "patron12@example.com"),
                new Patron(null, "Patron Name 13", "333-444-5555", "patron13@example.com"),
                new Patron(null, "Patron Name 14", "444-555-6666", "patron14@example.com"),
                new Patron(null, "Patron Name 15", "555-666-7777", "patron15@example.com"),
                new Patron(null, "Patron Name 16", "666-777-8888", "patron16@example.com"),
                new Patron(null, "Patron Name 17", "777-888-9999", "patron17@example.com"),
                new Patron(null, "Patron Name 18", "888-999-0000", "patron18@example.com"),
                new Patron(null, "Patron Name 19", "999-000-1111", "patron19@example.com"),
                new Patron(null, "Patron Name 20", "000-111-2222", "patron20@example.com")
        );

        patronRepository.saveAll(patrons);


    }
}
