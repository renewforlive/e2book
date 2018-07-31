package com.mycaculate.e2book;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static com.mycaculate.e2book.WebConnect.URI_IMAGES;

public class RankLoadingbook extends AsyncTask<String, Void, ArrayList<Bitmap>>{
    Context context;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    Bitmap bitmap;


    public RankLoadingbook(Context context) {
        this.context=context;
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        ArrayList<Bitmap> result = new ArrayList<Bitmap>();
        URL url = null;
        try {
            //建立連結
            url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //傳入類型的id
            conn.connect();

            // 讀取資料流
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (is.read(b) != -1)
                baos.write(b);
            String JSONResp = new String(baos.toByteArray());
            Log.i("Json = ", JSONResp);

            JSONArray arr = new JSONArray(JSONResp);
            for (int i = 0; i<arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                String filename = obj.getString("filename");

                if(filename != null) {
                    bitmap = LoadImage(URI_IMAGES + filename);
                    Log.i("bitmap",String.valueOf(bitmap));
                }else{
                    bitmap = LoadImage(URI_IMAGES + "no_pictures.png");
                }
                result.add(bitmap);
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

        return result;
    }
    private Bitmap LoadImage(String imageUrl){
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList strings) {
        super.onPostExecute(strings);
    }
}
