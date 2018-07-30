package com.mycaculate.e2book;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.mycaculate.e2book.WebConnect.URI_IMAGES;
import static com.mycaculate.e2book.WebConnect.URI_RANKLOADINGBOOK;

public class RankActivity extends AppCompatActivity {
    //取得bData資料
    Bundle bData;
    String[] idForNickname;
    //傳值，搜尋Shevles的成交時間，找到Book的Book_id

    //元件
    ImageView showpic0,showpic1,showpic2,showpic3,showpic4,showpic5,showpic6,showpic7;
    //存取圖片
    ArrayList<Bitmap> imgarraylist;
    Bitmap bitmap[]={null,null,null,null,null,null,null,null};
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        bData = getIntent().getExtras();
        if (bData !=null){
            idForNickname = bData.getStringArray("bData");
        }
        initView();
        imgarraylist = new ArrayList<>();
        RankLoadingbook rankLoadingbook = new RankLoadingbook(this);
        try {
            imgarraylist = rankLoadingbook.execute(URI_RANKLOADINGBOOK).get();
            Log.i("imgarraylist",String.valueOf(imgarraylist));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (int i=0; i<imgarraylist.size(); i++){
            bitmap[i] = imgarraylist.get(i);
        }

        showpic0.setImageBitmap(bitmap[0]);
        showpic1.setImageBitmap(bitmap[1]);
        showpic2.setImageBitmap(bitmap[2]);
        showpic3.setImageBitmap(bitmap[3]);
        showpic4.setImageBitmap(bitmap[4]);
        showpic5.setImageBitmap(bitmap[5]);
        showpic6.setImageBitmap(bitmap[6]);
        showpic7.setImageBitmap(bitmap[7]);
    }

    public void initView(){
        showpic0 = findViewById(R.id.showpic0);
        showpic1 = findViewById(R.id.showpic1);
        showpic2 = findViewById(R.id.showpic2);
        showpic3 = findViewById(R.id.showpic3);
        showpic4 = findViewById(R.id.showpic4);
        showpic5 = findViewById(R.id.showpic5);
        showpic6 = findViewById(R.id.showpic6);
        showpic7 = findViewById(R.id.showpic7);
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
