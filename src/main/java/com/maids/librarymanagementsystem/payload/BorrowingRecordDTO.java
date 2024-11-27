package com.maids.librarymanagementsystem.payload;

import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.model.Patron;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowingRecordDTO {
    private Long borrowingRecordId;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    Patron patron;
    Book book;
}
