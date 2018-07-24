package com.mycaculate.e2book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CodeAdapter extends BaseAdapter{
    private LayoutInflater myLayoutInflater;
    private List<CodeItem> codeItemList;

    public CodeAdapter(Context mctx, List<CodeItem> codeItemList) {
        this.myLayoutInflater = (LayoutInflater)mctx.getSystemService(mctx.LAYOUT_INFLATER_SERVICE);
        this.codeItemList = codeItemList;
    }

    @Override
    public int getCount() {
        return codeItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return codeItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return codeItemList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=myLayoutInflater.inflate(R.layout.codeitem_spinner, null);
        CodeItem codeItem=(CodeItem)getItem(position);
        TextView txtDescription=v.findViewById(R.id.txtDescription);
        txtDescription.setText(codeItem.getDescription());
        return v;
    }
}
