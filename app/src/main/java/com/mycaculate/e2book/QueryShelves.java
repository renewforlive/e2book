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

public class QueryShelves extends AsyncTask<String, Void, String[]> {
    Context context;
    String book_id;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    String create_time;


    public QueryShelves(Context context, String book_id) {
        this.context = context;
        this.book_id = book_id;
    }

    @Override
    protected String[] doInBackground(String... strings) {
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
            request.writeBytes("Content-Disposition: form-data; name=\"book_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(book_id);
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

            JSONArray arr = new JSONArray(JSONResp);
            JSONObject obj = arr.getJSONObject(0);
            int owner_id = obj.getInt("owner_id");
            int shelves_id = obj.getInt("id");
            String nickname = obj.getString("nickname");

            String[] ownerAndShelves_idAndNickname = new String[]{String.valueOf(owner_id),String.valueOf(shelves_id),nickname};
            return ownerAndShelves_idAndNickname;

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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);
    }
}
