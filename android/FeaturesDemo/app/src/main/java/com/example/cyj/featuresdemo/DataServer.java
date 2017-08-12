package com.example.cyj.featuresdemo;

import com.example.cyj.featuresdemo.broadcast.BroadcastEntity;
import com.example.cyj.featuresdemo.gift.entity.GiftEntity;
import com.example.cyj.featuresdemo.gift.entity.SendGiftModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyj on 2017/6/2.
 */

public class DataServer {

    public static List<SendGiftModel> mData;

    public static List<SendGiftModel> getData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GiftEntity giftEntity = new GiftEntity();
            SendGiftModel sendGiftModel = new SendGiftModel(1, false, giftEntity);
            mData.add(sendGiftModel);
        }
        return mData;
    }

    public static List<BroadcastEntity> getBroadcastEntityList() {
        List<BroadcastEntity> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new BroadcastEntity(0, "http://test-1252212268.coscd.myqcloud.com/home_treasure_btn_diamond.png", "第" + i + "条广播"));
        }
        return list;
    }
}
