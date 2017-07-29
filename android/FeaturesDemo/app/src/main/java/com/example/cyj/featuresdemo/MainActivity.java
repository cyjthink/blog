package com.example.cyj.featuresdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cyj.featuresdemo.gallery.GalleryActivity;
import com.example.cyj.featuresdemo.gift.ui.SendGiftActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_send_gift)
    Button btnSendGift;
    @BindView(R.id.btn_gallery)
    Button btnGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_send_gift, R.id.btn_gallery})
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
        }
    }
}
