package com.example.cyj.featuresdemo.invite;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cyj.featuresdemo.R;
import com.example.cyj.featuresdemo.common.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.concurrent.LinkedBlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

public class InviteActivity extends AppCompatActivity implements InviteView.onClickListener{

    @BindView(R.id.tv_queue_count)
    TextView tvQueueCount;
    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.ll_invites)
    LinearLayout llInvites;

    // 用于标记第一次开启发送message
    private boolean mFirstTime = true;

    // 用于控制添加的view之间间隔
    private LinearLayout.LayoutParams mParams;

    // 用于设置添加、删除view的动画
    private LayoutTransition mLayoutTransition;

    // 阻塞队列
    private LinkedBlockingQueue<String> mBlockQueue = new LinkedBlockingQueue<>();

    // 三条邀请layout
    private InviteView mInviteView1, mInviteView2, mInviteView3;

    public static final int MESSAGE_WHAT = 1;
    // 延迟发送handler消息的时间
    public static final int MESSAGE_DELAY = 1000;
    // 默认邀请layout存在的时间
    public static final int DEFAULT_DURATION = 3500;
    public static final String TAG = InviteActivity.class.getSimpleName();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mBlockQueue.size() > 0) {
                // 寻找位置播放，若有位置则播放;否则继续等待
                if (!mInviteView1.ismIsShow()) {
                    inviteAnimatorStart(mInviteView1, DEFAULT_DURATION);
                } else if (!mInviteView2.ismIsShow()) {
                    inviteAnimatorStart(mInviteView2, DEFAULT_DURATION);
                } else if (!mInviteView3.ismIsShow()) {
                    inviteAnimatorStart(mInviteView3, DEFAULT_DURATION);
                }
                tvQueueCount.setText("queue count = " + mBlockQueue.size());
            }
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, MESSAGE_DELAY);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);

        initValue();
        initView();
    }

    private void initValue() {
//        for (int i = 0; i < 10; i++) {
//            putDataToQueue(i + "");
//        }
    }

    private void initView() {
        tvQueueCount.setText("queue count = " + mBlockQueue.size());

        mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParams.setMargins(0, DensityUtil.dp2px(this, 10f), 0, 0);

        mLayoutTransition = new LayoutTransition();
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translationX", DensityUtil.getWindowWidth(this), 0);
        // 设置view添加到linearlayout时的动画
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, animIn);
        // 设置view从linearlayout移除时的动画
        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, null);
        llInvites.setLayoutTransition(mLayoutTransition);

        mInviteView1 = new InviteView(this);
        mInviteView1.setInfo("---这是第1条信息---");
        mInviteView1.setmOnClickListener(this);
        mInviteView1.setmOnAnimatorListener(new InviteView.onAnimatorListener() {
            @Override
            public void onAnimatorOver() {
                inviteAnimatorOver(mInviteView1);
            }
        });

        mInviteView2 = new InviteView(this);
        mInviteView2.setInfo("---这是第2条信息---");
        mInviteView2.setmOnClickListener(this);
        mInviteView2.setmOnAnimatorListener(new InviteView.onAnimatorListener() {
            @Override
            public void onAnimatorOver() {
                inviteAnimatorOver(mInviteView2);
            }
        });

        mInviteView3 = new InviteView(this);
        mInviteView3.setInfo("---这是第3条信息---");
        mInviteView3.setmOnClickListener(this);
        mInviteView3.setmOnAnimatorListener(new InviteView.onAnimatorListener() {
            @Override
            public void onAnimatorOver() {
                inviteAnimatorOver(mInviteView3);
            }
        });
    }

    @OnClick(R.id.btn_click)
    public void onClick() {
        putDataToQueue("new item");
        tvQueueCount.setText("queue count = " + mBlockQueue.size());

        if (mFirstTime) {
            mFirstTime = false;
            mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, MESSAGE_DELAY);
        }
    }

    /**
     * 将数据添加到队列中
     *
     * @param data
     */
    private void putDataToQueue(String data) {
        try {
            mBlockQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从队列中取出数据
     *
     * @return
     */
    private String takeDataFromQueue() {
        String data = "";
        try {
            data = mBlockQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 加入到linearlayout并开始显示倒计时
     * @param inviteView
     */
    private void inviteAnimatorStart(InviteView inviteView, long duration) {
        if (inviteView.getParent() != null) {
            Log.e(TAG, "error !!!");
            llInvites.removeView(inviteView);
        }

        inviteView.setmIsShow(true);
        Log.d(TAG, "time = " + createMsgYMDHMSStr(System.currentTimeMillis()) + " data = " + takeDataFromQueue());
        // 添加到linearlayou
        llInvites.addView(inviteView, mParams);
        // 开始播放动画
        inviteView.animatorStart(duration);
    }

    /**
     * 显示结束时将其从linearlayout中移除并初始化值
     */
    private void inviteAnimatorOver(InviteView inviteView) {
        llInvites.removeView(inviteView);
        inviteView.setmIsShow(false);
        if (inviteView.getParent() != null) {
            Log.w(TAG, "warning, view3 not remove");
        }
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

    @SuppressLint("SimpleDateFormat")
    public static final String createMsgYMDHMSStr(long time) {
        time = changeTime(time);
        String reTime = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-M-d H:mm:ss");
        reTime = sf.format(time);
        return reTime;
    }

    public static long changeTime(long time) {
        if (String.valueOf(time).length() == 10) {
            time = time * 1000;
        }
        return time;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MESSAGE_WHAT);
    }
}
