package com.example.cyj.minesweep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyj on 2017/8/25.
 */

public class DataServer {

    public static List<MineEntity> getData() {
        List<MineEntity> mData = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            MineEntity entity = new MineEntity(Status.MARK_INITIAL, 1);
            mData.add(entity);
        }
        return mData;
    }
}
