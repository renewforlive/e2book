package com.mycaculate.e2book;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.mycaculate.e2book.WebConnect.URI_INSERTMESSAGE;
import static com.mycaculate.e2book.WebConnect.URI_QUERYSHELVES;
import static com.mycaculate.e2book.WebConnect.URI_UPDATEWISHLIST;

public class BuyListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;
    String member_id;
    //AlertDialog的使用
    AlertDialog alertDialog;
    AlertDialog.Builder builder;

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
                final LayoutInflater inflater1 = LayoutInflater.from(context);
                final View view = inflater1.inflate(R.layout.alertdialog_item,null);
                builder = new AlertDialog.Builder(context)
                        .setTitle("傳送訊息")
                        .setView(view)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText edtMessage = view.findViewById(R.id.edtMessage);
                                String message = edtMessage.getText().toString();
                                Toast.makeText(context,"你傳送了一個msg:" + message,Toast.LENGTH_LONG).show();

                                Log.i("book_id=", String.valueOf(book_id));
                                QueryShelves queryShelves = new QueryShelves(context, String.valueOf(book_id));
                                try {
                                    int[] ownerAndShelves_id = queryShelves.execute(URI_QUERYSHELVES).get();
                                    InsertMessage insertMessage = new InsertMessage(context,member_id,String.valueOf(ownerAndShelves_id[0]),message,String.valueOf(ownerAndShelves_id[1]));
                                    insertMessage.execute(URI_INSERTMESSAGE);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateWishList updateWishList = new UpdateWishList(context, member_id,book_id);
                updateWishList.execute(URI_UPDATEWISHLIST);
            }
        });


        return v;
    }
}
