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

public class BuyListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;
    String member_id;

    public BuyListAdaptar(Context context, ArrayList<Book> arrayList, String member_id) {
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
        View v = inflater.inflate(R.layout.buylist_item, null);

        TextView showBookname = v.findViewById(R.id.showBookName);
        TextView showCatalog = v.findViewById(R.id.showCatalog);
        TextView showauthor = v.findViewById(R.id.showAuthor);
        ImageButton btn_transaction = v.findViewById(R.id.btn_transaction);
        ImageButton btn_delete = v.findViewById(R.id.btn_delete);

        final int book_id = book.getBook_id();
        showBookname.setText(book.getBook_name());
        showCatalog.setText(book.getCatalog());
        showauthor.setText(book.getAuthor());

        btn_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateWishList updateWishList = new UpdateWishList(context, member_id,book_id);
                updateWishList.execute("http://renewforlive11.000webhostapp.com/test/updatewishlist.php");
            }
        });


        return v;
    }
}
