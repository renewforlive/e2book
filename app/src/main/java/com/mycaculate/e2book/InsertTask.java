package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class InsertTask extends AsyncTask<String, Void, String> {
    Context context;
    private ProgressDialog dialog;
    String twoHyphens = "--";
    String crlf = "\r\n";
    String boundary = "*****";
    private String[] getData;
    public int index;
    Intent intent;
    String attachmentFileName;
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;

    public InsertTask(Context context, String[] data, String picturepath) {
            this.context = context;
            dialog = new ProgressDialog(context);
            this.getData = data;
            this.attachmentFileName = picturepath;
    }

    @Override
    protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("資料讀取中~~");
            dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            int isSuccess = 0;
            isSuccess = s.indexOf("add Success");
            if (isSuccess != -1){
                Toast.makeText(context,"新增一筆資料", Toast.LENGTH_SHORT).show();
                index = Integer.parseInt(s.split(",")[1]);
                Log.i("id=",String.valueOf(index));
                InsertImgTask insertImgTask = new InsertImgTask(context,attachmentFileName);
                insertImgTask.execute("http://renewforlive11.000webhostapp.com/test/insertimg.php");

            }
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
            request.writeBytes("Content-Disposition: form-data; name=\"book_name\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(getData[0]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"catalog_id\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(getData[1]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"author\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(getData[2]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"publisher\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(getData[3]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"price\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(getData[4]);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"notes\"" + "\"" + crlf);
            request.writeBytes(crlf);
            request.writeBytes(getData[5]);
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
    class InsertImgTask extends AsyncTask<String, Void, String>{
        Context context;
        String attachmentFileName;
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;


        public InsertImgTask(Context context, String attachmentFileName) {
            this.attachmentFileName = attachmentFileName;
            this.context = context;
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
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                conn.setRequestProperty("Charset", "UTF-8");
                //串流物件
                DataOutputStream request = new DataOutputStream(conn.getOutputStream());
                if(this.attachmentFileName != null) {
                    File sourceFile = new File(attachmentFileName);
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    request.writeBytes(twoHyphens + boundary + crlf);
                    request.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + this.attachmentFileName + "\"" + crlf);
                    request.writeBytes(crlf);

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        request.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }
                    request.write(buffer);
                    request.writeBytes(crlf);
                }
                request.writeBytes(twoHyphens + boundary + crlf);
                request.writeBytes("Content-Disposition: form-data; name=\"classification\"" + "\"" + crlf);
                request.writeBytes(crlf);
                request.writeBytes("book");
                request.writeBytes(crlf);
                request.writeBytes(twoHyphens + boundary + crlf);
                request.writeBytes("Content-Disposition: form-data; name=\"reference_id\"" + "\"" + crlf);
                request.writeBytes(crlf);
                request.writeBytes(String.valueOf(index));
                request.writeBytes(crlf);
                request.writeBytes(twoHyphens + boundary + crlf);
                request.writeBytes("Content-Disposition: form-data; name=\"description\"" + "\"" + crlf);
                request.writeBytes(crlf);
                request.writeBytes("book");
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


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
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
                context.startActivity(new Intent(context,MainActivity.class));
            }
        }

}
