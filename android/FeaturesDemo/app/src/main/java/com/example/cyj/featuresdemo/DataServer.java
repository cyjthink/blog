package com.example.cyj.featuresdemo;

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
}
