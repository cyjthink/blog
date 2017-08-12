package com.cyjthink.customviews.radar_scan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cyj on 2017/8/1.
 */

public class RadarScanView extends View {

    private int mStart;
    private int mViewSize = 900;
    private int mCenterX, mCenterY;

    // 坐标系画笔
    private Paint mCoordinatePaint;
    // 雷达画笔
    private Paint mRadarPaint;

    private Matrix mMatrix;

    private Handler mHandler = new Handler();

    private Runnable mScanRunnable = new Runnable() {
        @Override
        public void run() {
            mStart -= 10;
            mMatrix = new Matrix();
            mMatrix.postRotate(mStart, mCenterX, mCenterY);
            postInvalidate();
            mHandler.postDelayed(mScanRunnable, 100);
        }
    };

    public RadarScanView(Context context) {
        super(context);
        init();
    }

    public RadarScanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mCenterX = mCenterY = mViewSize / 2;
        mMatrix = new Matrix();

        setBackgroundColor(Color.TRANSPARENT);
        mCoordinatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCoordinatePaint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        mCoordinatePaint.setStyle(Paint.Style.STROKE);
        mCoordinatePaint.setStrokeWidth(10);

        mRadarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadarPaint.setColor(Color.parseColor("#99a2a2a2"));

        mHandler.post(mScanRunnable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mViewSize, mViewSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制背景的两条白线
        canvas.drawLine(0, mViewSize / 2, mViewSize, mViewSize / 2, mCoordinatePaint);
        canvas.drawLine(mViewSize / 2, 0, mViewSize / 2, mViewSize, mCoordinatePaint);
        // 绘制背景的圆圈
        canvas.drawCircle(mCenterX, mCenterY, 150, mCoordinatePaint);
        canvas.drawCircle(mCenterX, mCenterY, 300, mCoordinatePaint);
        canvas.drawCircle(mCenterX, mCenterY, 445, mCoordinatePaint);
        // 绘制扇形
        Shader shader = new SweepGradient(mCenterX, mCenterY, Color.TRANSPARENT, Color.GREEN);
        mRadarPaint.setShader(shader);
        canvas.concat(mMatrix);
        canvas.drawCircle(mCenterX, mCenterY, 450, mRadarPaint);
    }
}
