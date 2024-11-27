package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.exception.APIException;
import com.maids.librarymanagementsystem.exception.ResourceNotFoundException;
import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.model.BorrowingRecord;
import com.maids.librarymanagementsystem.model.Patron;
import com.maids.librarymanagementsystem.payload.BorrowingRecordDTO;
import com.maids.librarymanagementsystem.repository.BookRepository;
import com.maids.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.maids.librarymanagementsystem.repository.PatronRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRepository;

    private final BookRepository bookRepository;

    private final PatronRepository patronRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public BorrowingRecordDTO borrowBook(Long bookId, Long patronId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "patronId", patronId));

        List<BorrowingRecord> borrowingsRecords = borrowingRepository.findAllByBookOrderByBorrowingRecordIdDesc(book);

        if(!borrowingsRecords.isEmpty() && borrowingsRecords.get(0).getReturnDate() == null){

            // Check if the Patron have already borrowed the book and did not return it
            if(patron == borrowingsRecords.get(0).getPatron()){
                throw new APIException("Book was already borrowed by this patron");
            }
            // Check if Another Patron have already borrowed the book and did not return it
            else {
                throw new APIException("Book was already borrowed by another patron");
            }
        }

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .build();

        BorrowingRecord savedBorrowingRecord = borrowingRepository.save(borrowingRecord);

        return modelMapper.map(savedBorrowingRecord, BorrowingRecordDTO.class);

    }

    @Transactional
    @Override
    public BorrowingRecordDTO returnBook(Long bookId, Long patronId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "patronId", patronId));

        List<BorrowingRecord> borrowingRecords = borrowingRepository.findAllByBookAndPatronOrderByBorrowingRecordIdDesc(book, patron);

        if(borrowingRecords.isEmpty()){
            throw new APIException("Patron with patronId: " + patronId + " didn't borrow Book with bookId: " + bookId);
        }

        // Get the most recent Borrowing Record
        BorrowingRecord borrowingRecord = borrowingRecords.get(0);

        // Check if the user have not already returned the Book
        if(borrowingRecord.getReturnDate() != null){
            throw new APIException("Book was already returned by patron");
        }

        borrowingRecord.setReturnDate(LocalDate.now());

        BorrowingRecord savedBorrowingRecord = borrowingRepository.save(borrowingRecord);

        return modelMapper.map(savedBorrowingRecord, BorrowingRecordDTO.class);

    }
}
