package com.example.cyj.featuresdemo.gift.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.cyj.featuresdemo.Constants;
import com.example.cyj.featuresdemo.gift.entity.GiftEntity;
import com.example.cyj.featuresdemo.gift.ui.GiftFragment;
import com.example.cyj.featuresdemo.gift.ui.SendGiftActivity;

import java.util.List;


/**
 * Created by cyj on 2017/6/2.
 */

public class GiftPagerAdapter extends FragmentStatePagerAdapter {

    private List<GiftEntity> mData;
    private SparseArray<Fragment> mRegisteredFragments = new SparseArray<Fragment>();

    public GiftPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        mData = ((SendGiftActivity) activity).getmData();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("currPager", position);
        Fragment fragment = new GiftFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        } else {
            if (mData.size() % Constants.ITEM_COUNT == 0) {
                return mData.size() / Constants.ITEM_COUNT;
            } else {
                return mData.size() / Constants.ITEM_COUNT + 1;
            }
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return mRegisteredFragments.get(position);
    }
}
