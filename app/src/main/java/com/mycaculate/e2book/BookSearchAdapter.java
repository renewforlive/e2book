package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookSearchAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    List<BookSearch> bookSearchList;
    public BookSearchAdapter(Context mctx, List<BookSearch> bookSearchList) {
        this.layoutInflater = (LayoutInflater)mctx.getSystemService(mctx.LAYOUT_INFLATER_SERVICE);
        this.bookSearchList = bookSearchList;
    }

    @Override
    public int getCount() {
        return bookSearchList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookSearchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookSearchList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=layoutInflater.inflate(R.layout.booksearch_item, null);
        BookSearch bookSearch=(BookSearch) getItem(position);
        TextView txtCatalog=v.findViewById(R.id.txtCatalog);
        TextView txtBookName=v.findViewById(R.id.txtBookName);
        TextView txtAuthor=v.findViewById(R.id.txtAuthor);
        TextView txtPublisher=v.findViewById(R.id.txtPublisher);
        TextView txtPrice=v.findViewById(R.id.txtPrice);
        TextView txtNotes=v.findViewById(R.id.txtNotes);
        TextView txtOwner=v.findViewById(R.id.txtOwner);
        TextView txtShelveTime=v.findViewById(R.id.txtShelveTime);
        txtCatalog.setText(bookSearch.getCatalog());
        txtBookName.setText(bookSearch.getBookName());
        txtAuthor.setText(bookSearch.getAuthor());
        txtPublisher.setText(bookSearch.getPublisher());
        txtPrice.setText(bookSearch.getPrice());
        txtPrice.setText(bookSearch.getAuthor());
        if (bookSearch.getOwnerId()==0)
        {
            txtOwner.setText("");
            txtShelveTime.setText("");
            txtNotes.setText(bookSearch.getBookNotes());
        }
        else
        {
            txtOwner.setText(bookSearch.getOwnerName());
            txtShelveTime.setText(bookSearch.getShelveTime());
            txtNotes.setText(bookSearch.getShelveNotes());
        }
        return v;
    }
}
