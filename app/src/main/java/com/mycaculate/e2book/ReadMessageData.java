package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ReadMessageData extends AsyncTask<String, Void, List<Message>> {
    Context context;
    private  ProgressDialog dialog;

    public ReadMessageData(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("資料下載中...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(List<Message> messages) {
        super.onPostExecute(messages);
        dialog.dismiss();
    }

    @Override
    protected List<Message> doInBackground(String... strings) {
        List<Message> result = new ArrayList<Message>();
        URL u = null;
        int cnt;
        try {
            u = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            cnt=is.read(b);
            while (cnt>0) {
                baos.write(b, 0, cnt);
                cnt = is.read(b);
            }
            is.close();
            String JSONResp = new String(baos.toByteArray());
            Log.i("JSONResp=", JSONResp);
            JSONArray array = new JSONArray(JSONResp);
            for(int i = 0; i < array.length(); i++){
//                if(array.getJSONObject(i) != null){
                    //將資料加入ArrayList
                if (array.getJSONObject(i)!=null)
                    result.add(convertMessage(array.getJSONObject(i)));
//                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
    static Message convertMessage(JSONObject obj) throws JSONException
    {
//        int id = obj.getInt(" id");
        int id=0;
        String from_id = obj.getString(" from_id");
        String attn_id = obj.getString(" attn_id");
        String shelves_id = obj.getString(" shelves_id");
        String message = obj.getString(" message");
        Log.v("jsonObj=", obj.getString(" from_id").toString());

        return new Message(String.valueOf(id), from_id, attn_id, shelves_id,message);
    }
}
