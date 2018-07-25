package com.mycaculate.e2book;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
