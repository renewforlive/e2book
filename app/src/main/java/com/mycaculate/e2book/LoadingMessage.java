package com.mycaculate.e2book;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class LoadingMessage extends AsyncTask<String,Void,ArrayList<Message>> {
    Context context;
    String member_id;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";

    public LoadingMessage(Context context, String member_id) {
        this.context = context;
        this.member_id = member_id;
    }

    @Override
    protected ArrayList<Message> doInBackground(String... strings) {
        ArrayList<Message> result = new ArrayList<Message>();
        URL url = null;
        try {
            //連線
            url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //檔頭區
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);
            conn.setRequestProperty("Charset", "UTF-8");
            //串流物件
            DataOutputStream request = new DataOutputStream(conn.getOutputStream());

            //上傳書名等資料
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"member_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(member_id);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
            request.flush();
            request.close();

            conn.connect();
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (is.read(b) != -1)
                baos.write(b);
            String JSONResp = new String(baos.toByteArray());
            Log.i("資料回傳成功",JSONResp );
            if(JSONResp.indexOf("[]") != -1){
                result = null;
            }
            else {
                JSONArray arr = new JSONArray(JSONResp);
                for (int i= 0; i<arr.length(); i++){
                    if (arr.getJSONObject(i) != null) {
                        result.add(convertMessage(arr.getJSONObject(i)));
                        Log.v("data=", arr.getJSONObject(i).toString());
                    }
                }
            }
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    private Message convertMessage(JSONObject obj) throws JSONException {

        int msg_id = obj.getInt("id");
        String msg = obj.getString("message");
        String sender = obj.getString("from_id");
        String recipient = obj.getString("attn_id");
        int shelves_id = obj.getInt("shelves_id");
        String create_time = obj.getString("create_time");
        String nickname = obj.getString("nickname");

        Log.v("jsonObj=",obj.getString("id").toString());


        return new Message(msg_id, msg, sender, recipient,shelves_id, create_time, nickname);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Message> arrayList) {
        super.onPostExecute(arrayList);
    }
}