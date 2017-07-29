package com.example.cyj.featuresdemo.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyj.featuresdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cyj on 17-7-16.
 */

public class GalleryFragment extends Fragment {

    @BindView(R.id.view)
    View view;

    private int mFlag;
    private int mBgColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);

        initValue();
        initView();
        return view;
    }

    private void initValue() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFlag = bundle.getInt("flag");
        }
    }

    private void initView() {
        switch (mFlag) {
            case 0:
                mBgColor = ContextCompat.getColor(getContext(), R.color.chrome_red);
                break;
            case 1:
                mBgColor = ContextCompat.getColor(getContext(), R.color.chrome_blue);
                break;
            case 2:
                mBgColor = ContextCompat.getColor(getContext(), R.color.chrome_green);
                break;
            case 3:
                mBgColor = ContextCompat.getColor(getContext(), R.color.chrome_yellow);
                break;
        }
        view.setBackgroundColor(mBgColor);
    }
}
