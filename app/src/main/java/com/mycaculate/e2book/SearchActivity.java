package com.mycaculate.e2book;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    Bundle bData;
    String[] idForNickname;
    Spinner bookSpinner, locateSpinner;
    EditText edtSearch;
    TextView txtNotFound;
    ImageButton btnSearch;
    Button btnAddWishList;
    ListView searchListView;
    List<CodeItem> bookCatalogList, locationList;
    CodeAdapter bookCatalogAdapter, locationAdapter;
    List<Book> bookList=null;
    BookSearchAdapter bookSearchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bData = this.getIntent().getExtras();
        if (bData !=null)
        {
            idForNickname = bData.getStringArray("bData");
        }
        Log.d("SearchActivity", "onCreate():Begin");
        initView();
        Log.d("SearchActivity", "onCreate():Pass initView()");
        showView();
        Log.d("SearchActivity", "onCreate():End");
    }

    private void initView()
    {
        Log.d("initView()", "Begin");
        bookSpinner=findViewById(R.id.bookSpinner);
        locateSpinner=findViewById(R.id.locateSpinner);
        edtSearch=findViewById(R.id.edtSearch);
        txtNotFound=findViewById(R.id.txtNotFound);
        btnSearch=findViewById(R.id.btnSearch);
        searchListView=findViewById(R.id.searchListView);
        Log.d("initView()", "Get BookCatalog");
        try
        {
            LoadCode bookCatalog = new LoadCode(this, "BookCatalog", true);
            bookCatalogList = bookCatalog.execute(WebConnect.URI_GETCODELIST).get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        bookCatalogAdapter=new CodeAdapter(this, bookCatalogList);
        bookSpinner.setAdapter(bookCatalogAdapter);

        Log.d("initView()", "Get Location");
        try
        {
            LoadCode bookCatalog = new LoadCode(this, "Location", true);
            locationList = bookCatalog.execute(WebConnect.URI_GETCODELIST).get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        locationAdapter=new CodeAdapter(this, locationList);
        locateSpinner.setAdapter(locationAdapter);
    }

    private void showView()
    {
        bookList=new ArrayList<Book>();
        if (bookList.size()>0)
        {
            Log.d("showView()", "Pass4");
            txtNotFound.setVisibility(View.INVISIBLE);
            searchListView.setVisibility(View.VISIBLE);
        }
        else
        {
            Log.d("showView()", "Pass5");
            txtNotFound.setVisibility(View.VISIBLE);
            searchListView.setVisibility(View.INVISIBLE);
        }
        bookSearchAdapter=new BookSearchAdapter(this, idForNickname, bookList);
        bookSpinner.setAdapter(bookSearchAdapter);
    }

}
