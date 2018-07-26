package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class CreateMessage extends AsyncTask<String, Void,ArrayList<Message>> {
    private ProgressDialog dialog;
    Context context;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    String from_id;
    String attn_id;
    String shelves_id;
    String message;
    String create_time;
    String[] response;


    public CreateMessage(Context context, String from_id, String attn_id, String shelves_id, String message, String create_time) {
        this.context = context;
        this.from_id = from_id;
        this.attn_id = attn_id;
        this.shelves_id = shelves_id;
        this.message = message;
        this.create_time = create_time;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected ArrayList<Message> doInBackground(String... strings) {
        ArrayList<Message> response = new ArrayList();
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

            //上傳訊息等資料
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"from_id\""  + crlf);
            request.writeBytes(crlf);
            request.writeBytes(from_id);
            request.writeBytes(crlf);

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"attn_id\""  + crlf);
            request.writeBytes(crlf);
            request.writeBytes(attn_id);
            request.writeBytes(crlf);

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"shelves_id\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(shelves_id);
            request.writeBytes(crlf);

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"message\""  + crlf);
            request.writeBytes(crlf);
            request.writeBytes(message);
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
            String  resultString= new String(baos.toByteArray());
            Log.i("resultString=", resultString);
            JSONArray array = new JSONArray(resultString);
            for (int i = 0; i < array.length(); i++)
            {
                if (array.getJSONObject(i) != null)
                    response.add(ReadMessageData.convertMessage(array.getJSONObject(i)));
            }
            return response;

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
    protected void onPostExecute(ArrayList<Message> messages) {
        super.onPostExecute(messages);
        int isSuccess= 0;
//        isSuccess = books.indexOf("pass Success");
        if(isSuccess != -1) Toast.makeText(context,"成功傳遞訊息",Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }


}
