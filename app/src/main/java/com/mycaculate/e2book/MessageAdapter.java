package com.mycaculate.e2book;

<<<<<<< HEAD
import android.content.Context;
=======
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
>>>>>>> master
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
<<<<<<< HEAD
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<Message> readList;

    public MessageAdapter(Context context, List<Message> readList) {
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.readList = readList;
=======
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.mycaculate.e2book.WebConnect.URI_INSERTMESSAGE;
import static com.mycaculate.e2book.WebConnect.URI_UPDATEMESSAGE;

public class MessageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Message> arrayList;
    //AlertDialog的使用
    AlertDialog alertDialog;
    AlertDialog.Builder builder;


    public MessageAdapter(Context context, ArrayList<Message> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
>>>>>>> master
    }

    @Override
    public int getCount() {
<<<<<<< HEAD
        return readList.size();
=======
        if (arrayList != null)
            return arrayList.size();
        return 0;
>>>>>>> master
    }

    @Override
    public Object getItem(int position) {
<<<<<<< HEAD
        return readList.get(position);
=======
        if (arrayList != null)
            return arrayList.get(position);
        return null;
>>>>>>> master
    }

    @Override
    public long getItemId(int position) {
<<<<<<< HEAD
        return readList.indexOf(getItem(position));
=======
        if (arrayList != null)
            return arrayList.indexOf(getItem(position));
        return 0;
>>>>>>> master
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
<<<<<<< HEAD
        Message data=readList.get(position);
        convertView=myInflater.inflate(R.layout.read_message_view,null);
        TextView sender_re=convertView.findViewById(R.id.sender_re);
        sender_re.setText(data.getFrom_id());
        TextView receiver_re=convertView.findViewById(R.id.receiver_re);
        receiver_re.setText(data.getAttn_id());
        TextView shelves_id_re=convertView.findViewById(R.id.shelves_id_re);
        shelves_id_re.setText(data.getShelves_id());
        TextView message_re=convertView.findViewById(R.id.message_re);
        message_re.setText(data.getMessage());
        return convertView;
=======
        final Message message = (Message)getItem(position);
        View v = inflater.inflate(R.layout.newsinfo_item,null);

        TextView showSender = v.findViewById(R.id.showSender);
        TextView showText = v.findViewById(R.id.showtext);
        TextView showDate = v.findViewById(R.id.showDate);
        ImageButton btn_reply = v.findViewById(R.id.btn_reply);
        ImageButton btn_deletemsg = v.findViewById(R.id.btn_deletemsg);

        showSender.setText(message.getSenderNickname());
        showText.setText(message.getMsg());
        showDate.setText(message.getCreate_time());

        //取得寄件人，收件人，和shelves_id
        final String sender = message.getSender();
        final String receipient = message.getRecipient();
        final int shelves_id = message.getShelves_id();
        final int msg_id = message.getMsg_id();

        btn_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater1 = LayoutInflater.from(context);
                final View view = inflater1.inflate(R.layout.alertdialog_item,null);

                TextView showReceipt = view.findViewById(R.id.showReceipt);
                showReceipt.setText(message.getSenderNickname());

                builder = new AlertDialog.Builder(context)
                        .setTitle("傳送訊息")
                        .setView(view)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText edtMessage = view.findViewById(R.id.edtMessage);
                                String message = edtMessage.getText().toString();

                                Toast.makeText(context,"你傳送了一個訊息",Toast.LENGTH_LONG).show();

                                InsertMessage insertMessage = new InsertMessage(context, receipient,sender,message,String.valueOf(shelves_id));
                                insertMessage.execute(URI_INSERTMESSAGE);
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_deletemsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMessage updateMessage = new UpdateMessage(context,msg_id);
                updateMessage.execute(URI_UPDATEMESSAGE);
            }
        });

        return v;
>>>>>>> master
    }
}
