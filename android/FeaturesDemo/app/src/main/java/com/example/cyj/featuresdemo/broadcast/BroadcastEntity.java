package com.example.cyj.featuresdemo.broadcast;

/**
 * Created by cyj on 2017/8/10.
 */

public class BroadcastEntity {

    private float currPos;
    private String avatar;
    private String content;

    public BroadcastEntity(float currPos, String avatar, String content) {
        this.currPos = currPos;
        this.avatar = avatar;
        this.content = content;
    }

    public float getCurrPos() {
        return currPos;
    }

    public void setCurrPos(float currPos) {
        this.currPos = currPos;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
