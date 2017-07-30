package com.example.cyj.featuresdemo.gift.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyj.featuresdemo.DataServer;
import com.example.cyj.featuresdemo.R;
import com.example.cyj.featuresdemo.common.DensityUtil;
import com.example.cyj.featuresdemo.gift.adapter.GiftPagerAdapter;
import com.example.cyj.featuresdemo.gift.entity.GiftEntity;
import com.example.cyj.featuresdemo.gift.entity.SendGiftModel;
import com.example.cyj.featuresdemo.gift.view.AdjustableViewPager;
import com.example.cyj.featuresdemo.gift.view.GiftLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendGiftActivity extends AppCompatActivity implements GiftFragment.onItemClickListener {

    @BindView(R.id.iv_send_gift)
    ImageView ivSendGift;
    @BindView(R.id.view_send_gift_blank)
    View viewSendGiftBlank;
    @BindView(R.id.iv_switch_left)
    ImageView ivSwitchLeft;
    @BindView(R.id.iv_avator)
    ImageView ivAvator;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_switch_right)
    ImageView ivSwitchRight;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.view_pager)
    AdjustableViewPager viewPager;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;
    @BindView(R.id.iv_diamond)
    ImageView ivDiamond;
    @BindView(R.id.tv_diamond_num)
    TextView tvDiamondNum;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.tv_please_recharge)
    TextView tvPleaseRecharge;
    @BindView(R.id.ll_dots)
    LinearLayout llDots;
    @BindView(R.id.view_dot_white)
    View viewDotWhite;
    @BindView(R.id.tv_send_gift)
    TextView tvSendGift;
    @BindView(R.id.ll_send_gift)
    LinearLayout llSendGift;
    @BindView(R.id.gift_layout_1)
    GiftLayout giftLayout1;
    @BindView(R.id.gift_layout_2)
    GiftLayout giftLayout2;
    @BindView(R.id.gift_layout_3)
    GiftLayout giftLayout3;

    private int mDotSpace;
    private int mDotSize;
    private int mLeftMargin;

    private int mLastPager = -1;
    private int mLastPosInTotal = -1;
    private int mLastPos = -1;

    private boolean mIsFirstTime = true;

    private GiftPagerAdapter mPagerAdapter;

    private List<GiftLayout> mGiftLayoutList;
    private List<SendGiftModel> mQueue = new ArrayList<>();
    private List<SendGiftModel> mData = DataServer.getData();

    public static final String TAG = SendGiftActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gift);
        ButterKnife.bind(this);

        initValue();
        initGiftLayout();
    }

    private void initValue() {
        mDotSize = DensityUtil.dp2px(this, 6f);
        mLeftMargin = DensityUtil.dp2px(this, 10f);
    }

    private void initGiftLayout() {
        mGiftLayoutList = new ArrayList<>();
        mGiftLayoutList.add(giftLayout1.setValue(new SendGiftModel(0, false, new GiftEntity(-1, "", ""))));
        mGiftLayoutList.add(giftLayout2.setValue(new SendGiftModel(0, false, new GiftEntity(-1, "", ""))));
        mGiftLayoutList.add(giftLayout3.setValue(new SendGiftModel(0, false, new GiftEntity(-1, "", ""))));

        giftLayout1.setmOnAnimatorListener(new GiftLayout.onAnimatorListener() {
            @Override
            public void onAnimatorEnd() {
                takeDataFromQueue();
            }
        });
        giftLayout2.setmOnAnimatorListener(new GiftLayout.onAnimatorListener() {
            @Override
            public void onAnimatorEnd() {
                takeDataFromQueue();
            }
        });
        giftLayout3.setmOnAnimatorListener(new GiftLayout.onAnimatorListener() {
            @Override
            public void onAnimatorEnd() {
                takeDataFromQueue();
            }
        });
    }

    private void initDots() {
        if (mIsFirstTime) {
            for (int i = 0; i < 3; i++) {
                View dot = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize, mDotSize);
                dot.setLayoutParams(params);
                dot.setBackgroundResource(R.drawable.dot_trans50);
                if (i != 0) {
                    params.leftMargin = mLeftMargin;
                }
                llDots.addView(dot);
            }

            llDots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        llDots.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        llDots.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    mDotSpace = llDots.getChildAt(1).getLeft() - llDots.getChildAt(0).getLeft();
                }
            });

            mIsFirstTime = false;
        }
    }

    private void initSendGiftPager() {
        initDots();
        mPagerAdapter = new GiftPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int marginLeft = (int) (mDotSpace * positionOffset + position * mDotSpace);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewDotWhite.getLayoutParams();
                params.leftMargin = marginLeft;
                viewDotWhite.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.iv_send_gift, R.id.view_send_gift_blank, R.id.iv_switch_left, R.id.iv_avator, R.id.iv_switch_right, R.id.tv_close, R.id.tv_recharge, R.id.iv_next, R.id.tv_send_gift})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send_gift:
                llSendGift.setVisibility(View.VISIBLE);
                initSendGiftPager();
                break;

            case R.id.view_send_gift_blank:
                llSendGift.setVisibility(View.GONE);
                break;

            case R.id.iv_switch_left:
                break;
            case R.id.iv_avator:
                break;
            case R.id.iv_switch_right:
                break;

            case R.id.tv_close:
                llSendGift.setVisibility(View.GONE);
                break;

            case R.id.tv_recharge:
                break;
            case R.id.iv_next:
                break;
            case R.id.tv_send_gift:
                if (mLastPosInTotal != -1) {
                    GiftEntity giftEntity = new GiftEntity(mLastPosInTotal, "礼物名称", "");
                    playSendGiftAnimator(new SendGiftModel(1, true, giftEntity));
                } else {
                    Toast.makeText(this, "请先选择一件礼物", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(int currPager, int currPos, int posInTotal) {
        if (posInTotal == mLastPosInTotal) {
            return;
        }

        mData.get(posInTotal).setSelect(true);
        if (mPagerAdapter.getRegisteredFragment(currPager) != null) {
            ((GiftFragment) mPagerAdapter.getRegisteredFragment(currPager)).clearSelect(currPos, true);
        }

        if (mLastPager != -1) {
            mData.get(mLastPosInTotal).setSelect(false);
            if (mPagerAdapter.getRegisteredFragment(mLastPager) != null) {
                ((GiftFragment) mPagerAdapter.getRegisteredFragment(mLastPager)).clearSelect(mLastPos, false);
            }
        }

        mLastPager = currPager;
        mLastPosInTotal = posInTotal;
        mLastPos = currPos;
    }

    @Override
    public void onBackPressed() {
        if (llSendGift.getVisibility() == View.VISIBLE) {
            llSendGift.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    public List<SendGiftModel> getmData() {
        return mData;
    }

    /**
     * 播放赠送礼物动画
     *
     * @param sendGiftModel
     */
    private void playSendGiftAnimator(SendGiftModel sendGiftModel) {
        int giftId = sendGiftModel.getGiftEntity().getGiftId();
        int giftNum = sendGiftModel.getGiftNum();
        GiftLayout samePlayingLayout = checkSamePlayingLayout(giftId);

        // 当前不存在正在播放编号为mLastPosInTotal的礼物动画
        if (null == samePlayingLayout) {
            Log.e(TAG, "当前不存在正在播放编号为" + giftId + "的礼物动画");
            GiftLayout freeLayout = checkFreeLayout();

            // 当前没有空闲的位置播放礼物动画
            if (null == freeLayout) {
                Log.e(TAG, "当前没有空闲的礼物动画位置");
                // 更新队列中的内容
                synchronized (mQueue) {
                    int queueSize = mQueue.size();
                    // 若队列中最后一项中的giftId与要传入的相同，则直接giftNum加1；
                    // 否则添加一个新对象至队列中
                    if (queueSize > 0) {
                        if (mQueue.get(queueSize - 1).getGiftEntity().getGiftId() == giftId) {
                            mQueue.get(queueSize - 1).setGiftNum(mQueue.get(queueSize - 1).getGiftNum() + 1);
                            Log.e(TAG, "在原基础上增加，currNum = " + mQueue.get(queueSize - 1).getGiftNum());
                            return;
                        }
                    }
                    Log.e(TAG, "添加了一个新对象到队列中");
                    mQueue.add(new SendGiftModel(1, true, new GiftEntity(giftId, "礼物名称", "")));
                }
            } else {
                // 当前位置空闲
                Log.e(TAG, "当前位置空闲");
                // 播放礼物进入动画
                freeLayout.setmSendGiftModel(sendGiftModel);
                AnimatorSet animatorSet = freeLayout.playAnimatorSet(freeLayout.playEnterAnimator(),
                        freeLayout.playScaleTextAnimator(giftNum));
                animatorSet.start();
                freeLayout.postGiftMessageDelayed(1500);
            }

        } else {
            // 当前正在播放编号为mLastPosInTotal的礼物动画
            Log.e(TAG, "当前正在播放编号为" + giftId + "的礼物动画");
            // 播放改变礼物数量动画
            samePlayingLayout.getmSendGiftModel().getGiftEntity().setGiftId(giftId);
            samePlayingLayout.getmSendGiftModel().setGiftNum(samePlayingLayout.getmSendGiftModel().getGiftNum() + 1);
            samePlayingLayout.removeGiftCallback();
            ObjectAnimator scaleAnimator = samePlayingLayout.playScaleTextAnimator(samePlayingLayout.getmSendGiftModel().getGiftNum());
            scaleAnimator.start();
            samePlayingLayout.postGiftMessageDelayed(1000);
        }
    }

    /**
     * 检查是否有正在播放giftId动画的位置，若有则返回该GiftLayout；否则返回null
     *
     * @param giftId
     * @return
     */
    private GiftLayout checkSamePlayingLayout(int giftId) {
        for (GiftLayout model : mGiftLayoutList) {
            if (model.ismIsShow() && giftId == model.getmSendGiftModel().getGiftEntity().getGiftId()) {
                return model;
            }
        }
        return null;
    }

    /**
     * 检查是否有空闲的位置用来播放礼物动画，若有则返回该GiftLayout;否则返回null
     *
     * @return
     */
    private GiftLayout checkFreeLayout() {
        for (GiftLayout model : mGiftLayoutList) {
            if (!model.ismIsShow()) {
                return model;
            }
        }
        return null;
    }

    /**
     * 若队列中存在数据，则从队列中取出第一条数据
     */
    private void takeDataFromQueue() {
        synchronized (mQueue) {
            if (mQueue.size() > 0) {
                Log.e(TAG, "从队列中取出第一组数据");
                playSendGiftAnimator(mQueue.get(0));
                mQueue.remove(0);
            }
        }
    }
}
