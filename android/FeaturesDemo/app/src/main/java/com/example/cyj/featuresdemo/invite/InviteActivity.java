package com.example.cyj.featuresdemo.invite;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.cyj.featuresdemo.Constants;
import com.example.cyj.featuresdemo.R;
import com.example.cyj.featuresdemo.common.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenyujie on 2017/9/29.
 */

public class InviteActivity extends AppCompatActivity {

    @BindView(R.id.btn_click)
    Button btnClick;

    private InviteMessageReceiver mMessageReceiver;

    protected InvitesLayout mInvitesLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);

        mInvitesLayout = new InvitesLayout(this);
        mMessageReceiver = new InviteMessageReceiver(this);
    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setAction(Constants.INVITE_MESSAGE_BROADCAST);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter inviteFilter = new IntentFilter(Constants.INVITE_MESSAGE_BROADCAST);
        registerReceiver(mMessageReceiver, inviteFilter);

        // SharedPreferences中会一直存在addpos\removepos两个位置
        if (InvitePreferencesUtils.getCount(this) > 2) {
            addInviteLayoutToParentGroup();

            mInvitesLayout.startLoop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mMessageReceiver);

        mInvitesLayout.closeLayout();
        removeInviteLayoutFromParentGroup();
    }

    /**
     * 将InviteLayout添加到容器中
     */
    public void addInviteLayoutToParentGroup() {
        if (!mInvitesLayout.ismIsVisiable()) {
            mInvitesLayout.setmIsVisiable(true);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            params.setMargins(0, 0, 0, DensityUtil.dp2px(this, 100f));
            ((FrameLayout) this.findViewById(android.R.id.content)).addView(mInvitesLayout, params);
        }
    }

    /**
     * 将InviteLayout从容器中移除
     */
    public void removeInviteLayoutFromParentGroup() {
        if (mInvitesLayout.getParent() != null) {
            ((FrameLayout) this.findViewById(android.R.id.content)).removeView(mInvitesLayout);
            mInvitesLayout.setmIsVisiable(false);
        }
    }

    public InvitesLayout getmInvitesLayout() {
        return mInvitesLayout;
    }
}
