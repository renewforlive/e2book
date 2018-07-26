package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BookSearchAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    String ownerId;
    List<BookSearch> bookSearchList;
    public BookSearchAdapter(Context mctx, String[] idForNickname, List<BookSearch> bookSearchList) {
        this.layoutInflater = (LayoutInflater)mctx.getSystemService(mctx.LAYOUT_INFLATER_SERVICE);
        this.ownerId=ownerId;
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
        final int fBookId=bookSearch.getBookId();
        final int fShelveId=bookSearch.getShelveId();
        final int fOwnerId=bookSearch.getOwnerId();
        TextView txtCatalog=v.findViewById(R.id.txtCatalog);
        TextView txtBookName=v.findViewById(R.id.txtBookName);
        TextView txtAuthor=v.findViewById(R.id.txtAuthor);
        TextView txtPublisher=v.findViewById(R.id.txtPublisher);
        TextView txtPrice=v.findViewById(R.id.txtPrice);
        TextView txtNotes=v.findViewById(R.id.txtNotes);
        TextView txtOwner=v.findViewById(R.id.txtOwner);
        TextView txtArea=v.findViewById(R.id.txtArea);
        TextView txtShelveTime=v.findViewById(R.id.txtShelveTime);
        Button btnAddWishList=v.findViewById(R.id.btnAddWishList);
        txtCatalog.setText(bookSearch.getCatalog());
        txtBookName.setText(bookSearch.getBookName());
        txtAuthor.setText(bookSearch.getAuthor());
        txtPublisher.setText(bookSearch.getPublisher());
        if (bookSearch.getPrice()==0)
            txtPrice.setText("");
        else
            txtPrice.setText(String.valueOf(bookSearch.getPrice()));
        if (bookSearch.getOwnerId()==0)
        {
            txtOwner.setText("");
            txtArea.setText("");
            txtShelveTime.setText("");
            txtNotes.setText(bookSearch.getBookNotes());
            btnAddWishList.setEnabled(false);
            btnAddWishList.setText("無人提供");
        }
        else
        {
            txtOwner.setText(bookSearch.getOwnerName());
            txtArea.setText(bookSearch.getOwnerArea());
            txtShelveTime.setText(bookSearch.getShelveTime());
            txtNotes.setText(bookSearch.getShelveNotes());
            btnAddWishList.setEnabled(true);
        }
        btnAddWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWishList addWishList=new AddWishList(v.getContext(), fOwnerId, fBookId, fShelveId);
            }
        });
        return v;
    }
}
