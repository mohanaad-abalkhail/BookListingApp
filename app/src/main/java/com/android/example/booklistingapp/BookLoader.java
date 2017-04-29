package com.android.example.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by MOHANAAD on 4/3/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {


    private String mUrl;


    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Book> Books = com.android.example.booklistingapp.QueryUtils.fetchBookData(mUrl);
        return Books;
    }
}
