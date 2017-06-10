package com.cyjthink.customviews.line_chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cyjthink.customviews.R;
import com.cyjthink.customviews.utils.DensityUtil;

/**
 * Created by cyj on 2017/6/10.
 * 折线图
 */

public class LineChartView extends View {

    /**
     * 折线图一半高度能表示的最大值
     */
    private int mMaxValue;
    /**
     * 点之间的距离
     */
    private int mPointGap;
    /**
     * 点所代表的值
     */
    private int[] mPointValues;

    private int mRoundRectColor;
    private int mPointColor;
    private int mLineColor;
    private int mValueTextColor;

    private int mDefaultWidth;
    private int mDefaultHeight;

    private float mRoundRectRadius;
    private float mPointRadius;
    private float mLineWidth;
    private float mValueTextSize;

    private Path mLinePath;

    private Paint mRoundRectPaint;
    private Paint mPointPaint;
    private Paint mLinePaint;
    private Paint mValuePaint;

    public LineChartView(Context context) {
        super(context);
        init();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        mMaxValue = a.getInt(R.styleable.LineChartView_max_value, 1000);
        mRoundRectColor = a.getColor(R.styleable.LineChartView_round_rect_bg, Color.WHITE);
        mRoundRectRadius = a.getDimension(R.styleable.LineChartView_round_rect_radius, DensityUtil.dp2px(getContext(), 5f));
        mPointColor = a.getColor(R.styleable.LineChartView_point_color, Color.BLACK);
        mPointRadius = a.getDimension(R.styleable.LineChartView_point_radius, DensityUtil.dp2px(getContext(), 5f));
        mLineColor = a.getColor(R.styleable.LineChartView_line_color, Color.BLACK);
        mLineWidth = a.getDimension(R.styleable.LineChartView_line_width, DensityUtil.dp2px(getContext(), 1f));
        mValueTextColor = a.getColor(R.styleable.LineChartView_value_text_color, Color.BLACK);
        mValueTextSize = a.getDimension(R.styleable.LineChartView_value_size, DensityUtil.sp2px(getContext(), 12f));
        a.recycle();
        init();
    }

    private void init() {
        mDefaultWidth = DensityUtil.dp2px(getContext(), 350f);
        mDefaultHeight = DensityUtil.dp2px(getContext(), 200f);

        mLinePath = new Path();

        mRoundRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundRectPaint.setStyle(Paint.Style.FILL);
        mRoundRectPaint.setColor(mRoundRectColor);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(mPointColor);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineWidth);

        mValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mValuePaint.setStyle(Paint.Style.FILL);
        mValuePaint.setColor(mValueTextColor);
        mValuePaint.setTextSize(mValueTextSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPointGap = w / 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw round rect
        RectF roundRect = new RectF(0, 0, mDefaultWidth, mDefaultHeight);
        canvas.drawRoundRect(roundRect, mRoundRectRadius, mRoundRectRadius, mRoundRectPaint);

        //points length must be 4
        if (mPointValues.length == 4) {
            for (int i = 0; i < 4; i++) {
                //draw points
                float cy = getHeight() - getHeight() / 2 * mPointValues[i] / mMaxValue;
                canvas.drawCircle(mPointGap * (i + 1), cy, mPointRadius, mPointPaint);

                //draw lines
                if (i == 0) {
                    mLinePath.moveTo(mPointGap * i, cy);
                }
                mLinePath.lineTo(mPointGap * (i + 1), cy);
                canvas.drawPath(mLinePath, mLinePaint);
                mLinePath.moveTo(mPointGap * (i + 1), cy);

                //draw values text
                float tx = mPointGap * (i + 1) - DensityUtil.getTextWidth("" + mPointValues[i], mValuePaint) / 2;
                float ty = cy + mPointRadius + DensityUtil.getTextHeight("" + mPointValues[i], mValuePaint) + DensityUtil.dp2px(getContext(), 5f);
                canvas.drawText("" + mPointValues[i], tx, ty, mValuePaint);
            }
        }
    }

    public void setmPointValues(int[] mPointValues) {
        this.mPointValues = mPointValues;
        postInvalidate();
    }
}
