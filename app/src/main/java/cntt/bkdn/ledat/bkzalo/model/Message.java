package cntt.bkdn.ledat.bkzalo.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Message {
    public String sms;
    public String idUserSend;
    private String idUserReceive;
    private Bitmap imageView;
    private boolean isText;
    private boolean isPhoto;

    public Message(String sms, String idUserSend, String idUserReceive) {
        this.sms = sms;
        this.idUserSend = idUserSend;
        this.idUserReceive = idUserReceive;

    }

    public Message(Bitmap imageView, String idUserSend, String idUserReceive) {
        this.imageView = imageView;
        this.idUserSend = idUserSend;
        this.idUserReceive = idUserReceive;
    }

    public Message(boolean isText, boolean isPhoto) {
        this.isText = isText;
        this.isPhoto = isPhoto;
    }

    public Bitmap getImageView() {
        return imageView;
    }

    public void setImageView(Bitmap imageView) {
        this.imageView = imageView;
    }

    public Message(String sms) {
        this.sms = sms;
    }

    public Message() {
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getIdUserSend() {
        return idUserSend;
    }

    public void setIdUserSend(String idUserSend) {
        this.idUserSend = idUserSend;
    }

    public String getIdUserReceive() {
        return idUserReceive;
    }

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setPhoto(boolean photo) {
        isPhoto = photo;
    }
}
