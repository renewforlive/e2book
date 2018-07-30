package com.mycaculate.e2book;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
//    private int id;
    private String from_id;
    private String attn_id;
    private String shelves_id;
    private String message;

    public Message(String from_id, String attn_id, String shelves_id, String message) {
//        this.id=id;
        this.from_id = from_id;
        this.attn_id = attn_id;
        this.shelves_id = shelves_id;
        this.message = message;
    }

//    public int getId() {
//        return id;
//    }

    public String getFrom_id() {
        return from_id;
    }

    public String getAttn_id() {
        return attn_id;
    }

    public String getShelves_id() {
        return shelves_id;
    }

    public String getMessage() {
        return message;
    }
}