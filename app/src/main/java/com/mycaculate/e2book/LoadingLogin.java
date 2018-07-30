package com.mycaculate.e2book;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class LoadingLogin extends AsyncTask<String,Void,String[]>{
    Context context;
    String account;
    String password;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    int member_index;
    String member_nickname;
    String[] idForNickname;




    public LoadingLogin(Context context, String account, String password) {
        this.context = context;
        this.account = account;
        this.password = password;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        URL url = null;
        try {
            //建立連結
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
            //傳入類型的id
            //串流物件
            DataOutputStream request = new DataOutputStream(conn.getOutputStream());
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"account" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(account);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"password" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(password);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
            request.flush();
            request.close();
            conn.connect();

            // 讀取資料流
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (is.read(b) != -1)
                baos.write(b);
            String JSONResp = new String(baos.toByteArray());
            Log.i("Json = ", JSONResp);
            if (JSONResp.indexOf("no account") != -1){
                Log.i("no account","沒帳號");
                String[] noAccount = new String[]{String.valueOf(0),String.valueOf(0)};
                return noAccount;
            }
            else if (JSONResp.indexOf("password fail") != -1){
                Log.i("password fail","密碼錯誤");
                String[] passwordFail = new String[]{String.valueOf(0),String.valueOf(1)};
                return passwordFail;
            }
            else{
                JSONArray arr = new JSONArray(JSONResp);
                JSONObject obj = arr.getJSONObject(0);
                member_index = obj.getInt("id");
                member_nickname = obj.getString("nickname");
                idForNickname = new String[]{String.valueOf(member_index),member_nickname};

                return idForNickname;
            }

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
