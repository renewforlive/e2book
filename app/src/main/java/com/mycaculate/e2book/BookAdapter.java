package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class BookAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    List<Book> bookList;
    public BookAdapter(Context mctx, List<Book> bookList) {
        this.layoutInflater = (LayoutInflater)mctx.getSystemService(mctx.LAYOUT_INFLATER_SERVICE);
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
