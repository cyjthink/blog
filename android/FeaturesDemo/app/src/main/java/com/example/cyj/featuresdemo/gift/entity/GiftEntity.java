package com.example.cyj.featuresdemo.gift.entity;

/**
 * Created by cyj on 2017/6/2.
 * 礼物模型
 */

public class GiftEntity {

    private int giftId;
    private String giftName;
    private String giftPhoto;

    public GiftEntity() {
    }

    public GiftEntity(int giftId, String giftName, String giftPhoto) {
        this.giftId = giftId;
        this.giftName = giftName;
        this.giftPhoto = giftPhoto;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftPhoto() {
        return giftPhoto;
    }

    public void setGiftPhoto(String giftPhoto) {
        this.giftPhoto = giftPhoto;
    }
}
