package com.example.cyj.featuresdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cyj.featuresdemo.broadcast.BroadcastActivity;
import com.example.cyj.featuresdemo.gallery.GalleryActivity;
import com.example.cyj.featuresdemo.gift.ui.SendGiftActivity;
import com.example.cyj.featuresdemo.invite.InviteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_receive_invite)
    Button btnReceiveInvite;
    @BindView(R.id.btn_send_gift)
    Button btnSendGift;
    @BindView(R.id.btn_gallery)
    Button btnGallery;
    @BindView(R.id.btn_broadcast)
    Button btnBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_send_gift, R.id.btn_gallery, R.id.btn_broadcast, R.id.btn_receive_invite})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_send_gift:
                intent = new Intent(this, SendGiftActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_gallery:
                intent = new Intent(this, GalleryActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_broadcast:
                intent = new Intent(this, BroadcastActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_receive_invite:
                intent = new Intent(this, InviteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
