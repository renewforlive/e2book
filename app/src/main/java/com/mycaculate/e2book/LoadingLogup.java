package com.mycaculate.e2book;

import android.content.Context;
import android.content.Intent;
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

public class LoadingLogup extends AsyncTask<String,Void,String> {
    Context context;
    String[] data;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    int member_id;
    String nickname;


    public LoadingLogup(Context context, String[] data) {
        this.context = context;
        this.data = data;
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
            request.writeBytes("Content-Disposition: form-data; name=\"account" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(data[0]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"pwd" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(data[1]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"nickname" +"\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(data[2]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"email" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(data[3]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"area_id" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(data[4]);
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
        //如果成功上傳後
        int isSuccess = 0;
        isSuccess = s.indexOf("Log Up Success");
        if (isSuccess != -1){
            Toast.makeText(context,"新增一筆資料", Toast.LENGTH_SHORT).show();
            //取得memberID和nickname的資料
            member_id = Integer.parseInt(s.split(",")[1]);
            Log.i("member_id=",String.valueOf(member_id));
            nickname = s.split(":")[1];
            Log.i("nickname=",nickname);
            //將資料傳到主畫面
            String[] idForNickname = new String[]{String.valueOf(member_id),nickname};
            Intent intent = new Intent();
            intent.putExtra("bData",idForNickname);
            intent.setClass(context,MainActivity.class);
            context.startActivity(intent);
        }
    }
}
