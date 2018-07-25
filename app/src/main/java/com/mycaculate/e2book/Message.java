package com.mycaculate.e2book;

public class Message {

    private String msg;
    private String sender;
    private String recipient;
    private int shelves_id;
    private String create_time;

    public Message(String msg, String sender, String recipient, int shelves_id,String create_time) {
        this.msg = msg;
        this.sender = sender;
        this.recipient = recipient;
        this.shelves_id = shelves_id;
        this.create_time = create_time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getShelves_id() {
        return shelves_id;
    }

    public void setShelves_id(int shelves_id) {
        this.shelves_id = shelves_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
