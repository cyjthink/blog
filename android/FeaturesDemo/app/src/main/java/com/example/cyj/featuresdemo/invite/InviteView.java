package com.example.cyj.featuresdemo.invite;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyj.featuresdemo.R;

/**
 * Created by cyj on 2017/9/15.
 */

public class InviteView extends FrameLayout {

    private ImageView ivAvator;
    private TextView tvTitle;
    private TextView tvInfo;
    private TextView tvIgnore;
    private TextView tvRefuse;
    private TextView tvAccept;

    public interface onClickListener {
        void onIgnoreClick();

        void onRefuseClick();

        void onAcceptClick();
    }

    public interface onAnimatorListener {
        void onAnimatorOver();
    }

    private boolean mIsShow = false;

    private onClickListener mOnClickListener;

    private onAnimatorListener mOnAnimatorListener;

    private Handler mHandler = new Handler();

    private Runnable mExitRunnable = new Runnable() {
        @Override
        public void run() {
            animatorOver();
        }
    };

    public InviteView(@NonNull Context context) {
        super(context);
        init();
    }

    public InviteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InviteView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View inviteView = LayoutInflater.from(getContext()).inflate(R.layout.part_invite, (ViewGroup) getParent(), false);
        ivAvator = (ImageView) inviteView.findViewById(R.id.iv_avator);
        tvTitle = (TextView) inviteView.findViewById(R.id.tv_title);
        tvInfo = (TextView) inviteView.findViewById(R.id.tv_info);
        tvIgnore = (TextView) inviteView.findViewById(R.id.tv_ignore);
        tvRefuse = (TextView) inviteView.findViewById(R.id.tv_refuse);
        tvAccept = (TextView) inviteView.findViewById(R.id.tv_accept);
        addView(inviteView);

        tvIgnore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    // 点击时移除callback，直接进行动画结束操作
                    mHandler.removeCallbacks(mExitRunnable);
                    animatorOver();
                    mOnClickListener.onIgnoreClick();
                }
            }
        });
        tvRefuse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    // 点击时移除callback，直接进行动画结束操作
                    mHandler.removeCallbacks(mExitRunnable);
                    animatorOver();
                    mOnClickListener.onRefuseClick();
                }
            }
        });
        tvAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    // 点击时移除callback，直接进行动画结束操作
                    mHandler.removeCallbacks(mExitRunnable);
                    animatorOver();
                    mOnClickListener.onAcceptClick();
                }
            }
        });
    }

    public void setInfo(String title) {
        tvTitle.setText(title);
    }

    /**
     * 开始播放礼物
     *
     * @param duration 从动画开始到结束存在的时间
     */
    public void animatorStart(long duration) {
        // 将该操作至于activity中进行
//        mIsShow = true;
        mHandler.postDelayed(mExitRunnable, duration);
    }

    /**
     * 动画结束时
     *
     * @return
     */
    public void animatorOver() {
        if (mOnAnimatorListener != null) {
            mOnAnimatorListener.onAnimatorOver();
        }
    }

    public void setmOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setmOnAnimatorListener(onAnimatorListener mOnAnimatorListener) {
        this.mOnAnimatorListener = mOnAnimatorListener;
    }

    public boolean ismIsShow() {
        return mIsShow;
    }

    public void setmIsShow(boolean mIsShow) {
        this.mIsShow = mIsShow;
    }
}
