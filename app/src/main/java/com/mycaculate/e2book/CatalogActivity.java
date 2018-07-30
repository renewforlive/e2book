package com.mycaculate.e2book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class CatalogActivity extends AppCompatActivity implements View.OnClickListener {



    enum picCatalog {
        小說, 漫畫, 雜誌, 參考書, 歷史, 自傳, 童書, 藝術, 技術, 電腦;
    }
    ImageButton btn_novel,btn_comic,btn_mazagine,btn_reference,btn_history,btn_intro,btn_child,btn_art,btn_pro,btn_computer;
    Intent intent;
    int catalog_type;
    //傳member_id
    Bundle bData;
    String[] idForNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        initView();
        initButton();
        bData = getIntent().getExtras();
        if (bData!=null){
            idForNickname = bData.getStringArray("bData");
        }
        intent = new Intent();
        intent.setClass(CatalogActivity.this, CatalogDetailActivity.class);
    }
    private void initView(){
        btn_novel =findViewById(R.id.btn_novel);
        btn_comic = findViewById(R.id.btn_comic);
        btn_mazagine = findViewById(R.id.btn_mazagine);
        btn_reference = findViewById(R.id.btn_reference);
        btn_history = findViewById(R.id.btn_history);
        btn_intro = findViewById(R.id.btn_intro);
        btn_child = findViewById(R.id.btn_child);
        btn_art = findViewById(R.id.btn_art);
        btn_pro = findViewById(R.id.btn_pro);
        btn_computer = findViewById(R.id.btn_computer);
    }
    private void initButton(){
        btn_novel.setOnClickListener(this);
        btn_comic.setOnClickListener(this);
        btn_mazagine.setOnClickListener(this);
        btn_reference.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_intro.setOnClickListener(this);
        btn_child.setOnClickListener(this);
        btn_art.setOnClickListener(this);
        btn_pro.setOnClickListener(this);
        btn_computer.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_novel:
                catalog_type = 1;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_comic:
                catalog_type = 2;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_mazagine:
                catalog_type = 3;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_reference:
                catalog_type = 4;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_history:
                catalog_type = 5;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_intro:
                catalog_type = 6;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_child:
                catalog_type = 7;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_art:
                catalog_type = 8;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_pro:
                catalog_type = 9;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
            case R.id.btn_computer:
                catalog_type = 10;
                intent.putExtra("bData",idForNickname);
                intent.putExtra("catalog_type",catalog_type);
                startActivity(intent);
                break;
        }
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
