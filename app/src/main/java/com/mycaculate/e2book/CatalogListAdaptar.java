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

public class CatalogListAdaptar extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Book> arrayList;
    String[] idForNickname;
    int book_id;

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
        showCatalog.setText(book.getCatalog());
        showPublisher.setText(book.getPublisher());
        showAuthor.setText(book.getAuthor());
        book_id = book.getBook_id();

        //點擊加入書架
        btn_mywish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertWishlist insertWishlist = new InsertWishlist(context,idForNickname[0],book_id);
                insertWishlist.execute("http://renewforlive11.000webhostapp.com/test/insertwishlist.php");
                btn_mywish.setEnabled(false);
                btn_mywish.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }
}
