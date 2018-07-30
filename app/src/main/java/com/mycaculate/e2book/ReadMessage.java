package com.mycaculate.e2book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReadMessage extends AppCompatActivity {
    private TextView showSender,showReceiver,showsvid,showmessage;
    String show1,show2,show3,show4;
    private Intent intent;
    private ListView readlist;
    private MessageAdapter messageAdapter;
    private ReadMessageData readMessageData,readMessageData2;
    List<Message> message_list;
    private Bundle bData;
    private String[] idForNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        readlist=findViewById(R.id.readList);
        readMessageData=new ReadMessageData(this);
//        bData=this.getIntent().getExtras();
//        idForNickname=bData.getStringArray("bData");
//        CreateMessage createMessage=new CreateMessage(this,idForNickname[0],idForNickname[1],idForNickname[2],idForNickname[3],idForNickname[4]);

//        showSender=findViewById(R.id.showsender);
//        showSender.setText(idForNickname[1]);
//        showReceiver=findViewById(R.id.showreciver);
//        showReceiver.setText(idForNickname[2]);
//        showsvid=findViewById(R.id.showsheves);
//        showsvid.setText(idForNickname[3]);
//        showmessage=findViewById(R.id.showmessage);
//        showmessage.setText(idForNickname[4]);

        try {
            message_list=readMessageData.execute(WebConnect.URI_MESSAGE_CONNECT).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        messageAdapter=new MessageAdapter(this,message_list);
        readlist.setAdapter(messageAdapter);


        readlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent =new Intent();
                intent.putExtra("from_name", message_list.get(position).getFrom_id());
                intent.putExtra("attn_id",message_list.get(position).getAttn_id());
                intent.putExtra("shelves_id",message_list.get(position).getShelves_id());
                intent.putExtra("message",message_list.get(position).getMessage());



                intent.setClass(ReadMessage.this,ShowMessage.class);
                startActivity(intent);
            }
        });

    }

}
