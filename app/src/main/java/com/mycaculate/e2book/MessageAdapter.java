package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Message> arrayList;

    public MessageAdapter(Context context, ArrayList<Message> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (arrayList != null)
            return arrayList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList != null)
            return arrayList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (arrayList != null)
            return arrayList.indexOf(getItem(position));
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message)getItem(position);
        View v = inflater.inflate(R.layout.newsinfo_item,null);

        TextView showSender = v.findViewById(R.id.showSender);
        TextView showText = v.findViewById(R.id.showtext);
        TextView showDate = v.findViewById(R.id.showDate);

        showSender.setText(message.getSender());
        showText.setText(message.getMsg());
        showDate.setText(message.getCreate_time());


        return v;
    }
}
