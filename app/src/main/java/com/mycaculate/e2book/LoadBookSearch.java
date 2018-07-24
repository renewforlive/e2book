package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadBookSearch extends AsyncTask<String, Void, List<BookSearch>>
{
    private ProgressDialog dialog;
    private Context mctx;
    private String keyword;
    private int catalog, location;

    public LoadBookSearch(Context mctx, int catalog, int location, String keyword)
    {
        this.mctx=mctx;
        this.catalog=catalog;
        this.location=location;
        this.keyword=keyword;
        dialog=new ProgressDialog(this.mctx);
    }

    @Override
    protected List<BookSearch> doInBackground(String... strings) {
        String resultString;
        List<BookSearch> result=new ArrayList<BookSearch>();
        JSONObject object;
        URL u=null;
        try
        {
            u=new URL(strings[0]);
            Log.d("LoadBookSearch", "doInBackground():u="+u);
            HttpURLConnection conn=(HttpURLConnection)u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String searchString="";
            if (this.catalog!=0)
                searchString=searchString.concat("catalog="+String.valueOf(this.catalog));
            if (this.location!=0)
                searchString=searchString.concat((searchString.length()>0?"&":"")+"location="+String.valueOf(this.location));
            if (this.keyword.length()>0)
                searchString=searchString.concat((searchString.length()>0?"&":"")+"keyword="+keyword);
            Log.d("LoadBookSearch", "searchString="+searchString);
            OutputStream os=conn.getOutputStream();
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(searchString);
            writer.flush();
            writer.close();
            os.close();
            Log.d("LoadBookSearch", "doInBackground():Post:"+searchString);
            InputStream is=conn.getInputStream();
            byte[] b=new byte[1024];
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            while (is.read(b)!=-1)
                baos.write(b);
            is.close();
            resultString =new String(baos.toByteArray());
            Log.d("LoadBookSearch","response="+resultString);

            JSONArray array=new JSONArray(resultString);
            for (int i=0;i<array.length();i++)
            {
                if (array.getJSONObject(i)!=null)
                    result.add(BookSearch.convertBookSearch(array.getJSONObject(i)));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("資料讀取中...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(List<BookSearch> bookSearches) {
        super.onPostExecute(bookSearches);
        dialog.dismiss();
    }

}
