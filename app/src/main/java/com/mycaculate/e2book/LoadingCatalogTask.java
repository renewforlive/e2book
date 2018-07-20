package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
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

public class LoadingCatalogTask extends AsyncTask<String, Void, ArrayList<Book>> {
    Context context;
    private final ProgressDialog progressDialog;
    private int catalog_id;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    Bitmap bitmap;

    public LoadingCatalogTask(Context context, int catalog_id) {
        this.context = context;
        this.catalog_id = catalog_id;
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("資料下載中...");
        progressDialog.show();
    }

    @Override
    protected ArrayList<Book> doInBackground(String... strings) {
        ArrayList<Book> result = new ArrayList<Book>();
        URL url = null;
        try {
            //建立連結
            url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");


            //檔頭區
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);
            conn.setRequestProperty("Charset", "UTF-8");
            //傳入類型的id
            //串流物件
            DataOutputStream request = new DataOutputStream(conn.getOutputStream());
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"catalog_id\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(String.valueOf(catalog_id));
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
            request.flush();
            request.close();
            conn.connect();

//            InputStream iss = conn.getInputStream();
//            byte[] ba = new byte[1024];
//            ByteArrayOutputStream baos_up = new ByteArrayOutputStream();
//            while (iss.read(ba) != -1)
//                baos_up.write(ba);
//            String response = new String(baos_up.toByteArray());
//            Log.i("資料回傳成功",response );

            // 讀取資料流
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (is.read(b) != -1)
                baos.write(b);
            String JSONResp = new String(baos.toByteArray());
            Log.i("Json = ", JSONResp);

            JSONArray array = new JSONArray(JSONResp);
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i) != null) {
                    result.add(convertBook(array.getJSONObject(i)));
                    Log.v("data=", array.getJSONObject(i).toString());
                }
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
    private Book convertBook(JSONObject obj) throws JSONException {
//        Bitmap bitmap;
//        if(obj.getString("Picture") != null) {
//            bitmap = LoadImage("http://renewforlive11.000webhostapp.com/images/" + obj.getString("Picture").toString());
//        }else{
//            bitmap = LoadImage("http://renewforlive11.000webhostapp.com/apple.jpg");
//        }
        String book_name = obj.getString("book_name");
        String catalog_id = obj.getString("catalog_id");
        String author = obj.getString("author");
        String publisher = obj.getString("publisher");

        Log.v("jsonObj=",obj.getString("book_name").toString());

        return new Book(bitmap, book_name, catalog_id, author, publisher);
    }


    @Override
    protected void onPostExecute(ArrayList<Book> books) {
        super.onPostExecute(books);
        progressDialog.dismiss();
    }
}
