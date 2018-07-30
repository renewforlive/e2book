package com.mycaculate.e2book;

public class Receipt {
    private int sender_id;
    private String sender_nickname;
    private boolean isSelected = false;

    public Receipt(int sender_id, String sender_nickname) {
        this.sender_id = sender_id;
        this.sender_nickname = sender_nickname;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_nickname() {
        return sender_nickname;
    }

    public void setSender_nickname(String sender_nickname) {
        this.sender_nickname = sender_nickname;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
