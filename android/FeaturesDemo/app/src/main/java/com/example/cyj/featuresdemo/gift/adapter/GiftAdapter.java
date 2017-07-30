package com.example.cyj.featuresdemo.gift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cyj.featuresdemo.R;
import com.example.cyj.featuresdemo.gift.entity.GiftEntity;
import com.example.cyj.featuresdemo.gift.entity.SendGiftModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cyj on 2017/6/2.
 */

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder> {

    private List<SendGiftModel> mSendGiftModelList;
    private LayoutInflater mLayoutInflater;

    public GiftAdapter(Context context, List<SendGiftModel> giftEntityList) {
        this.mSendGiftModelList = giftEntityList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_gift, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mSendGiftModelList.get(position).isSelect()) {
            holder.ivSelectFlag.setVisibility(View.VISIBLE);
        } else {
            holder.ivSelectFlag.setVisibility(View.INVISIBLE);
        }
        holder.llGiveGift.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mSendGiftModelList == null ? 0 : mSendGiftModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_select_flag)
        ImageView ivSelectFlag;
        @BindView(R.id.iv_gift)
        ImageView ivGift;
        @BindView(R.id.tv_value_num)
        TextView tvValueNum;
        @BindView(R.id.ll_value_num)
        LinearLayout llValueNum;
        @BindView(R.id.tv_charm_num)
        TextView tvCharmNum;
        @BindView(R.id.ll_give_gift)
        LinearLayout llGiveGift;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_give_gift)
        public void onClick(View view) {
            mOnClickListener.onClick(view, (int) view.getTag());
        }
    }

    private onClickListener mOnClickListener;

    public interface onClickListener {
        void onClick(View view, int position);
    }

    public void setOnClickListener(onClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }
}
