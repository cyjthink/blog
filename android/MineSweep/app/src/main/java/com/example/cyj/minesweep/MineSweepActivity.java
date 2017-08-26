package com.example.cyj.minesweep;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cyj on 2017/8/25.
 */

public class MineSweepActivity extends AppCompatActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_mine_num)
    TextView tvMineNum;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.rec_mine)
    RecyclerView recMine;

    // 每轮时间
    private int mRoundTime = 20;
    private int mItemWidth;

    private List<MineEntity> mData;

    private MineAdapter mAdapter;

    private Handler mRoundCount = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mRoundTime > 0) {
                mRoundTime --;
                mRoundCount.sendEmptyMessageDelayed(0, 1000);
            }
            tvTime.setText(mRoundTime + "");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_sweep);
        ButterKnife.bind(this);

        initValue();
        initView();
        mRoundCount.sendEmptyMessageDelayed(0, 1000);
    }

    private void initValue() {
        mItemWidth = (DensityUtil.getWindowWidth(this) - DensityUtil.dp2px(this, 45)) / 8;
        mData = DataServer.getData();
    }

    private void initView() {
        tvTime.setText(mRoundTime + "");
        tvMineNum.setText("15");
        tvScore.setText("0");

        recMine.setLayoutManager(new GridLayoutManager(this, 8));
        mAdapter = new MineAdapter(this, mData, mItemWidth);
        recMine.setAdapter(mAdapter);
        mAdapter.setmOnClickListener(new MineAdapter.onClickListener() {
            @Override
            public void onClick() {

            }
        });
    }
}
