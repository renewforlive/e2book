package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import static com.mycaculate.e2book.WebConnect.URI_TRANSACTIONSHELVES;

public class TransactionListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Receipt> arrayList;
    LayoutInflater inflater;
    String book_id;


    public TransactionListViewAdapter(Context context, ArrayList<Receipt> arrayList, String book_id) {
        this.context = context;
        this.arrayList = arrayList;
        this.book_id = book_id;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Receipt receipt = (Receipt) getItem(position);
        View view = inflater.inflate(R.layout.dialog_transaction_item,null);

        TextView showWishbook = view.findViewById(R.id.showWishBook);
        ImageButton btn_transaction2 = view.findViewById(R.id.btn_transaction2);

        showWishbook.setText(receipt.getSender_nickname());
        btn_transaction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionShelves transactionShelves = new TransactionShelves(context,book_id,receipt.getSender_id());
                transactionShelves.execute(URI_TRANSACTIONSHELVES);
            }
        });

        return view;
    }
}
