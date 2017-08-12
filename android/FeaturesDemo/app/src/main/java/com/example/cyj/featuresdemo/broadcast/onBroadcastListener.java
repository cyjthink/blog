package com.example.cyj.featuresdemo.broadcast;

/**
 * Created by cyj on 2017/8/11.
 */

public interface onBroadcastListener {
    void onStart(BroadcastEntity entity);

    void onNextStart();

    void onFinish();
}
