package com.mycaculate.e2book;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    Bundle bDate;
    String[] idForNickname;
    Spinner bookSpinner, locateSpinner;
    EditText edtSearch;
    TextView txtNotFound;
    ImageButton btnSearch;
    Button btnAddWishList;
    ListView searchListView;
    List<CodeItem> bookCatalogList, locationList;
    CodeAdapter bookCatalogAdapter, locationAdapter;
    List<BookSearch> bookSearchList;
    BookSearchAdapter bookSearchAdapter;
    Integer catalog=0, location=0;
    String keyword="";
    boolean first_time=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bDate = this.getIntent().getExtras();
        idForNickname = bDate.getStringArray("bData");
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
            LoadCode location = new LoadCode(this, "Location", true);
            locationList = location.execute(WebConnect.URI_GETCODELIST).get();
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
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword= SearchActivity.this.edtSearch.getText().toString();
                SearchActivity.this.showView();
            }
        });
    }

    private void showView()
    {
        Log.d("showView()", "Get bookSearchList");
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
            if (first_time)
                first_time=false;
            else
                txtNotFound.setText("無資料, 請重設篩選條件");
        }
        bookSearchAdapter=new BookSearchAdapter(this, idForNickname, bookSearchList);
        searchListView.setAdapter(bookSearchAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.backtomenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backtomenu:
                Intent intent = new Intent();
                intent.putExtra("bData",idForNickname);
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
