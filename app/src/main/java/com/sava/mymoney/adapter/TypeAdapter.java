package com.sava.mymoney.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder>{
    private Context mContext;
    private int[] mListType;
    private int mWhatnew;
    private ItemClickListener mItemClickListener;
    public TypeAdapter(Context mContext, int[] mListType,ItemClickListener itemClickListener,int whatnew) {
        this.mContext = mContext;
        this.mListType = mListType;
        this.mItemClickListener = itemClickListener;
        this.mWhatnew = whatnew;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = position;
        if(mWhatnew==1){
            int rID =mContext.getResources().getIdentifier(MainActivity.ICON_INCOMES[type],"drawable", mContext.getPackageName());
            Glide.with(mContext).load(rID).into(holder.imgType);
            holder.tvType.setText(MainActivity.TYPE_INCOMES[type]);
        }else{
            int rID =mContext.getResources().getIdentifier(MainActivity.ICON_EXPENDITURES[type],"drawable", mContext.getPackageName());
            Glide.with(mContext).load(rID).into(holder.imgType);
            holder.tvType.setText(MainActivity.TYPE_EXPENDITURES[type]);
        }
    }

    @Override
    public int getItemCount() {
        return mListType.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgType;
        private TextView tvType;
        private LinearLayout layoutType;
        public ViewHolder(View itemView) {
            super(itemView);
            imgType = itemView.findViewById(R.id.img_type);
            tvType = itemView.findViewById(R.id.tv_type);
            layoutType = itemView.findViewById(R.id.layout_type);
            layoutType.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
