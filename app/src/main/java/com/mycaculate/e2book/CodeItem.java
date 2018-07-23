package com.mycaculate.e2book;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CodeItem {
    private String classification;
    private int code;
    private String description;

    public CodeItem(String classification, int code, String description) {
        this.classification=classification;
        this.code = code;
        this.description = description;
    }

    public String getClassification() { return classification; }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static List<CodeItem> getList(String Classifiaction)
    {
        return null;
    }

    static CodeItem convertCodeItem(JSONObject obj) throws JSONException
    {

        String classification = obj.getString("classification");
        int code = obj.getInt("id");
        String description = obj.getString("description");
        Log.v("jsonObj=", obj.getString("description").toString());

        return new CodeItem(classification, code, description);
    }
}
