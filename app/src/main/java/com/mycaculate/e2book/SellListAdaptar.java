package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SellListAdaptar extends BaseAdapter implements View.OnClickListener{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;

    public SellListAdaptar(Context context, ArrayList<Book> arrayList) {
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
        View v = inflater.inflate(R.layout.selllist_item, null);

        TextView showBookname1 = v.findViewById(R.id.showBookName1);
        TextView showCatalog1 = v.findViewById(R.id.showCatalog1);
        ImageButton btn_search = v.findViewById(R.id.btn_search);
        ImageButton btn_nosell = v.findViewById(R.id.btn_nosell);

        showBookname1.setText(book.getBook_name());
        showCatalog1.setText(book.getCatalog());

        btn_search.setOnClickListener(this);
        btn_nosell.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                break;
            case R.id.btn_nosell:
                break;
        }
    }
}
