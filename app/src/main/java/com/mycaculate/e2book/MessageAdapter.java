package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<Message> readList;

    public MessageAdapter(Context context, List<Message> readList) {
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.readList = readList;
    }

    @Override
    public int getCount() {
        return readList.size();
    }

    @Override
    public Object getItem(int position) {
        return readList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return readList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
    }
}
