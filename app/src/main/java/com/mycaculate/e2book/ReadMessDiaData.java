package com.mycaculate.e2book;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ReadMessDiaData extends AsyncTask<String, Void, String[]> {
    private ProgressDialog dialog;
    Context c;
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary =  "*****";


    @Override
    protected String[] doInBackground(String... strings) {
        return new String[0];
    }
}
