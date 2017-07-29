package com.example.cyj.featuresdemo.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cyj.featuresdemo.Constants;
import com.example.cyj.featuresdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cyj on 17-7-16.
 */

public class GalleryActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;

    private GalleryPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        initView();
        // TODO: 17-7-16 点击两边的页面可以进行切换
        // TODO: 17-7-16 添加定时切换页面
    }

    private void initView() {
        mPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
        viewPager.setPageMargin(20);

        // 不加的话会导致左右切换异常
        viewPager.setOffscreenPageLimit(Constants.PAGER_COUNT);

        // 使中间页面放大,两边页面缩小
        viewPager.setPageTransformer(true, new ScaleInTransformer());

        // 滑动两边的页面使viewpager进行滑动
        llBg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        viewPager.setAdapter(mPagerAdapter);
    }
}
