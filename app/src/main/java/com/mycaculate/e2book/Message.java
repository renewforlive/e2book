package com.mycaculate.e2book;

public class    Message {

    private int msg_id;
    private String msg;
    private String sender;
    private String recipient;
    private int shelves_id;
    private String create_time;
    private String senderNickname;

    public Message(int msg_id, String msg, String sender, String recipient, int shelves_id, String create_time, String senderNickname) {
        this.msg_id = msg_id;
        this.msg = msg;
        this.sender = sender;
        this.recipient = recipient;
        this.shelves_id = shelves_id;
        this.create_time = create_time;
        this.senderNickname = senderNickname;

    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
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