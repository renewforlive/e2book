package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CatalogListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;

    public CatalogListAdaptar(Context context, ArrayList<Book> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (arrayList != null)
            return arrayList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList != null)
            return arrayList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (arrayList != null)
            return arrayList.indexOf(getItem(position));
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = (Book)getItem(position);
        View v = inflater.inflate(R.layout.catalog_detail_item, null);

        ImageView showImg = v.findViewById(R.id.showImg);
        TextView showBookName = v.findViewById(R.id.showBookName);
        TextView showCatalog = v.findViewById(R.id.ShowCatalog);
        TextView showPublisher = v.findViewById(R.id.showPublisher);
        TextView showAuthor = v.findViewById(R.id.showAuthor);

        showBookName.setText(book.getBook_name());
        showCatalog.setText(book.getCatalog());
        showPublisher.setText(book.getPublisher());
        showAuthor.setText(book.getAuthor());

        return v;
    }
}
