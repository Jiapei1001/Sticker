package edu.neu.firebase.sticker;

public class MsgCard {
    private int sticker_key;
    private String sender;
    private String receiver;
    private String sendTime;
    private String sticker;

    public MsgCard(int sticker_key, String sender, String receiver, String sendTime, String sticker) {
        this.sticker_key = sticker_key;
        this.sender = sender;
        this.receiver = receiver;
        this.sendTime = sendTime;
        this.sticker = sticker;
    }

    public int getSticker_key() {
        return sticker_key;
    }

    public void setSticker_key(int sticker_key) {
        this.sticker_key = sticker_key;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }
}
