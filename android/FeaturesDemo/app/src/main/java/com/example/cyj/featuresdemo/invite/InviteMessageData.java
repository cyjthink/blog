package com.example.cyj.featuresdemo.invite;

/**
 * Created by chenyujie on 2017/9/29.
 */

public class InviteMessageData {

    private String type;
    private String data;
    private String fromId;
    private String username;
    private String avator;

    public InviteMessageData() {
    }

    public InviteMessageData(String type, String data, String fromId, String username, String avator) {
        this.type = type;
        this.data = data;
        this.fromId = fromId;
        this.username = username;
        this.avator = avator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }
}
