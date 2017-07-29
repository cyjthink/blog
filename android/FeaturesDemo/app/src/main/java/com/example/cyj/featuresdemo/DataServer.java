package com.example.cyj.featuresdemo;

import com.example.cyj.featuresdemo.gift.entity.GiftEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyj on 2017/6/2.
 */

public class DataServer {

    public static List<GiftEntity> mData;

    public static List<GiftEntity> getData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GiftEntity model = new GiftEntity();
            mData.add(model);
        }
        return mData;
    }
}
