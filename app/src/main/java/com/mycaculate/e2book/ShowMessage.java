package com.mycaculate.e2book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowMessage extends AppCompatActivity {
    private TextView showSender,showReceiver,showsvid,showmessage;
    Button btn_Show;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);

       initView();
    }
    private void initView(){
        intent=this.getIntent();
        String from_id = intent.getStringExtra("from_name");
        String attn_id = intent.getStringExtra("attn_id");
        String shelves_id = intent.getStringExtra("shelves_id");
        String message = intent.getStringExtra("message");

        showSender=findViewById(R.id.showsender);
        showSender.setText(from_id);
        showReceiver=findViewById(R.id.showreciver);
        showReceiver.setText(attn_id);
        showsvid=findViewById(R.id.showsheves);
        showsvid.setText(shelves_id);
        showmessage=findViewById(R.id.showmessage);
        showmessage.setText(message);

        btn_Show=findViewById(R.id.btn_Show);

    }
}
