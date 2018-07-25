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

<<<<<<< HEAD
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
=======
public class LoadingMessage extends AsyncTask<String, Void, ArrayList<Book>> {
    Context context;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    String from_id;
    String attn_id;
    String shelves_id;
    String message;
    String create_time;

    public Context getContext() {
        return context;
    }

    public String getFrom_id() {
        return from_id;
    }

    public String getAttn_id() {
        return attn_id;
    }

    public String getShelves_id() {
        return shelves_id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected ArrayList<Book> doInBackground(String... strings) {
        ArrayList<Book> result = new ArrayList<Book>();
>>>>>>> origin/jimmy20180725
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
<<<<<<< HEAD
            request.writeBytes("Content-Disposition: form-data; name=\"member_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(member_id);
            request.writeBytes(crlf);
=======
            request.writeBytes("Content-Disposition: form-data; name=\"from_id\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(from_id);
            request.writeBytes(crlf);

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"attn_id\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(attn_id);
            request.writeBytes(crlf);

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"shelves_id\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(shelves_id);
            request.writeBytes(crlf);

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"message\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(message);
            request.writeBytes(crlf);

>>>>>>> origin/jimmy20180725
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
<<<<<<< HEAD
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
=======

            Log.i("資料回傳成功",JSONResp );

            JSONArray arr = new JSONArray(JSONResp);

>>>>>>> origin/jimmy20180725
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
<<<<<<< HEAD
    private Message convertMessage(JSONObject obj) throws JSONException {

        String msg = obj.getString("message");
        String sender = member_id;
        String recipient = obj.getString("attn_id");
        int shelves_id = obj.getInt("shelves_id");
        String create_time = obj.getString("create_time");

        Log.v("jsonObj=",obj.getString("id").toString());


        return new Message(msg, sender, recipient,shelves_id, create_time);
    }
=======

>>>>>>> origin/jimmy20180725

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
<<<<<<< HEAD
    protected void onPostExecute(ArrayList<Message> arrayList) {
        super.onPostExecute(arrayList);
=======
    protected void onPostExecute(ArrayList<Book> books) {
        super.onPostExecute(books);
>>>>>>> origin/jimmy20180725
    }
}
