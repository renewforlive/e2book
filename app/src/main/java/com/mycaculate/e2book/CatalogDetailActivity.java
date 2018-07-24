package com.mycaculate.e2book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CatalogDetailActivity extends AppCompatActivity {
    Bundle bDate;
    int catalog_type;
    ArrayList<Book> arrayList;
    ListView catalog_listView;
    String[] idForNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_detail);


        catalog_listView = findViewById(R.id.catalog_listView);
        bDate = this.getIntent().getExtras();
        catalog_type = bDate.getInt("catalog_type");
        idForNickname = bDate.getStringArray("bData");
        change_catalog();


        LoadingCatalogTask loadingCatalogTask = new LoadingCatalogTask(this,catalog_type);
        try {
            arrayList = loadingCatalogTask.execute("http://renewforlive11.000webhostapp.com/test/downloadbook.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CatalogListAdaptar catalogListAdaptar = new CatalogListAdaptar(this,arrayList,idForNickname);
        catalog_listView.setAdapter(catalogListAdaptar);

    }
    public void change_catalog(){
        switch (catalog_type){
            case 1:
                setTitle("類型:小說");
                break;
            case 2:
                setTitle("類型:漫畫");
                break;
            case 3:
                setTitle("類型:雜誌");
                break;
            case 4:
                setTitle("類型:參考書");
                break;
            case 5:
                setTitle("類型:歷史");
                break;
            case 6:
                setTitle("類型:自傳");
                break;
            case 7:
                setTitle("類型:童書");
                break;
            case 8:
                setTitle("類型:藝術");
                break;
            case 9:
                setTitle("類型:技術");
                break;
            case 10:
                setTitle("類型:電腦");
                break;
        }
    }
}
