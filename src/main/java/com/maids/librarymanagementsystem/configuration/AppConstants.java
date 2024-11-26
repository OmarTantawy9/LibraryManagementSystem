package com.maids.librarymanagementsystem.configuration;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "12";
    public static final String SORT_BOOKS_BY = "bookId";
    public static final String SORT_PATRONS_BY = "patronId";
    public static final String SORT_ORDER = "asc";
    public static final String BOOK_VALID_ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    public static final String PHONE_NUMBER_REGEX = "^\\+?(\\d{1,3})?[-.\\s]?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$";

}
