package com.example.cyj.featuresdemo.invite;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cyj.featuresdemo.Constants;
import com.google.gson.Gson;

/**
 * Created by chenyujie on 2017/9/29.
 */

public class InvitePreferencesUtils {

    public static String PREFERENCE_NAME = InvitePreferencesUtils.class.getSimpleName();

    private InvitePreferencesUtils(){

    }

    /**
     * 将InviteMessageData数据存放入SharedPreferences
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putInviteMessageData(Context context, String key, InviteMessageData value) {
        Gson gson = new Gson();
        String strValue = gson.toJson(value);
        // 插入位置值+1
        int currAddPos = Integer.parseInt(getPosData(context, Constants.ADD_POS));
        updatePosData(context, Constants.ADD_POS, currAddPos + 1);
        return putData(context, key, strValue);
    }

    /**
     * 将数据存放入SharedPreferences
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean putData(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 从SharedPreferences获取InviteMessageData
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static InviteMessageData getInviteMessageData(Context context, String key, String defaultValue) {
        int currAddPos = Integer.parseInt(getPosData(context, Constants.ADD_POS));
        int currRemovePos = Integer.parseInt(getPosData(context, Constants.REMOVE_POS));

        // 防止循环读取时使mRemovePos无效增加
        if (currRemovePos == currAddPos) {
            return null;
        }

        String strValue = getData(context, key, defaultValue);
        // 获得数据后需要将该数据删除
        removeData(context, key);

        if (currAddPos >= currRemovePos + 1) {
            currRemovePos++;
            updatePosData(context, Constants.REMOVE_POS, currRemovePos);
        }

        Gson gson = new Gson();
        return gson.fromJson(strValue, InviteMessageData.class);
    }

    /**
     * 从SharedPreferences获取数据
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getData(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
        String strValue = settings.getString(key, defaultValue);
        return strValue;
    }

    /**
     * 获取添加、删除指针位置
     * @param context
     * @param key
     * @return
     */
    public static String getPosData(Context context, String key) {
        return getData(context, key, "0");
    }

    /**
     * 更新添加、删除指针位置
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean updatePosData(Context context, String key, int value) {
        return putData(context, key, value + "");
    }

    /**
     * 从SharedPreferences中删除数据
     * @param context
     * @param key
     * @return
     */
    public static boolean removeData(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 获取SharedPreferences里数据数目
     * @param context
     * @return
     */
    public static int getCount(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
        return settings.getAll().size();
    }

    /**
     * 清除SharedPreferences
     * @param context
     */
    public static void clearData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}
