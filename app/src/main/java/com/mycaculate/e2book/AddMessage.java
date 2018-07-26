package com.mycaculate.e2book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddMessage extends AppCompatActivity {
    private EditText editSender,editReceiver,editsvid,editmessage;
    private String addSender,addReceiver,addsvid,addmessage;
    private Button btn_OK;
    private CreateMessage createMessage;
    private Bundle bData;
    private String[] idForNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        bData=this.getIntent().getExtras();
        idForNickname=bData.getStringArray("bData");
        CreateMessage createMessage=new CreateMessage(this,idForNickname[0],null,null,null,null);
        initView();

    }
    private void initView(){
        editSender=findViewById(R.id.sender);
        editSender.setText(idForNickname[1]);
        editReceiver=findViewById(R.id.recevier);
        editsvid=findViewById(R.id.shelves_id);
        editmessage=findViewById(R.id.message);
        btn_OK=findViewById(R.id.btn_OK);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSender =idForNickname[0];
                addReceiver =editReceiver.getText().toString();
                addsvid =editsvid.getText().toString();
                addmessage =editmessage.getText().toString();
                Log.i(" addSender", addSender);
                createMessage =new CreateMessage(AddMessage.this,addSender,addReceiver,addsvid,addmessage,null);
                createMessage.execute(WebConnect.URI_CREATE_MESSAGE);
            }
        });

    }
}
