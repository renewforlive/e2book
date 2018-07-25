package com.mycaculate.e2book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.mycaculate.e2book.WebConnect.URI_LOADINGMESSAGE;

public class NewsInfoActivity extends AppCompatActivity {

    ListView messageListView;
    ArrayList<Message> msg_arraylist;
    ImageView no_message;
    //將收member_id
    Bundle bData;
    String[] idForNickname;
    String member_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsinfo);

        initView();
        bData = getIntent().getExtras();
        if (bData != null){
            idForNickname = bData.getStringArray("bData");
            member_id = idForNickname[0];
        }


        LoadingMessage loadingMessage = new LoadingMessage(this,member_id);
        try {
            msg_arraylist = loadingMessage.execute(URI_LOADINGMESSAGE).get();
            Log.i("msg_arrlist=", String.valueOf(msg_arraylist));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (msg_arraylist != null){
            no_message.setVisibility(View.INVISIBLE);
            messageListView.setVisibility(View.VISIBLE);
            MessageAdapter messageAdapter = new MessageAdapter(this,msg_arraylist);
            messageListView.setAdapter(messageAdapter);
        }
        else{
            no_message.setVisibility(View.VISIBLE);
            messageListView.setVisibility(View.INVISIBLE);
        }
    }
    private void initView(){
        messageListView = findViewById(R.id.messageListView);
        no_message = findViewById(R.id.no_message);
    }

    //menu的建置
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
