package com.example.cyj.featuresdemo.gift.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
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
import com.example.cyj.featuresdemo.gift.view.AdjustableViewPager;

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
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.fl_gift)
    FrameLayout flGift;

    private int mDotSpace;
    private int mDotSize;
    private int mLeftMargin;

    private int mLastPager = -1;
    private int mLastPosInTotal = -1;
    private int mLastPos = -1;

    private boolean mIsFirstTime = true;

    private GiftPagerAdapter mPagerAdapter;

    private List<GiftEntity> mData = DataServer.getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gift);
        ButterKnife.bind(this);

        initValue();
    }

    private void initValue() {
        mDotSize = DensityUtil.dp2px(this, 6f);
        mLeftMargin = DensityUtil.dp2px(this, 10f);
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
//                Toast.makeText(this, "pos =" + mLastPosInTotal, Toast.LENGTH_SHORT).show();
                flGift.setVisibility(View.VISIBLE);
                enterInAnimator(flGift, 1000);
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

    public List<GiftEntity> getmData() {
        return mData;
    }

    private void enterInAnimator(View target, long duration){
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "translationX", -target.getWidth(), 0);
        animator.setDuration(duration);
    }

    private void scaleTextAnimator(View target){
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1.7f, 0.8f, 1.0f);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1.7f, 0.8f, 1.0f);
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f, 1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, scaleXHolder, scaleYHolder, alphaHolder);
    }

    private void exitAnimator(){

    }
}
