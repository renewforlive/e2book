package com.mycaculate.e2book;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import static com.mycaculate.e2book.WebConnect.URI_UPDATESHELVES;

public class SellListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;
    String member_id;

    public SellListAdaptar(Context context, ArrayList<Book> arrayList,String member_id) {
        this.context = context;
        this.arrayList = arrayList;
        this.member_id = member_id;
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
        TextView showCreateTime =v.findViewById(R.id.showCreateTime);
        ImageButton btn_search = v.findViewById(R.id.btn_search);
        ImageButton btn_nosell = v.findViewById(R.id.btn_nosell);

        final int showBook_id = book.getBook_id();
        showBookname1.setText(book.getBook_name());
        showCatalog1.setText(book.getCatalog());
        showCreateTime.setText(book.getCreate_time());
        Log.i("showBook_id",String.valueOf(showBook_id));

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_nosell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateShelves updateShelves = new UpdateShelves(context,member_id,showBook_id);
                updateShelves.execute(URI_UPDATESHELVES);
            }
        });

        return v;
    }

}
