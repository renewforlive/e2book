package com.mycaculate.e2book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReadMessage extends AppCompatActivity {
    private Intent intent;
    private ListView readlist;
    private MessageAdapter messageAdapter;
    private ReadMessageData readMessageData;
    List<Message> message_list;
//    private String readSender,readReceiver,readsvid,readmessage;
//    private Button btn_read;
//    private Bundle bData;
//    private String[] idForNickname;
//    private String[] old_message = new String[4];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        readlist=findViewById(R.id.readList);
        readMessageData=new ReadMessageData(this);

        try {
            message_list=readMessageData.execute(WebConnect.URI_MESSAGE_CONNECT).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        messageAdapter=new MessageAdapter(this,message_list);
        readlist.setAdapter(messageAdapter);

//        initView();
    }




//    private void initView(){
//        editSender_re=findViewById(R.id.sender_re);
//        editReceiver_re=findViewById(R.id.recevier_re);
//        editsvid_re=findViewById(R.id.shelves_id_re);
//        editmessage_re=findViewById(R.id.message_re);
//        btn_read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readSender=editSender_re.getText().toString();
//                readReceiver=editReceiver_re.getText().toString();
//                readsvid=editsvid_re.getText().toString();
//                readmessage=editmessage_re.getText().toString();
//
//                old_message[1]=readSender;
//                old_message[2]=readSender;
//                old_message[3]=readSender;
//                old_message[4]=readSender;
//            }
//        });
//
//    }
}
