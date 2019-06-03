package com.sava.mymoney.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;

public class DetailTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Payment> mListPayment;

    public DetailTypeAdapter(Context context, ArrayList<Payment> listPayment) {
        mContext = context;
        mListPayment = listPayment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup viewGroup;
        if (viewType != 0) {
            viewGroup = (ViewGroup) inflater.inflate(R.layout.item_detail_payment, parent, false);
            ViewItem viewItem = new ViewItem(viewGroup);
            return viewItem;
        }
        viewGroup = (ViewGroup) inflater.inflate(R.layout.item_header, parent, false);
        return new ViewHeader(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Payment payment = mListPayment.get(position);
        if (payment.getmMoney() != 0) {
            ViewItem viewItem = (ViewItem) holder;
            viewItem.tvMoney.setText(MySupport.converToMoney(payment.getmMoney()));
            if (payment.getmNote().equals(""))
                viewItem.tvNote.setText("Không có ghi chú");
            else
                viewItem.tvNote.setText(payment.getmNote());
        }else {
            ViewHeader viewHeader = (ViewHeader) holder;
            viewHeader.tvDate.setText(payment.getmNote());
        }
    }

    @Override
    public int getItemCount() {
        return mListPayment.size();
    }

    public class ViewItem extends RecyclerView.ViewHolder {
        private TextView tvNote, tvMoney;

        public ViewItem(View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.item_detail_payment_tvNote);
            tvMoney = itemView.findViewById(R.id.item_detail_payment_tvMoney);
        }
    }

    public class ViewHeader extends RecyclerView.ViewHolder {
        private TextView tvDate;

        public ViewHeader(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_header);
            tvDate.setTextColor(mContext.getColor(R.color.yellow));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mListPayment.get(position).getmMoney();
    }
}
