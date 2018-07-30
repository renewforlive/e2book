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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadCode extends AsyncTask<String, Void, List<CodeItem>>
{
    Context mctx;
    String classification;
    ProgressDialog dialog;
    String resultString;
    boolean includeEmpty;

    public LoadCode(Context context, String classification, boolean includeEmpty)
    {
        this.mctx=context;
        this.classification=classification;
        this.includeEmpty=includeEmpty;
        dialog=new ProgressDialog(this.mctx);
    }

    @Override
    protected List<CodeItem> doInBackground(String... strings) {
        List<CodeItem> result=new ArrayList<CodeItem>();
        URL u=null;
        try
        {
            u=new URL(strings[0]);
            Log.d("LoadCode", "doInBackground():u="+u);
            HttpURLConnection conn=(HttpURLConnection)u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String searchString="classification="+this.classification;
            OutputStream os=conn.getOutputStream();
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(searchString);
            writer.flush();
            writer.close();
            os.close();
            Log.d("LoadCode", "doInBackground():Post:"+searchString);
            InputStream is=conn.getInputStream();
            byte[] b=new byte[1024];
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            while (is.read(b)!=-1)
                baos.write(b);
            is.close();
            resultString =new String(baos.toByteArray());
            Log.d("LoadCode","response="+resultString);
            JSONArray array=new JSONArray(resultString);
            if (includeEmpty)
                result.add(new CodeItem(classification, 0, ""));
            for (int i=0;i<array.length();i++)
            {
                if (array.getJSONObject(i)!=null)
                    result.add(CodeItem.convertCodeItem(array.getJSONObject(i)));
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("資料讀取中...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(List<CodeItem> list) {
        super.onPostExecute(list);
        dialog.dismiss();
    }

    public static CodeItem convertCodeItem(JSONObject obj) throws JSONException {
        String classification=obj.getString("classification");
        int code = obj.getInt("picture");
        String description = obj.getString("description");
        Log.v("jsonObj=",obj.getString("description").toString());
        return new CodeItem(classification, code, description);
    }
}