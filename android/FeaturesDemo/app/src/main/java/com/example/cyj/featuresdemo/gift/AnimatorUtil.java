package com.example.cyj.featuresdemo.gift;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * Created by cyj on 2017/7/29.
 */

public class AnimatorUtil {

    /**
     * 礼物进入动画
     * @param target
     * @param start
     * @param end
     * @param duration
     * @return
     */
    public static ObjectAnimator enterAnimator(View target, float start, float end, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "translationX", start, end);
        animator.setDuration(duration);
        return animator;
    }

    /**
     * 礼物退出动画
     * @param target
     * @param start
     * @param end
     * @param duration
     * @return
     */
    public static ObjectAnimator exitAnimator(View target, float start, float end, long duration, long delay) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("translationY", start, end);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, holder1, holder2);
        animator.setDuration(duration);
        animator.setStartDelay(delay);
        return animator;
    }

    /**
     * 礼物数量动画
     * @param target
     * @param duration
     * @return
     */
    public static ObjectAnimator scaleTextAnimator(View target, long duration) {
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1.7f, 0.8f, 1.0f);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1.7f, 0.8f, 1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, scaleXHolder, scaleYHolder);
        animator.setDuration(duration);
        return animator;
    }
}
