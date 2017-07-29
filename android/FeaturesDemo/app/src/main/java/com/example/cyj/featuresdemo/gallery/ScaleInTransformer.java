package com.example.cyj.featuresdemo.gallery;


import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by cyj on 17-7-16.
 */

public class ScaleInTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        float scale = 0.5f;
        float scaleValue = Math.abs(Math.abs(position) - 1) * scale + scale;
        scaleValue = position < -1 || position > 1 ? scale : scaleValue;
        view.setScaleX(scaleValue);
        view.setScaleY(scaleValue);
        view.setAlpha(scaleValue);

        /**
         * position = -1: PivotX 为 width
         * position =  0: PivotX 为 width / 2
         * position =  1: PivotX 为 0
         * 以此为基准做调整
         */
        final float offset = Build.VERSION.SDK_INT > 19 ? (Math.abs(position) / position) * 0.5f : 0f;
        view.setPivotX(view.getWidth() * (1 - position - offset) * scale);
        ViewCompat.setElevation(view, position >= -0.25 && position <= 0.25 ? 1 : 0);
    }
}
