package com.example.cyj.minesweep;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cyj on 2017/8/25.
 */

public class MineAdapter extends RecyclerView.Adapter<MineAdapter.MineViewHolder> {

    private int mItemWidth;

    private Context mContext;

    private List<MineEntity> mData;

    private onClickListener mOnClickListener;

    private LayoutInflater mLayoutInflater;

    public MineAdapter(Context context, List<MineEntity> mData, int itemWidth) {
        this.mContext = context;
        this.mData = mData;
        this.mItemWidth = itemWidth;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineViewHolder(mLayoutInflater.inflate(R.layout.item_mine, parent, false));
    }

    @Override
    public void onBindViewHolder(MineViewHolder holder, int position) {
        holder.llMine.getLayoutParams().height = mItemWidth;
        holder.llMine.getLayoutParams().width = mItemWidth;

        MineEntity entity = mData.get(position);
        switch (entity.getStatus()) {
            case Status.MARK_INITIAL:
                // 还未显示，最初始状态
                holder.llMine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mine_bg_inital));
                holder.ivMark.setVisibility(View.GONE);
                holder.tvMark.setVisibility(View.GONE);
                break;
            case Status.MARK_NO_MINE:
                // 没有地雷，显示为空
                holder.llMine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mine_bg_mark));
                holder.ivMark.setVisibility(View.GONE);
                holder.tvMark.setVisibility(View.GONE);
                break;
            case Status.MARK_NUM_MINE:
                // 没有地雷，显示为数字
                holder.llMine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mine_bg_mark));
                holder.ivMark.setVisibility(View.GONE);
                holder.tvMark.setVisibility(View.VISIBLE);
                holder.tvMark.setText(entity.getMine_num() + "");
                break;
            case Status.MARK_EXIST_MINE:
                // 存在地雷，显示为地雷图标
                holder.llMine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mine_bg_mark));
                holder.ivMark.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(R.drawable.mine_kulou).into(holder.ivMark);
                holder.tvMark.setVisibility(View.GONE);
                break;
            case Status.CLEAR_MINE_SAFE:
                // 此处没有雷

                break;
            case Status.CLEAR_MINE_SUCCESS:
                // 成功清理掉雷

                break;
            case Status.CLEAR_MINE_FAIL:
                // 踩到雷，清理失败

                break;
        }
        holder.llMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class MineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_mark)
        ImageView ivMark;
        @BindView(R.id.tv_mark)
        TextView tvMark;
        @BindView(R.id.ll_mine)
        LinearLayout llMine;

        public MineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onClickListener{
        void onClick();
    }

    public void setmOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
