package com.example.cyj.featuresdemo.invite;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by chenyujie on 2017/9/29.
 */

public class InviteMessageReceiver extends BroadcastReceiver {

    private WeakReference<Activity> mReference;

    public static final String TAG = InviteMessageReceiver.class.getSimpleName();

    public InviteMessageReceiver(Activity activity) {
        this.mReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 过滤掉60秒之前的请求（通过RongIM的send_time\receive_time来实现）
        // 过滤掉来自同一个房间的邀请
        // 过滤同一对象、同一类型、60s内多次请求（将请求记录保存起来之后若有相同的则更新）

        InviteActivity activity = (InviteActivity) mReference.get();
        if (activity == null) {
            return;
        }
        activity.addInviteLayoutToParentGroup();
        activity.getmInvitesLayout().onReceiveData(new InviteMessageData());
    }
}
