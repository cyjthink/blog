package com.example.cyj.featuresdemo.broadcast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.cyj.featuresdemo.DataServer;
import com.example.cyj.featuresdemo.common.DensityUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cyj on 2017/8/10.
 */

// 自定义surfaceview必须继承surfaceview且实现surfaceholder.callback
public class BroadcastSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    /**
     * 广播滚动速度
     */
    private int mSpeed = 5;

    /**
     * 控件的宽高
     */
    private float mViewWidth;
    private float mViewHeight;

    /**
     * 最近一项的位置
     */
    private float mLastTargetPos;

    /**
     * 是否为第一次初始化
     */
    private boolean mIsInit = true;
    /**
     * 线程是否正在运行
     */
    private boolean mIsRunning;

    /**
     * 最近一项的内容
     */
    private String mLastTargetContent;

    /**
     * 正在播放的广播列表
     */
    private List<BroadcastEntity> mPlayingList;
    /**
     * 存放广播的队列
     */
    private List<BroadcastEntity> mBroadcastEntityList;

    private Thread mThread;

    private Paint mPaint;

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private onBroadcastListener mOnBroadcastListener;

    public static final int START = 0;
    public static final int NEXT_START = 1;
    public static final int FINISH = 2;
    public static final String TAG = BroadcastSurfaceView.class.getSimpleName();

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEXT_START:
                    if (mOnBroadcastListener != null) {
                        mOnBroadcastListener.onNextStart();
                    }
                    break;
                case FINISH:
                    if (mOnBroadcastListener != null) {
                        mOnBroadcastListener.onFinish();
                    }
                    break;
            }
        }
    };

    public BroadcastSurfaceView(Context context) {
        super(context);
        init();
    }

    public BroadcastSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BroadcastSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mIsRunning = true;

        mPlayingList = new ArrayList<>();
        mBroadcastEntityList = DataServer.getBroadcastEntityList();

        mHolder = getHolder();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(DensityUtil.sp2px(getContext(), 14f));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mThread != null) {
            mIsRunning = false;
            mThread.interrupt();
            mThread = null;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void run() {
        while (mIsRunning) {
            draw();
        }
    }

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.BLACK);

        if (mIsInit) {
            checkBroadcastEntityList();
            mIsInit = false;
        }

        Iterator<BroadcastEntity> it = mPlayingList.iterator();
        while (it.hasNext()) {
            BroadcastEntity entity = it.next();
            float currPos = entity.getCurrPos();
            String content = entity.getContent();
            mCanvas.drawText(content, currPos -= mSpeed, mViewHeight / 2, mPaint);
            entity.setCurrPos(currPos);

            // 当播放列表中的第一项位置移出屏幕时
            if (entity.getCurrPos() <= -DensityUtil.getTextWidth(entity.getContent(), mPaint)) {
                finishPlayBroadcast(it);
                mHandler.sendEmptyMessage(FINISH);
                Log.e(TAG, "广播播放结束");
            }
            if (!it.hasNext()) {
                mLastTargetPos = currPos;
            }
        }

        // 当最近一条广播的位置移动了1/4时，发送message并启动下一条
        if (mLastTargetPos < mViewWidth * 0.75f) {
            checkBroadcastEntityList();
            mHandler.sendEmptyMessage(NEXT_START);
            Log.e(TAG, "开始播放下一条广播");
        }

        mHolder.unlockCanvasAndPost(mCanvas);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断广播队列是否为空，是：则播放默认文案；否：则播放广播
     */
    private void checkBroadcastEntityList(){
        if (mBroadcastEntityList.size() != 0) {
            // 播放广播
            initBeforePlayBroadcast(mBroadcastEntityList.get(0));
            synchronized (mBroadcastEntityList) {
                mBroadcastEntityList.remove(0);
            }
        } else {
            // 播放默认广播文案
            BroadcastEntity entity = new BroadcastEntity(0, "", "默认广播文案");
            initBeforePlayBroadcast(entity);
        }
    }

    /**
     * 在广播开始播放前初始化数据(包括下载头像)并将对象添加到播放队列中
     * @param entity
     */
    private void initBeforePlayBroadcast(BroadcastEntity entity) {
        entity.setCurrPos(mViewWidth);
        mLastTargetPos = mViewWidth;
        mLastTargetContent = entity.getContent();
        synchronized (mPlayingList) {
            mPlayingList.add(entity);
        }
    }

    /**
     * 广播播放完毕后从播放列表移除
     */
    private void finishPlayBroadcast(Iterator<BroadcastEntity> it){
        synchronized (it) {
            it.remove();
        }
    }

    public void startScroll() {
        if (mThread != null && mIsRunning == true) {
            return;
        }
        mThread = new Thread(this);
        mThread.start();
    }

    public void stopScroll() {
        if (mThread == null) {
            return;
        }
        mIsRunning = false;
        mThread.interrupt();
        mThread = null;
    }

    public void addBroadcastToList(BroadcastEntity entity){
        synchronized (mBroadcastEntityList) {
            mBroadcastEntityList.add(entity);
        }
    }

    public void setmOnBroadcastListener(onBroadcastListener mOnBroadcastListener) {
        this.mOnBroadcastListener = mOnBroadcastListener;
    }
}
