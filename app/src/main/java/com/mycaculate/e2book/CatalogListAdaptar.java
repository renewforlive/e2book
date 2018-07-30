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

import static com.mycaculate.e2book.WebConnect.URI_INSERTWISHLIST;

public class CatalogListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;
    String[] idForNickname;

    public CatalogListAdaptar(Context context, ArrayList<Book> arrayList, String[] idForNickname) {
        this.context = context;
        this.arrayList = arrayList;
        this.idForNickname = idForNickname;
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
        View v = inflater.inflate(R.layout.catalog_detail_item, null);

        ImageView showImg = v.findViewById(R.id.showImg);
        TextView showBookName = v.findViewById(R.id.showBookName);
        TextView showCatalog = v.findViewById(R.id.ShowCatalog);
        TextView showPublisher = v.findViewById(R.id.showPublisher);
        TextView showAuthor = v.findViewById(R.id.showAuthor);
        final ImageButton btn_mywish = v.findViewById(R.id.btn_mywish);

        if(book.getPic() != null){
            showImg.setImageBitmap(book.getPic());
            Log.i("getPic=",book.getPic().toString());
        }

        showBookName.setText(book.getBook_name());
        int catalog_id = Integer.parseInt(book.getCatalog());
        String catalog_name = change_catalog(catalog_id);
        showCatalog.setText(catalog_name);
        showPublisher.setText(book.getPublisher());
        showAuthor.setText(book.getAuthor());
        final int book_id = book.getBook_id();

        //點擊加入書架
        btn_mywish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertWishlist insertWishlist = new InsertWishlist(context,idForNickname[0],book_id);
                insertWishlist.execute(URI_INSERTWISHLIST);
                btn_mywish.setEnabled(false);
                btn_mywish.setVisibility(View.INVISIBLE);
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
