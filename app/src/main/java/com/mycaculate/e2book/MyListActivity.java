package com.mycaculate.e2book;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyListActivity extends AppCompatActivity implements View.OnClickListener{
    TextView showUsername,showEmail,showLocation;
    ListView mylistView;
    Bundle bData;
    ImageButton btn_buylist,btn_selllist;
    String[] idForNickname;
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
            LoadingMember loadingMember = new LoadingMember(this, idForNickname[0]);
            try {
                member_data = loadingMember.execute("http://renewforlive11.000webhostapp.com/test/loadingmember.php").get();
                showUsername.setText(member_data[0]);
                showEmail.setText(member_data[1]);
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
            buyarraylist = loadingWishlist.execute("http://renewforlive11.000webhostapp.com/test/loadingwishlist.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        BuyListAdaptar buyListAdaptar = new BuyListAdaptar(this,buyarraylist);
        mylistView.setAdapter(buyListAdaptar);
    }
    public void selllist(){
        LoadingShelves loadingShelves = new LoadingShelves(this,idForNickname[0]);
        try {
            sellarraylist = loadingShelves.execute("http://renewforlive11.000webhostapp.com/test/loadingshelves.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        SellListAdaptar sellListAdaptar = new SellListAdaptar(this,sellarraylist);
        mylistView.setAdapter(sellListAdaptar);
    }
}
