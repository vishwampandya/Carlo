package com.swich.carlo;

public class Message {

    String message;
    String senderID;
    String ReciverID;

    public Message(String message, String senderID, String reciverID) {
        this.message = message;
        this.senderID = senderID;
        ReciverID = reciverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReciverID() {
        return ReciverID;
    }

    public void setReciverID(String reciverID) {
        ReciverID = reciverID;
    }

}
