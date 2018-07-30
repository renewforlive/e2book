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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static com.mycaculate.e2book.WebConnect.URI_IMAGES;

public class LoadingShelves extends AsyncTask<String, Void, ArrayList<Book>> {
    Context context;
    String member_id;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    String create_time;
    Bitmap bitmap;

    public LoadingShelves(Context context, String member_id) {
        this.context = context;
        this.member_id = member_id;
    }

    @Override
    protected ArrayList<Book> doInBackground(String... strings) {
        ArrayList<Book> result = new ArrayList<Book>();
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

            JSONArray arr = new JSONArray(JSONResp);
            for (int i= 0; i<arr.length(); i++){
                if (arr.getJSONObject(i) != null) {
                    result.add(convertBook(arr.getJSONObject(i)));
                    Log.v("data=", arr.getJSONObject(i).toString());

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
    private Book convertBook(JSONObject obj) throws JSONException {

        String filename = obj.getString("filename");

        if(filename != null) {
            bitmap = LoadImage(URI_IMAGES + filename);
        }else{
            bitmap = LoadImage(URI_IMAGES + "no_pictures.png");
        }

        String book_name = obj.getString("book_name");
        String catalog_id = String.valueOf(obj.getInt("catalog_id"));
        String author = obj.getString("author");
        String publisher = obj.getString("publisher");
        int book_id = obj.getInt("id");
        String create_time = obj.getString("create_time");

        Log.v("jsonObj=",obj.getString("id").toString());

        return new Book(bitmap, book_id, book_name, catalog_id, author, publisher,null, create_time);
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
    protected void onPostExecute(ArrayList<Book> arrayList) {
        super.onPostExecute(arrayList);
    }
}
