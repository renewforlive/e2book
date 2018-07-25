package com.mycaculate.e2book;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class InsertMessage extends AsyncTask<String, Void, String> {
    Context context;
    String sender_id,recipient_id,shelves_id,text;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";

    public InsertMessage(Context context, String sender_id,String recipient_id, String text, String shelves_id) {
        this.context = context;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.text = text;
        this.shelves_id = shelves_id;
    }

    @Override
    protected String doInBackground(String... strings) {
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
            Log.i("String===>",request.toString());
            request.writeBytes("Content-Disposition: form-data; name=\"from_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(sender_id);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"attn_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(recipient_id);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"shelves_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(shelves_id);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"message" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(text);
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
            String response = new String(baos.toByteArray());
            Log.i("資料回傳成功",response );

            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
