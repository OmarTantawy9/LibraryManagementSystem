package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.payload.BorrowingRecordDTO;

public interface BorrowingRecordService {
    BorrowingRecordDTO borrowBook(Long bookId, Long patronId);

    BorrowingRecordDTO returnBook(Long bookId, Long patronId);
}
