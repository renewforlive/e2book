package com.mycaculate.e2book;

import android.os.Bundle;
import android.os.LocaleList;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    Spinner bookSpinner, locateSpinner;
    EditText edtSearch;
    TextView txtNotFound;
    ImageButton btnSearch;
    ListView searchListView;
    List<CodeItem> bookCatalogList, locationList;
    CodeAdapter bookCatalogAdapter, locationAdapter;
    List<BookSearch> bookSearchList;
    Integer catalog=0, location=0;
    String keyword="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
            bookCatalogList = bookCatalog.execute(WebConnect.URI_GET_CODE_LIST).get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        bookCatalogAdapter=new CodeAdapter(this, bookCatalogList);
        bookSpinner.setAdapter(bookCatalogAdapter);
        bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catalog=bookCatalogList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.d("initView()", "Get Location");
        try
        {
            LoadCode bookCatalog = new LoadCode(this, "Location", true);
            locationList = bookCatalog.execute(WebConnect.URI_GET_CODE_LIST).get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        locationAdapter=new CodeAdapter(this, locationList);
        locateSpinner.setAdapter(locationAdapter);
        locateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location= locationList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showView()
    {
        Log.d("initView()", "Get BookCatalog");
        try
        {
            LoadBookSearch loadBookSearch = new LoadBookSearch(this, catalog, location, keyword);
            bookSearchList = loadBookSearch.execute(WebConnect.URI_BOOKSEARCH_LIST).get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (bookSearchList.size()>0)
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
        bookCatalogAdapter=new CodeAdapter(this, bookCatalogList);
        bookSpinner.setAdapter(bookCatalogAdapter);
    }

}
