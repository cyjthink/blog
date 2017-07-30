package com.example.cyj.featuresdemo.gift.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyj.featuresdemo.R;
import com.example.cyj.featuresdemo.Constants;
import com.example.cyj.featuresdemo.gift.adapter.GiftAdapter;
import com.example.cyj.featuresdemo.gift.entity.GiftEntity;
import com.example.cyj.featuresdemo.gift.entity.SendGiftModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.GridLayout.HORIZONTAL;
import static android.widget.GridLayout.VERTICAL;

/**
 * Created by cyj on 2017/6/2.
 */

public class GiftFragment extends Fragment {

    @BindView(R.id.rec_gift)
    RecyclerView recGift;

    private int mCurrPage;

    private List<SendGiftModel> mSendGiftModelList = new ArrayList<>();

    private GiftAdapter mAdapter;

    private GridLayoutManager mManager;

    public static final String TAG = GiftFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnItemClickListener = (onItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        ButterKnife.bind(this, view);

        initValue();
        initView();
        return view;
    }

    private void initValue() {
        if (getArguments() != null) {
            mCurrPage = getArguments().getInt("currPager");
            pickData(mCurrPage);
        }
    }

    private void initView() {
        mAdapter = new GiftAdapter(getContext(), mSendGiftModelList);
        mManager = new GridLayoutManager(getContext(), 4);
        recGift.setAdapter(mAdapter);
        recGift.setLayoutManager(mManager);
        recGift.addItemDecoration(new DividerItemDecoration(recGift.getContext(), VERTICAL));
        recGift.addItemDecoration(new DividerItemDecoration(recGift.getContext(), HORIZONTAL));
        mAdapter.setOnClickListener(new GiftAdapter.onClickListener() {
            @Override
            public void onClick(View view, int position) {
                int pos = (int) view.getTag();
                mOnItemClickListener.onItemClick(mCurrPage, pos, (mCurrPage) * Constants.ITEM_COUNT + pos);
            }
        });
    }

    public void clearSelect(int pos, boolean isSelecte) {
        mSendGiftModelList.get(pos).setSelect(isSelecte);
        mAdapter.notifyDataSetChanged();
    }

    private void pickData(int position) {
        int last;
        List<SendGiftModel> data = ((SendGiftActivity) getActivity()).getmData();

        if (data.size() > (position + 1) * Constants.ITEM_COUNT) {
            last = (position + 1) * Constants.ITEM_COUNT;
        } else {
            last = data.size();
        }
        for (int i = position * Constants.ITEM_COUNT; i < last; i++) {
            mSendGiftModelList.add(data.get(i));
        }
    }

    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener {
        void onItemClick(int currPager, int currPos, int posInTotal);
    }
}
