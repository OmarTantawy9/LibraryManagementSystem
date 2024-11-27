package com.maids.librarymanagementsystem.repository;

import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.model.BorrowingRecord;
import com.maids.librarymanagementsystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    List<BorrowingRecord> findAllByBookAndPatronOrderByBorrowingRecordIdDesc(Book book, Patron patron);

    List<BorrowingRecord> findAllByBookOrderByBorrowingRecordIdDesc(Book book);
}
