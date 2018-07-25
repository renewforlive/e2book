package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;

public class AddWishList extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;
    private Context mctx;
    private int id, ownerId, bookId, focusId;
    private String createTime, endTime;

    public AddWishList(Context mctx, int ownerId, int bookId, int focusId) {
        this.mctx = mctx;
        this.ownerId = ownerId;
        this.bookId = bookId;
        this.focusId = focusId;
        dialog=new ProgressDialog(this.mctx);
    }

    @Override
    protected String doInBackground(String... strings) {
        String resultString;
        URL u=null;
        int cnt;
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
