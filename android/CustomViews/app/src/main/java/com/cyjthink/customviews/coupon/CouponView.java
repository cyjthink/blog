package com.cyjthink.customviews.coupon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.cyjthink.customviews.R;
import com.cyjthink.customviews.utils.DensityUtil;

/**
 * Created by cyj on 2017/6/10.
 * 模仿饿了么优惠券样式
 */

public class CouponView extends LinearLayout {

    private int mRectBgColor;
    private int mCircleBgColor;

    private float mRectBgRadius;
    private float mCircleRadius;

    private Paint mCirclePaint;
    private Paint mRectBgPaint;

    public CouponView(Context context) {
        super(context);
        init();
    }

    public CouponView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CouponView);
        mRectBgRadius = a.getDimension(R.styleable.CouponView_rect_bg_radius, DensityUtil.dp2px(getContext(), 5f));
        mRectBgColor = a.getColor(R.styleable.CouponView_rect_bg_color, Color.WHITE);
        mCircleRadius = a.getDimension(R.styleable.CouponView_circle_radius, DensityUtil.dp2px(getContext(), 5f));
        mCircleBgColor = a.getColor(R.styleable.CouponView_circle_bg, Color.WHITE);
        a.recycle();
        init();
    }

    private void init() {
        setWillNotDraw(false);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mCircleBgColor);

        mRectBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectBgPaint.setColor(mRectBgColor);
        mRectBgPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw round rect
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, mRectBgRadius, mRectBgRadius, mRectBgPaint);

        //draw top circle
        canvas.drawCircle(getWidth() / 3, 0, mCircleRadius, mCirclePaint);

        //draw bottom circle
        canvas.drawCircle(getWidth() / 3, getHeight(), mCircleRadius, mCirclePaint);
    }
}
