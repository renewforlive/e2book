package com.mycaculate.e2book;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.mycaculate.e2book.WebConnect.URI_INSERTMESSAGE;
import static com.mycaculate.e2book.WebConnect.URI_QUERYSHELVES;
import static com.mycaculate.e2book.WebConnect.URI_TRANSACTIONWISHLIST;
import static com.mycaculate.e2book.WebConnect.URI_UPDATEWISHLIST;

public class BuyListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;
    String member_id;
    //AlertDialog的使用
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    //owner_id 和shelves_id和對方的暱稱
    int owner_id;
    int shelves_id;
    String receiptNickname;

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

        ImageView showImg3 = v.findViewById(R.id.showImg3);
        TextView showBookname = v.findViewById(R.id.showBookName);
        TextView showCatalog = v.findViewById(R.id.showCatalog);
        TextView showauthor = v.findViewById(R.id.showAuthor);
        TextView showPrice = v.findViewById(R.id.showPrice);
        final ImageButton btn_sendmsg = v.findViewById(R.id.btn_sendmsg);
        final ImageButton btn_transaction = v.findViewById(R.id.btn_transaction);
        final ImageButton btn_delete = v.findViewById(R.id.btn_delete);

        if(book.getPic() != null){
            showImg3.setImageBitmap(book.getPic());
            Log.i("getPic=",book.getPic().toString());
        }

        final int book_id = book.getBook_id();
        showBookname.setText(book.getBook_name());

        int catalog_id = Integer.parseInt(book.getCatalog());
        String catalog_name = change_catalog(catalog_id);
        showCatalog.setText(catalog_name);

        showauthor.setText(book.getAuthor());
        showPrice.setText(book.getPrice());


        btn_sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater1 = LayoutInflater.from(context);
                final View view = inflater1.inflate(R.layout.alertdialog_item,null);
                QueryShelves queryShelves = new QueryShelves(context, String.valueOf(book_id));
                try {
                    String[] ownerAndShelves_idAndNickname = queryShelves.execute(URI_QUERYSHELVES).get();
                    owner_id = Integer.parseInt(ownerAndShelves_idAndNickname[0]);
                    shelves_id = Integer.parseInt(ownerAndShelves_idAndNickname[1]);
                    receiptNickname = ownerAndShelves_idAndNickname[2];
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                TextView showReceipt = view.findViewById(R.id.showReceipt);
                showReceipt.setText(receiptNickname);

                builder = new AlertDialog.Builder(context)
                        .setTitle("傳送訊息")
                        .setView(view)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText edtMessage = view.findViewById(R.id.edtMessage);
                                String message = edtMessage.getText().toString();
                                Toast.makeText(context,"你傳送了一個訊息",Toast.LENGTH_LONG).show();

                                Log.i("book_id=", String.valueOf(book_id));
                                InsertMessage insertMessage = new InsertMessage(context,member_id,String.valueOf(owner_id),message,String.valueOf(shelves_id));
                                insertMessage.execute(URI_INSERTMESSAGE);
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionWishlist transactionWishlist = new TransactionWishlist(context, String.valueOf(book_id));
                transactionWishlist.execute(URI_TRANSACTIONWISHLIST);
                btn_sendmsg.setVisibility(View.INVISIBLE);
                btn_transaction.setVisibility(View.INVISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateWishList updateWishList = new UpdateWishList(context, member_id,book_id);
                updateWishList.execute(URI_UPDATEWISHLIST);
                btn_sendmsg.setVisibility(View.INVISIBLE);
                btn_transaction.setVisibility(View.INVISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
            }
        });


        return v;
    }
    public String change_catalog(int catalog_id){
        String catalog_name = "";
        switch (catalog_id){
            case 1:
                catalog_name = "小說";
                break;
            case 2:
                catalog_name = "漫畫";
                break;
            case 3:
                catalog_name = "雜誌";
                break;
            case 4:
                catalog_name = "參考書";
                break;
            case 5:
                catalog_name = "歷史";
                break;
            case 6:
                catalog_name = "自傳";
                break;
            case 7:
                catalog_name = "童書";
                break;
            case 8:
                catalog_name = "藝術";
                break;
            case 9:
                catalog_name = "技術";
                break;
            case 10:
                catalog_name = "電腦";
                break;
        }
        return catalog_name;
    }
}
