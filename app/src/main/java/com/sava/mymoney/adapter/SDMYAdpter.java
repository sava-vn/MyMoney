package com.sava.mymoney.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.SDay;
import com.sava.mymoney.model.SDate;
import com.sava.mymoney.model.SDMY;

import java.util.ArrayList;
import java.util.Calendar;

public class SDMYAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<SDMY> mList;
    private ItemClickListener mItemClickListener;
    private Calendar calendar;
    private int[] dayOfweek ={R.drawable.ic_sun,R.drawable.ic_mon,R.drawable.ic_tue,R.drawable.ic_wed,R.drawable.ic_thur,R.drawable.ic_fri,R.drawable.ic_sat};

    public SDMYAdpter(Context mContext, ArrayList<SDMY> mList, ItemClickListener mItemClickListener) {
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
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.item_detail, parent, false);
        ThoiGianViewHolder thoiGianViewHolder = new ThoiGianViewHolder(group);
        return thoiGianViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SDMY sDMY = mList.get(position);
        if (holder instanceof HeadeViewHolder) {
            HeadeViewHolder headeViewHolder = (HeadeViewHolder) holder;
            if(sDMY instanceof SDay)
                headeViewHolder.tvHeader.setText(mList.get(position).getmSDate().showMonth());
            else
                headeViewHolder.tvHeader.setText(mList.get(position).getmSDate().getmYear()+"");
        } else {
            ThoiGianViewHolder itemV = (ThoiGianViewHolder) holder;
            SDate sDate = sDMY.getmSDate();
            if(sDMY instanceof SDay)
                itemV.imgDate.setImageResource(dayOfweek[sDate.getmDoW()-1]);
            else
                itemV.imgDate.setImageResource(R.drawable.ics_time);
            itemV.imgDate.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition));
            itemV.tvDate.setText(sDMY.toString());
            itemV.tvDate.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition));
            itemV.tvCoutPay.setText(sDMY.getmCountPay()+"");
            itemV.tvMoneyIn.setText(MySupport.converToMoney(sDMY.getmMoneyIn()));
            itemV.tvMoneyOut.setText(MySupport.converToMoney(sDMY.getmMoneyOut()));
            itemV.tvBlance.setText(MySupport.converToMoney(sDMY.getmBalance()));
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
        }
    }

    public class ThoiGianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgDate;
        private TextView tvCoutPay, tvMoneyIn, tvBlance, tvMoneyOut,tvDate;
        private LinearLayout clickLayout;

        public ThoiGianViewHolder(View itemView) {
            super(itemView);
            clickLayout = itemView.findViewById(R.id.click_layout);
            clickLayout.setOnClickListener(this);
            imgDate = itemView.findViewById(R.id.item_time_img_date);
            tvDate = itemView.findViewById(R.id.item_time_tv_date);
            tvCoutPay = itemView.findViewById(R.id.item_time_tv_countPay);
            tvMoneyIn = itemView.findViewById(R.id.item_time_tv_moneyIn);
            tvMoneyOut = itemView.findViewById(R.id.item_time_tv_moneyOut);
            tvBlance = itemView.findViewById(R.id.item_time_tv_blance);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
