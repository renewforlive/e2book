package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
        String resultString="";
        URL u=null;
        int cnt;
        try
        {
            Log.d("AddWishList", "doInBackground():u=" + u);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String postString = "";
            if (this.ownerId != 0)
                postString = postString.concat("owner_id=" + String.valueOf(this.ownerId));
            else
                resultString="no ownerId";
            if (this.bookId!=0)
                postString=postString.concat((postString.length()>0?"&":"")+"book_id="+String.valueOf(this.bookId));
            else
                resultString=resultString.concat((resultString.length()>0?", ":"")+"no bookId");
            if (this.focusId != 0)
                postString = postString.concat((postString.length() > 0 ? "&" : "") + "focus_id=" + String.valueOf(this.focusId));
            Log.d("AddWishList", "postString=" + postString);
            if (resultString.length()==0)
            {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postString);
                writer.flush();
                writer.close();
                os.close();
                Log.d("AddWishList", "doInBackground():Post:" + postString);
                InputStream is = conn.getInputStream();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                cnt = is.read(b);
                while (cnt > 0) {
                    baos.write(b, 0, cnt);
                    cnt = is.read(b);
                }
                is.close();
                resultString = new String(baos.toByteArray());
                Log.d("LoadBookSearch", "response=" + resultString);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("資料寫入中...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
    }
}
