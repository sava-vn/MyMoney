package com.sava.mymoney.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.TimePayment;

import java.util.ArrayList;

public class TimePaymentAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<TimePayment> mList;
    private ItemClickListener mItemClickListener;

    public TimePaymentAdpter(Context mContext, ArrayList<TimePayment> mList, ItemClickListener mItemClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == MyValues.HEADER) {
            ViewGroup group = (ViewGroup) inflater.inflate(R.layout.item_header, parent, false);
            HeadeViewHolder headeViewHolder = new HeadeViewHolder(group);
            return headeViewHolder;
        }
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.item_rcv_day, parent, false);
        ThoiGianViewHolder thoiGianViewHolder = new ThoiGianViewHolder(group);
        return thoiGianViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadeViewHolder) {
            HeadeViewHolder headeViewHolder = (HeadeViewHolder) holder;
            headeViewHolder.tvHeader.setText(mList.get(position).getmNote());
        } else {
            ThoiGianViewHolder thoiGianViewHolder = (ThoiGianViewHolder) holder;
            thoiGianViewHolder.tvDay.setText(mList.get(position).toString());
            thoiGianViewHolder.tvMoney1.setText(MySupport.converToMoney(mList.get(position).getmVi()));
            thoiGianViewHolder.tvMoney2.setText(MySupport.converToMoney(mList.get(position).getmMoney()));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getmViewType();
    }

    public class HeadeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHeader;

        public HeadeViewHolder(View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_header);
            MySupport.setFontBold(mContext,tvHeader, MyValues.FONT_AGENCY);
        }
    }

    public class ThoiGianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvDay;
        private TextView tvMoney1;
        private TextView tvMoney2;
        private LinearLayout clickLayout;

        public ThoiGianViewHolder(View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvMoney1 = itemView.findViewById(R.id.tv_money1);
            tvMoney2 = itemView.findViewById(R.id.tv_money2);
            clickLayout = itemView.findViewById(R.id.click_layout);
            MySupport.setFontBold(mContext,tvMoney1,MyValues.FONT_AGENCY);
            MySupport.setFontRegular(mContext,tvMoney2,MyValues.FONT_AGENCY);
            MySupport.setFontRegular(mContext,tvDay,MyValues.FONT_AGENCY);
            clickLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
