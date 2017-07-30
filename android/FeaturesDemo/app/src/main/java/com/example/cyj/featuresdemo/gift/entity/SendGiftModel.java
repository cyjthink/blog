package com.example.cyj.featuresdemo.gift.entity;

/**
 * Created by cyj on 2017/7/29.
 * 赠送礼物的模型
 */

public class SendGiftModel {

    private int giftNum;
    private boolean isSelect;
    private GiftEntity giftEntity;

    public SendGiftModel() {
    }

    public SendGiftModel(int giftNum, boolean isSelect, GiftEntity giftEntity) {
        this.giftNum = giftNum;
        this.isSelect = isSelect;
        this.giftEntity = giftEntity;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public GiftEntity getGiftEntity() {
        return giftEntity;
    }

    public void setGiftEntity(GiftEntity giftEntity) {
        this.giftEntity = giftEntity;
    }
}
