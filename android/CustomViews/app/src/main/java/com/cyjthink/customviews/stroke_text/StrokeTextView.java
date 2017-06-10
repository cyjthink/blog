package com.cyjthink.customviews.stroke_text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cyjthink.customviews.R;

/**
 * Created by cyj on 2017/4/16.
 * 字体描边
 */

public class StrokeTextView extends TextView {

    //描边颜色
    private int mStrokeColor;
    //填充颜色
    private int mFillColor;
    //描边宽度
    private float mStrokeWidth;
    //默认进行描边操作
    private boolean mDrawSideLine = true;

    private TextPaint mTextPaint;

    public StrokeTextView(Context context) {
        super(context);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        mStrokeColor = a.getColor(R.styleable.StrokeTextView_stroke_color, Color.WHITE);
        mFillColor = a.getColor(R.styleable.StrokeTextView_fill_color, Color.WHITE);
        mStrokeWidth = a.getDimension(R.styleable.StrokeTextView_stroke_width, 1f);
        a.recycle();
        init();
    }

    private void init(){
        mTextPaint = new TextPaint();
        mTextPaint = getPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawSideLine){
            this.setTextColor(mStrokeColor);
            mTextPaint.setStyle(Paint.Style.STROKE);
            mTextPaint.setStrokeWidth(mStrokeWidth);
            super.onDraw(canvas);
            this.setTextColor(mFillColor);
            mTextPaint.setStyle(Paint.Style.FILL);
        }
        super.onDraw(canvas);
    }

    /**
     * 设置是否描边
     * @param mDrawSideLine
     */
    public void setDrawSideLine(boolean mDrawSideLine){
        this.mDrawSideLine = mDrawSideLine;
        postInvalidate();
    }

    /**
     * 设置描边、填充颜色
     * @param mStrokeColor
     * @param mFillColor
     */
    public void setTextColor(int mStrokeColor, int mFillColor){
        this.mStrokeColor = mStrokeColor;
        this.mFillColor = mFillColor;
    }
}