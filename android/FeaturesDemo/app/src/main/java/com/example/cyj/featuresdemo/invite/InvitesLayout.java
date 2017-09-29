package com.example.cyj.featuresdemo.invite;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.cyj.featuresdemo.Constants;
import com.example.cyj.featuresdemo.common.DensityUtil;

/**
 * 重点：每隔固定时间去查看队列中的数据
 * 思路：
 *  1.获取到数据后加入到队列中
 *  2.开启循环，每隔固定时间查看队列是否有可用数据
 *      1.若有可用数据，查看是否有空闲的InviteView
 *          1.若有空闲位置，显示数据并从队列中将数据移除
 *          2.没有空闲位置，不做操作
 *      2.没有可用数据，不做操作
 *      3.发送一个延时message
 *  3.动画播放完毕后从容器中移除掉
 * Created by cyj on 2017/9/15.
 */

public class InvitesLayout extends LinearLayout implements InviteView.onClickListener {

    // 当前没有数据时重新读取数据次数
    private int mRetryTime;

    // 用于标记开启looper
    private boolean mInitLooper = false;

    // 用于标记当前是否可见
    private boolean mIsVisiable = false;

    // 用于控制添加的view之间间隔
    private LinearLayout.LayoutParams mParams;

    // 用于设置添加、删除view的动画
    private LayoutTransition mLayoutTransition;

    // 三条邀请子布局
    private InviteView mInviteView1, mInviteView2, mInviteView3;

    public static final int MESSAGE_WHAT = 1;
    // 延迟发送handler消息的时间
    public static final int MESSAGE_DELAY = 1000;
    // 默认邀请layout存在的时间
    public static final int DEFAULT_DURATION = 3000;
    // 默认拒绝存在的时间
    public static final int DEFAULT_REFUSE_DURATION = 3000;
    // 默认没有数据时的重试次数
    public static final int DEFAULT_RETRY_TIME = 10;

    public static final String TAG = InvitesLayout.class.getSimpleName();

    /**
     * 寻找空闲的位置:
     * 1.若有位置则将其添加到容器中
     * 2.继续每隔1s循环读取数据
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (InvitePreferencesUtils.getCount(getContext()) > 2) {

                // 一旦有数据则重置retry次数
                mRetryTime = 0;

                if (!mInviteView1.ismIsShow()) {
                    addToGroupAndDisplay(mInviteView1);
                } else if (!mInviteView2.ismIsShow()) {
                    addToGroupAndDisplay(mInviteView2);
                } else if (!mInviteView3.ismIsShow()) {
                    addToGroupAndDisplay(mInviteView3);
                }
            } else {
                // 此时没有新数据
                if (mRetryTime < DEFAULT_RETRY_TIME) {
                    mRetryTime++;

                } else {

                    // 此时经过10次的重新读取数据后还是没有新数据，那么 1.将looper停止 2.初始化值
                    mHandler.removeMessages(MESSAGE_WHAT);
                    mRetryTime = 0;
                    mInitLooper = false;
                    return;
                }
            }
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, MESSAGE_DELAY);
        }
    };

    public InvitesLayout(Context context) {
        super(context);
        init();
    }

    public InvitesLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InvitesLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setWillNotDraw(false);

        mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParams.setMargins(0, DensityUtil.dp2px(getContext(), 10f), 0, 0);

        mLayoutTransition = new LayoutTransition();
        // 设置view添加到linearlayout时的动画
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translationX", DensityUtil.getWindowWidth((Activity) getContext()), 0);
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, animIn);
        // 设置view从linearlayout移除时的动画
        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, null);
        setLayoutTransition(mLayoutTransition);

        mInviteView1 = new InviteView(getContext());
        mInviteView1.setmOnClickListener(this);
        mInviteView1.setmOnAnimatorListener(new InviteView.onAnimatorListener() {
            @Override
            public void onAnimatorOver() {
                removeFromGroup(mInviteView1);
            }
        });

        mInviteView2 = new InviteView(getContext());
        mInviteView2.setmOnClickListener(this);
        mInviteView2.setmOnAnimatorListener(new InviteView.onAnimatorListener() {
            @Override
            public void onAnimatorOver() {
                removeFromGroup(mInviteView2);
            }
        });

        mInviteView3 = new InviteView(getContext());
        mInviteView3.setmOnClickListener(this);
        mInviteView3.setmOnAnimatorListener(new InviteView.onAnimatorListener() {
            @Override
            public void onAnimatorOver() {
                removeFromGroup(mInviteView3);
            }
        });
    }

    @Override
    public void onIgnoreClick() {
    }

    @Override
    public void onRefuseClick() {
    }

    @Override
    public void onAcceptClick() {
    }

    /**
     * 收到消息时:
     * 1.将消息存入文件中
     * 2.开启循环读取数据
     */
    public void onReceiveData(InviteMessageData data) {
        String key = InvitePreferencesUtils.getPosData(getContext(), Constants.ADD_POS);
        InvitePreferencesUtils.putInviteMessageData(getContext(), key, data);

        startLoop();
    }

    /**
     * 开启循环
     */
    public void startLoop() {
        if (!mInitLooper) {
            mInitLooper = true;
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, MESSAGE_DELAY);
        }
    }

    /**
     * 添加到容器中并显示布局(即延迟duration后将布局移除)
     *
     * @param inviteView
     */
    private void addToGroupAndDisplay(InviteView inviteView) {
        // 若容器中已经存在该对象，则需要先移除
        if (inviteView.getParent() != null) {
            removeView(inviteView);
//            Log.e("cyj","error!!! getparent is null");
        }

        // 从队列中取出数据
        String key = InvitePreferencesUtils.getPosData(getContext(), Constants.REMOVE_POS);
        InviteMessageData data = InvitePreferencesUtils.getInviteMessageData(getContext(), key, "");
        if (data == null) {
//            Log.e("cyj","wraning!!! data is null");
            return;
        }

        // 设置InviteView基本参数
        inviteView.setmIsShow(true);

        // 设置InviteLayout信息

        // 添加到linearlayou
        addView(inviteView, mParams);

        // 开始播放动画
        inviteView.delayCloseItem(DEFAULT_DURATION);
    }

    /**
     * 动画结束时：
     * 1.将控件从容器中移除并初始化值
     */
    private void removeFromGroup(InviteView inviteView) {
        removeView(inviteView);

        inviteView.setmIsShow(false);
    }

    /**
     * 关闭邀请控件
     */
    public void closeLayout() {
        if (mInviteView1.ismIsShow()) {
            mInviteView1.closeItem();
        }
        if (mInviteView2.ismIsShow()) {
            mInviteView2.closeItem();
        }
        if (mInviteView3.ismIsShow()) {
            mInviteView3.closeItem();
        }
        mHandler.removeMessages(MESSAGE_WHAT);
        mRetryTime = 0;
        mInitLooper = false;
    }

    public boolean ismIsVisiable() {
        return mIsVisiable;
    }

    public void setmIsVisiable(boolean mIsVisiable) {
        this.mIsVisiable = mIsVisiable;
    }
}
