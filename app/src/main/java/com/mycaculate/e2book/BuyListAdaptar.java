package com.mycaculate.e2book;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class BuyListAdaptar extends BaseAdapter implements View.OnClickListener{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;

    public BuyListAdaptar(Context context, ArrayList<Book> arrayList) {
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
        View v = inflater.inflate(R.layout.buylist_item, null);

        TextView showBookname = v.findViewById(R.id.showBookName);
        TextView showCatalog = v.findViewById(R.id.showCatalog);
        TextView showauthor = v.findViewById(R.id.showAuthor);
        ImageButton btn_transaction = v.findViewById(R.id.btn_transaction);
        ImageButton btn_delete = v.findViewById(R.id.btn_delete);

        showBookname.setText(book.getBook_name());
        showCatalog.setText(book.getCatalog());
        showauthor.setText(book.getAuthor());
        btn_transaction.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_transaction:
                break;
            case R.id.btn_delete:
                break;
        }
    }
}
