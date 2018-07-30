package com.mycaculate.e2book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.mycaculate.e2book.WebConnect.URI_LOADINGMEMBER;
import static com.mycaculate.e2book.WebConnect.URI_LOADINGSHELVES;
import static com.mycaculate.e2book.WebConnect.URI_LOADINGWISHLIST;

public class MyListActivity extends AppCompatActivity implements View.OnClickListener{
    TextView showUsername,showEmail,showLocation;
    ListView mylistView;
    ImageButton btn_buylist,btn_selllist;
    //bData傳送
    Bundle bData;
    String[] idForNickname;
    //會員資料，帳戶、郵件、地區
    String[] member_data;
    //兩個arraylist為買和賣
    ArrayList<Book> buyarraylist;
    ArrayList<Book> sellarraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        //初始化
        initView();
        //按鈕
        initButton();
        //bData的資料
        bData = getIntent().getExtras();
        if (bData != null){
            idForNickname = bData.getStringArray("bData");
            //取得會員資料
            LoadingMember loadingMember = new LoadingMember(this, idForNickname[0]);
            try {
                member_data = loadingMember.execute(URI_LOADINGMEMBER).get();
                showUsername.setText(member_data[0]);
                showEmail.setText(member_data[1]);
                //地區id換中文
                changelocate();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    private void initView(){
        showUsername = findViewById(R.id.showUserName);
        showEmail = findViewById(R.id.showMail);
        showLocation = findViewById(R.id.showLocataion);
        btn_buylist = findViewById(R.id.btn_buylist);
        btn_selllist = findViewById(R.id.btn_selllist);
        mylistView = findViewById(R.id.mylistView);
    }
    private void initButton(){
        btn_buylist.setOnClickListener(this);
        btn_selllist.setOnClickListener(this);
    }
    private void changelocate(){
        switch (Integer.parseInt(member_data[2])){
            case 11:
                showLocation.setText("台北市");
                break;
            case 12:
                showLocation.setText("新北市");
                break;
            case 13:
                showLocation.setText("桃園市");
                break;
            case 14:
                showLocation.setText("新竹市");
                break;
            case 15:
                showLocation.setText("苗栗縣");
                break;
            case 16:
                showLocation.setText("台中市");
                break;
            case 17:
                showLocation.setText("彰化縣");
                break;
            case 18:
                showLocation.setText("南投縣");
                break;
            case 19:
                showLocation.setText("雲林縣");
                break;
            case 20:
                showLocation.setText("嘉義縣");
                break;
            case 21:
                showLocation.setText("台南市");
                break;
            case 22:
                showLocation.setText("高雄市");
                break;
            case 23:
                showLocation.setText("屏東縣");
                break;
            case 24:
                showLocation.setText("宜蘭縣");
                break;
            case 25:
                showLocation.setText("花蓮縣");
                break;
            case 26:
                showLocation.setText("台東縣");
                break;
            case 27:
                showLocation.setText("澎湖縣");
                break;
            case 28:
                showLocation.setText("金門縣");
                break;
            case 29:
                showLocation.setText("連江縣");
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buylist:
                buylist();
                break;
            case R.id.btn_selllist:
                selllist();
                break;
        }
    }
    public void buylist(){
        LoadingWishlist loadingWishlist = new LoadingWishlist(this,idForNickname[0]);
        try {
            buyarraylist = loadingWishlist.execute(URI_LOADINGWISHLIST).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        BuyListAdaptar buyListAdaptar = new BuyListAdaptar(this,buyarraylist,idForNickname[0]);
        mylistView.setAdapter(buyListAdaptar);
    }
    public void selllist(){
        //透過會員ID將該會員上架的書籍下載
        LoadingShelves loadingShelves = new LoadingShelves(this,idForNickname[0]);
        try {
            sellarraylist = loadingShelves.execute(URI_LOADINGSHELVES).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        SellListAdaptar sellListAdaptar = new SellListAdaptar(this,sellarraylist,idForNickname[0]);
        mylistView.setAdapter(sellListAdaptar);
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
