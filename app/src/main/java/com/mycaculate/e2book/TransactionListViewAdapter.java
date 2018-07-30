package com.mycaculate.e2book;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Handler;

import static com.mycaculate.e2book.WebConnect.URI_TRANSACTIONSHELVES;

public class TransactionListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Receipt> arrayList;
    LayoutInflater inflater;
    String book_id;
    Boolean isClick = false;
    android.os.Handler handler;



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
        final ImageButton btn_transaction2 = view.findViewById(R.id.btn_transaction2);

        showWishbook.setText(receipt.getSender_nickname());
        btn_transaction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new android.os.Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        if (msg.what ==1){
                            btn_transaction2.setEnabled(false);
                            btn_transaction2.setVisibility(View.INVISIBLE);
                        }
                    }
                };
                TransactionShelves transactionShelves = new TransactionShelves(context,book_id,receipt.getSender_id());
                transactionShelves.execute(URI_TRANSACTIONSHELVES);
                isClick = true;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(isClick == true){
                            handler.sendEmptyMessage(1);
                        }
                    }
                });
                thread.start();
            }
        });


        return view;
    }

}
