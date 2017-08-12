package com.example.cyj.featuresdemo.broadcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cyj.featuresdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cyj on 2017/8/12.
 */

public class BroadcastActivity extends AppCompatActivity {

    @BindView(R.id.sv_broadcast)
    BroadcastSurfaceView svBroadcast;
    @BindView(R.id.btn_add_broadcast)
    Button btnAddBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        ButterKnife.bind(this);

        svBroadcast.post(new Runnable() {
            @Override
            public void run() {
                svBroadcast.startScroll();
            }
        });
        initListener();
    }

    private void initListener() {
        onBroadcastListener listener = new onBroadcastListener() {
            @Override
            public void onStart(final BroadcastEntity entity) {
            }

            @Override
            public void onNextStart() {
//                Toast.makeText(BroadcastActivity.this, "开始播放下一条", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
//                Toast.makeText(BroadcastActivity.this, "播放完毕", Toast.LENGTH_SHORT).show();
            }
        };
        svBroadcast.setmOnBroadcastListener(listener);
    }

    @OnClick({R.id.btn_add_broadcast})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_broadcast:
                svBroadcast.addBroadcastToList(new BroadcastEntity(0, "", "新添加的广播"));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (svBroadcast != null) {
            svBroadcast.stopScroll();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (svBroadcast != null) {
            svBroadcast.stopScroll();
        }
    }
}
