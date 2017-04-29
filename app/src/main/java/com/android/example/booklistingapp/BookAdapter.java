package com.android.example.booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MOHANAAD on 4/3/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, List<Book> Books) {
        super(context, 0, Books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView bookNameView = (TextView) listItem.findViewById(R.id.book_name);
        bookNameView.setText(currentBook.getBookName());

        TextView authorNameView = (TextView) listItem.findViewById(R.id.author_name);
        authorNameView.setText(currentBook.getBookAuthor());

        return listItem;
    }
}
