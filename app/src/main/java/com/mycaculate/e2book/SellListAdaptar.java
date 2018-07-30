package com.mycaculate.e2book;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.mycaculate.e2book.WebConnect.URI_QUERYWISHLIST;
import static com.mycaculate.e2book.WebConnect.URI_TRANSACTIONSHELVES;
import static com.mycaculate.e2book.WebConnect.URI_UPDATESHELVES;

public class SellListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;
    String member_id;
    //加入RadioButton 的 dialog
    ArrayList<Receipt> receiptList;
    Receipt receipt;
    //建一個dialog
    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    public SellListAdaptar(Context context, ArrayList<Book> arrayList,String member_id) {
        this.context = context;
        this.arrayList = arrayList;
        this.member_id = member_id;
        receiptList = new ArrayList<Receipt>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Book book = (Book)getItem(position);
        View v = inflater.inflate(R.layout.selllist_item, null);

        ImageView showImg2 = v.findViewById(R.id.showImg2);
        TextView showBookname1 = v.findViewById(R.id.showBookName1);
        TextView showCatalog1 = v.findViewById(R.id.showCatalog1);
        TextView showCreateTime =v.findViewById(R.id.showCreateTime);
        final ImageButton btn_search = v.findViewById(R.id.btn_search);
        final ImageButton btn_nosell = v.findViewById(R.id.btn_nosell);


        if(book.getPic() != null){
            showImg2.setImageBitmap(book.getPic());
            Log.i("getPic=",book.getPic().toString());
        }

        final int showBook_id = book.getBook_id();
        showBookname1.setText(book.getBook_name());

        int catalog_id = Integer.parseInt(book.getCatalog());
        String catalog_name = change_catalog(catalog_id);
        showCatalog1.setText(catalog_name);

        showCreateTime.setText(book.getCreate_time());
        Log.i("showBook_id",String.valueOf(showBook_id));

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryWishlist queryWishlist = new QueryWishlist(context,member_id,showBook_id);
                try {
                    receiptList = queryWishlist.execute(URI_QUERYWISHLIST).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
//
//                Receipt receipt = (Receipt)getItem(position);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.wishlist_listview_item,null);

                ListView listView = view.findViewById(R.id.transaction_listview);
                Log.i("receiptList=",String.valueOf(receiptList.size()));
                TransactionListViewAdapter transactionListViewAdapter = new TransactionListViewAdapter(context,receiptList,String.valueOf(showBook_id));
                listView.setAdapter(transactionListViewAdapter);

                builder = new AlertDialog.Builder(context);
                builder.setTitle("對這本書感興趣的人")
                        .setView(view)
                        .setPositiveButton("離開", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_nosell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateShelves updateShelves = new UpdateShelves(context,member_id,showBook_id);
                updateShelves.execute(URI_UPDATESHELVES);
                btn_search.setVisibility(View.INVISIBLE);
                btn_nosell.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }
    public void transactionToShelves(String showbook_id){
        TransactionShelves transactionShelves = new TransactionShelves(context,showbook_id,receipt.getSender_id());
        transactionShelves.execute(URI_TRANSACTIONSHELVES);
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
