package com.sava.mymoney.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;
import java.util.Calendar;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Payment> mList;
    private int[] dayOfweek ={R.drawable.ic_sun,R.drawable.ic_mon,R.drawable.ic_tue,R.drawable.ic_wed,R.drawable.ic_thur,R.drawable.ic_fri,R.drawable.ic_sat};
    private Calendar calendar;
    public BorrowAdapter(Context mContext, ArrayList<Payment> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_borrow,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = mList.get(position);
        calendar = Calendar.getInstance();
        calendar.set(payment.getmTime().getmYear(),payment.getmTime().getmMonth()+1,payment.getmTime().getmDay());
        int day = calendar.get(Calendar.DAY_OF_WEEK) -1;
        holder.imgTime1.setImageResource(dayOfweek[day]);
        holder.title.setText(payment.getmNote());
        holder.btn.setText("- Trả nợ");
        holder.ngayVay.setText(payment.getmTime().showDay());
        holder.ngayTra.setText(payment.getmTime2().showDay());
        holder.soTien.setText(MySupport.converToMoney(-payment.getmMoney()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView btn;
        private TextView ngayVay;
        private TextView soTien;
        private TextView ngayTra;
        private ImageView imgTime1;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tb_br_title);
            btn = itemView.findViewById(R.id.btn_br);
            ngayVay = itemView.findViewById(R.id.tv_br_ngayvay);
            ngayTra = itemView.findViewById(R.id.tv_br_ngaytra);
            soTien = itemView.findViewById(R.id.tv_br_money);
            imgTime1 = itemView.findViewById(R.id.img_time_1);
        }
    }
}
