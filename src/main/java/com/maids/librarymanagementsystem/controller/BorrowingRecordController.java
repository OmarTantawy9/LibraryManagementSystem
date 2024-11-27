package com.maids.librarymanagementsystem.controller;

import com.maids.librarymanagementsystem.payload.BorrowingRecordDTO;
import com.maids.librarymanagementsystem.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingService;

    // Allow a patron to borrow a book
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDTO borrowingDTO = borrowingService.borrowBook(bookId, patronId);
        return new ResponseEntity<>(borrowingDTO, HttpStatus.OK);

    }


    // Record the return of a borrowed book by a patron
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDTO borrowingDTO = borrowingService.returnBook(bookId, patronId);
        return new ResponseEntity<>(borrowingDTO, HttpStatus.OK);
    }


}
