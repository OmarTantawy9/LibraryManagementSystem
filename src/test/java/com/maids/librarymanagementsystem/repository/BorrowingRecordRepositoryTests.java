package com.maids.librarymanagementsystem.repository;

import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.model.BorrowingRecord;
import com.maids.librarymanagementsystem.model.Patron;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BorrowingRecordRepositoryTests {

    @Autowired
    private BorrowingRecordRepository borrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;


    @Test
    public void FindAllByBookAndPatronOrderByBorrowingRecordIdDesc_ReturnsNonEmptyList() {

        Book book = Book.builder()
                .title("Title")
                .author("AuthorName")
                .publicationYear(2024)
                .isbn("978-1503278196")
                .build();


        Patron patron = Patron.builder()
                .patronName("PatronName")
                .patronEmail("patron@email.com")
                .patronPhone("+1 843-749-3820")
                .build();

        List<BorrowingRecord> borrowings = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            borrowings.add(
                    BorrowingRecord.builder()
                            .book(book)
                            .patron(patron)
                            .borrowingDate(LocalDate.now().plusDays(i * 5))
                            .returnDate(LocalDate.now().plusDays((i * 5) + 5))
                            .build()
            );

        }

        borrowingRepository.saveAll(borrowings);

        List<BorrowingRecord> borrowingList = borrowingRepository.findAllByBookAndPatronOrderByBorrowingRecordIdDesc(book, patron);


        Assertions.assertThat(borrowingList).hasSize(5);

        Collections.reverse(borrowings);
        Assertions.assertThat(borrowingList.toArray()).containsExactly(borrowings.toArray());

        Assertions.assertThat(borrowingList).isSortedAccordingTo(Comparator.comparing(BorrowingRecord::getBorrowingRecordId).reversed());

    }


    @Test
    public void FindAllByBookAndPatronOrderByBorrowingRecordIdDesc_ReturnsEmptyList() {

        Book book = Book.builder()
                .title("Title")
                .author("AuthorName")
                .publicationYear(2024)
                .isbn("978-1503278196")
                .build();


        Patron patron = Patron.builder()
                .patronName("PatronName")
                .patronEmail("patron@email.com")
                .patronPhone("+1 843-749-3820")
                .build();

        bookRepository.save(book);
        patronRepository.save(patron);

        List<BorrowingRecord> borrowingList = borrowingRepository.findAllByBookAndPatronOrderByBorrowingRecordIdDesc(book, patron);

        Assertions.assertThat(borrowingList).isEmpty();

    }

}
