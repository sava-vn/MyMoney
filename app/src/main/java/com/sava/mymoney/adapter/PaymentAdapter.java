package com.sava.mymoney.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Payment> mListPayment;

    public PaymentAdapter(Context mContext, ArrayList<Payment> mListPayment) {
        this.mContext = mContext;
        this.mListPayment = mListPayment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = mListPayment.get(position);
        int type = payment.getmType();
        holder.tvMoneyPayment.setText(MySupport.converToMoney(payment.getmMoney()));
        if (payment.getmMoney() < 0) {
            holder.tvTypePayment.setText(MainActivity.TYPE_EXPENDITURES[type]);
            holder.imgTypePayment.setImageResource(MainActivity.ICON_EXPENDITURES[type]);
        } else {
            holder.imgTypePayment.setImageResource(MainActivity.ICON_INCOMES[type]);
            holder.tvTypePayment.setText(MainActivity.TYPE_INCOMES[type]);
        }
    }

    @Override
    public int getItemCount() {
        return mListPayment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTypePayment;
        private TextView tvTypePayment;
        private TextView tvMoneyPayment;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTypePayment = itemView.findViewById(R.id.img_typePayment);
            tvTypePayment = itemView.findViewById(R.id.tv_typePayment);
            tvMoneyPayment = itemView.findViewById(R.id.tv_moneyPyament);
        }
    }
}
