package com.android.example.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private TextView mEmptyStateTextView;

    private EditText mSearchField;

    private Button mSearchButton;

    private String mSearchWord;

    private static final int BOOK_LOADER_ID = 1;

    private BookAdapter mAdapter;

    private LoaderManager mLoaderManager;

    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchButton = (Button) findViewById(R.id.search_button);

        ListView bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Book currentBook = mAdapter.getItem(position);

                Uri bookUri = Uri.parse(currentBook.getBookUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mLoaderManager = getLoaderManager();
            mLoaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    mLoaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {

                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        mSearchWord = mSearchField.getText().toString();
        String url = GOOGLE_BOOKS_REQUEST_URL;
        url = url + mSearchWord;

        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_books_found);

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        mAdapter.clear();
    }

}
