package com.mycaculate.e2book;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class InsertWishlist extends AsyncTask<String, Void, String> {
    int member_id;
    int book_id;
    Context context;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";


    public InsertWishlist(Context context, String member_id, int book_id) {
        this.context = context;
        this.member_id = Integer.parseInt(member_id);
        this.book_id = book_id;
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
            request.writeBytes("Content-Disposition: form-data; name=\"member_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(String.valueOf(member_id));
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"book_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(String.valueOf(book_id));
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

        if (s.indexOf("add wishlist Success") != -1){
            Toast.makeText(context,"已經加入您的清單",Toast.LENGTH_SHORT).show();
        }
    }
}
