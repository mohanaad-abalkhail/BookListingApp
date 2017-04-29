package com.android.example.booklistingapp;

/**
 * Created by MOHANAAD on 4/3/17.
 */

public class Book {

    private String mBookName;

    private String mBookAuthor;

    private String mBookUrl;

    public Book(String bookName, String bookAuthor, String bookUrl) {

        mBookName = bookName;
        mBookAuthor = bookAuthor;
        mBookUrl = bookUrl;
    }

    public String getBookName() {
        return mBookName;
    }

    public String getBookAuthor() {
        return mBookAuthor;
    }

    public String getBookUrl() {
        return mBookUrl;
    }
}
