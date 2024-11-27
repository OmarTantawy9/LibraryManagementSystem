package com.maids.librarymanagementsystem.repository;

import com.maids.librarymanagementsystem.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void BookRepository_ExistsByISBN_ReturnsTrue(){

        String isbn = "978-1503278196";

        Book book = Book.builder()
                .title("Title")
                .author("Author Name")
                .publicationYear(2024)
                .isbn(isbn)
                .build();

        bookRepository.save(book);

        boolean isbnExists = bookRepository.existsByIsbn(isbn);

        Assertions.assertTrue(isbnExists);

    }


    @Test
    public void BookRepository_ExistsByISBN_ReturnsFalse(){

        String isbn = "978-1503278198";

        boolean isbnExists = bookRepository.existsByIsbn(isbn);

        Assertions.assertFalse(isbnExists);

    }


}
