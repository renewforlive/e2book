package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class AddWishList {
    private Context mctx;
    private int ownerId, bookId, focusId;

    public AddWishList(Context mctx, int ownerId)
    {
        this.mctx = mctx;
        this.ownerId = ownerId;
        this.bookId = bookId;
        this.focusId = focusId;
    }

    public int addBook(int bookId, int focusId)
    {
        int ret=0;
        if (ownerId==0)
        {
            Toast.makeText(mctx, "無使用者id", Toast.LENGTH_SHORT).show();
            return ret;
        }
        if (bookId==0)
        {
            Toast.makeText(mctx, "無書籍id", Toast.LENGTH_SHORT).show();
            return ret;
        }
        try
        {
            AddWishListTask task = new AddWishListTask(mctx, ownerId, bookId, focusId);
            String result = task.execute(WebConnect.URI_ADDWISHLIST).get();
            try
            {
                ret = Integer.parseInt(result);
                Toast.makeText(mctx, "加入成功("+ret+")", Toast.LENGTH_LONG).show();
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
                Toast.makeText(mctx, result, Toast.LENGTH_LONG).show();
                ret=0;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
