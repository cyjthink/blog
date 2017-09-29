package com.example.cyj.featuresdemo;

import android.app.Application;
import android.text.TextUtils;

import com.example.cyj.featuresdemo.invite.InvitePreferencesUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chenyujie on 2017/9/29.
 */

public class FeatureApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String packageName = getPackageName();
        String processName = getProcessName(android.os.Process.myPid());

        // 1.每次主线程启动时清除储存的邀请消息数据 2.初始化添加、删除指针位置信息
        // 1.每次启动时清除邀请历史记录
        if (packageName.equals(processName)) {
            InvitePreferencesUtils.clearData(this);
            InvitePreferencesUtils.putData(this, Constants.ADD_POS, "0");
            InvitePreferencesUtils.putData(this, Constants.REMOVE_POS, "0");

//            InviteHistoryUtils.clearData(context);
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
