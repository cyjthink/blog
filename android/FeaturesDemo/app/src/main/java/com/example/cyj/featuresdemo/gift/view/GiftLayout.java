package com.example.cyj.featuresdemo.gift.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyj.featuresdemo.R;
import com.example.cyj.featuresdemo.gift.entity.GiftEntity;
import com.example.cyj.featuresdemo.gift.entity.SendGiftModel;

/**
 * Created by cyj on 2017/7/30.
 */

public class GiftLayout extends FrameLayout {

    private ImageView ivAvator;
    private TextView tvUsername;
    private TextView tvGiftDes;
    private ImageView ivGiftPhoto;
    private TextView tvGiftNum;

    private boolean mIsShow;

    private SendGiftModel mSendGiftModel;

    public onAnimatorListener mOnAnimatorListener;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            AnimatorSet animatorSet = playAnimatorSet(playExitAnimator(), playResetAnimator());
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // 查看队列中是否还有数据
                    if (null != mOnAnimatorListener) {
                        mOnAnimatorListener.onAnimatorEnd();
                    }
                }
            });
            animatorSet.start();
        }
    };

    public GiftLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public GiftLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.include_gift_layout, this, false);
        ivAvator = (ImageView) view.findViewById(R.id.iv_avator);
        tvUsername = (TextView) view.findViewById(R.id.tv_username);
        tvGiftDes = (TextView) view.findViewById(R.id.tv_gift_des);
        ivGiftPhoto = (ImageView) view.findViewById(R.id.iv_gift_photo);
        tvGiftNum = (TextView) view.findViewById(R.id.tv_gift_num);
        addView(view);
    }

    /**
     * 赋值操作
     * @param sendGiftModel
     * @return
     */
    public GiftLayout setValue(SendGiftModel sendGiftModel) {
        this.mSendGiftModel = sendGiftModel;
        return this;
    }

    /**
     * 播放礼物进入动画
     *
     * @return
     */
    public ObjectAnimator playEnterAnimator() {
        mIsShow = true;
        this.setVisibility(VISIBLE);
        // 使对象可见(由于退出动画中有对alpha进行操作)
        this.setAlpha(1f);

        ObjectAnimator enterAnimator = enterInAnimator();
        return enterAnimator;
    }

    /**
     * 播放缩放礼物数量动画
     *
     * @param giftNum
     * @return
     */
    public ObjectAnimator playScaleTextAnimator(int giftNum) {
        tvGiftNum.setText("x" + giftNum);

        ObjectAnimator scaleAnimator = scaleTextAnimator();
        return scaleAnimator;
    }

    /**
     * 播放退出动画
     *
     * @return
     */
    public ObjectAnimator playExitAnimator() {
        ObjectAnimator exitAnimator = exitOrResetAnimator(0, -getHeight(), 300);
        exitAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画播放完毕后进行初始化操作
                mIsShow = false;
                mSendGiftModel.setGiftNum(0);
                mSendGiftModel.setGiftEntity(new GiftEntity(-1, "", ""));
                setVisibility(INVISIBLE);
            }
        });
        return exitAnimator;
    }

    /**
     * 播放复位动画
     *
     * @return
     */
    public ObjectAnimator playResetAnimator() {
        ObjectAnimator resetAnimator = exitOrResetAnimator(0, 0, 20);
        return resetAnimator;
    }

    /**
     * 播放动画集
     *
     * @param animator1
     * @param animator2
     * @return
     */
    public AnimatorSet playAnimatorSet(ObjectAnimator animator1, ObjectAnimator animator2) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1).before(animator2);
        return animatorSet;
    }

    public void postGiftMessageDelayed(long delay){
        mHandler.postDelayed(mRunnable, delay);
    }

    public void removeGiftCallback(){
        mHandler.removeCallbacks(mRunnable);
    }

    public boolean ismIsShow() {
        return mIsShow;
    }

    public SendGiftModel getmSendGiftModel() {
        return mSendGiftModel;
    }

    public void setmSendGiftModel(SendGiftModel mSendGiftModel) {
        this.mSendGiftModel = mSendGiftModel;
    }

    public interface onAnimatorListener{
        void onAnimatorEnd();
    }

    public void setmOnAnimatorListener(onAnimatorListener mOnAnimatorListener) {
        this.mOnAnimatorListener = mOnAnimatorListener;
    }

    /**
     * 礼物进入动画
     *
     * @return
     */
    private ObjectAnimator enterInAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", -this.getWidth(), 0);
        animator.setDuration(500);
        return animator;
    }

    /**
     * 缩放礼物数量文字动画
     *
     * @return
     */
    private ObjectAnimator scaleTextAnimator() {
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1.7f, 0.8f, 1.0f);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1.7f, 0.8f, 1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tvGiftNum, scaleXHolder, scaleYHolder);
        animator.setDuration(500);
        return animator;
    }

    /**
     * 退出或复位动画
     *
     * @param start
     * @param end
     * @param duration
     * @return
     */
    private ObjectAnimator exitOrResetAnimator(float start, float end, long duration) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("translationY", start, end);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, holder1, holder2);
        animator.setDuration(duration);
        return animator;
    }
}
