package com.sava.mymoney.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.R;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.model.SBL;

import java.util.ArrayList;

public class BLAdapter extends RecyclerView.Adapter<BLAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SBL> mList;
    private ItemClickListener mItemClickListener;
    private int[] dayOfweek = {R.drawable.ic_sun, R.drawable.ic_mon, R.drawable.ic_tue, R.drawable.ic_wed, R.drawable.ic_thur, R.drawable.ic_fri, R.drawable.ic_sat};

    public BLAdapter(Context mContext, ArrayList<SBL> mList, ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_borrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SBL sbl = mList.get(position);

        holder.imgTime1.setImageResource(dayOfweek[sbl.getmSDate().getmDoW() - 1]);
        holder.imgTime2.setImageResource(dayOfweek[sbl.getmSDate2().getmDoW() - 1]);
        holder.title.setText(sbl.getmPerson());
        if (sbl.getIsPayment() == 1) {
            holder.btn.setText("Đã trả");
            holder.ngayTra.setText(sbl.getmSDate3().showDay());
        } else {
            if (sbl.getmType() == 39)
                holder.btn.setText("+ Thu nợ");
            else
                holder.btn.setText("- Trả nợ");
            holder.ngayTra.setText(sbl.getmSDate2().showDay());
        }
        holder.ngayVay.setText(sbl.getmSDate().showDay());
        holder.soTien.setText(MySupport.converToMoney(sbl.getmMoney()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView btn;
        private TextView ngayVay;
        private TextView soTien;
        private TextView ngayTra;
        private ImageView imgTime1;
        private ImageView imgTime2;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tb_br_title);
            btn = itemView.findViewById(R.id.btn_br);
            ngayVay = itemView.findViewById(R.id.tv_br_ngayvay);
            ngayTra = itemView.findViewById(R.id.tv_br_ngaytra);
            soTien = itemView.findViewById(R.id.tv_br_money);
            imgTime1 = itemView.findViewById(R.id.img_time_1);
            imgTime2 = itemView.findViewById(R.id.img_time_2);
            btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
