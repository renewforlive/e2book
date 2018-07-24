package com.mycaculate.e2book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    ImageButton btn_news,btn_catalog,btn_rank,btn_search,btn_mylist,btn_upload;
    TextView showNickname;
    Bundle bData;
    String[] idForNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //元件初始化
        initView();
        //按鈕初始化
        initButton();
        intent = new Intent();
        bData = getIntent().getExtras();
        if (bData !=null){
            idForNickname = bData.getStringArray("idForNickname");
            showNickname.setText(idForNickname[1]);
        }

    }
    //找到元件
    private void initView(){
        btn_news = findViewById(R.id.btn_news);
        btn_catalog = findViewById(R.id.btn_catalog);
        btn_rank = findViewById(R.id.btn_rank);
        btn_search = findViewById(R.id.btn_search);
        btn_mylist = findViewById(R.id.btn_mylist);
        btn_upload = findViewById(R.id.btn_upload);
        showNickname = findViewById(R.id.showNickname);
    }
    //按鈕添加監聽器
    private void initButton(){
        btn_news.setOnClickListener(this);
        btn_catalog.setOnClickListener(this);
        btn_rank.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_mylist.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_news:
                //到news的Activity
                startActivity(intent.setClass(MainActivity.this,NewsInfoActivity.class));
                break;
            case R.id.btn_catalog:
                //到類型的Activity
                intent.setClass(MainActivity.this,CatalogActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_rank:
                //到排名的Activity
                startActivity(intent.setClass(MainActivity.this,RankActivity.class));
                break;
            case R.id.btn_search:
                //到搜尋的Activity
                startActivity(intent.setClass(MainActivity.this,SearchActivity.class));
                break;
            case R.id.btn_mylist:
                //到我的列表的Activity
                startActivity(intent.setClass(MainActivity.this,MyListActivity.class));
                break;
            case R.id.btn_upload:
                //到賣出書籍的Activity
                startActivity(intent.setClass(MainActivity.this,UpLoadActivity.class));
                break;
        }
    }
}
