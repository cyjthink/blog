package com.cyjthink.customviews.dot_line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cyjthink.customviews.R;
import com.cyjthink.customviews.utils.DensityUtil;

/**
 * Created by cyj on 2017/6/10.
 * 虚线
 */

public class DotLineView extends View {

    private int mLineColor;

    private float mLineWidth;

    private Paint mLinePaint;

    public DotLineView(Context context) {
        super(context);
        init();
    }

    public DotLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DotLineView);
        mLineWidth = a.getDimension(R.styleable.DotLineView_dot_line_width, DensityUtil.dp2px(getContext(), 1f));
        mLineColor = a.getColor(R.styleable.DotLineView_dot_line_color, Color.GRAY);
        a.recycle();
        init();
    }

    private void init() {
        /**
         * 1. float数组长度必须为偶数且 >= 2
         * 2. 表示绘制多少长度的实线后绘制多少长度的虚线，依次重复。1为起始位置的偏移量
         */
        PathEffect pathEffect = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setPathEffect(pathEffect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(getWidth(), getHeight());
        canvas.drawPath(path, mLinePaint);
    }
}
