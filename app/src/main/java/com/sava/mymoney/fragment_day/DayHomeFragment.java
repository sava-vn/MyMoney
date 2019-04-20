package com.sava.mymoney.fragment_day;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;
import com.sava.mymoney.adapter.PaymentAdapter;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.DayPayment;
import com.sava.mymoney.model.Payment;

import java.util.ArrayList;


public class DayHomeFragment extends Fragment {
    private TextView tvVi;
    private TextView tvMoney;
    private ImageView img;
    private RecyclerView mRecyclerView;

    private PaymentAdapter mAdapter;
    private ArrayList<Payment> mListPayments;
    private Bundle bundle;
    public DayHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_home, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        tvVi = view.findViewById(R.id.tv_Vi_fragDayHome);
        tvMoney = view.findViewById(R.id.tv_money_fragDayHome);
        img = view.findViewById(R.id.img_fragDayHome);
        mRecyclerView = view.findViewById(R.id.rcv_daypayment);
    }

    public void initData(){
        bundle = getArguments();
        int ngay = bundle.getInt(MyValues.DAY);
        int thang = bundle.getInt(MyValues.MONTH);
        int nam = bundle.getInt(MyValues.YEAR)-2015;
        DayPayment dayPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay];

        mListPayments = dayPayment.getmListPayment();
        tvVi.setText(MySupport.converToMoney(dayPayment.getmVi()));
        tvMoney.setText(MySupport.converToMoney(dayPayment.getmMoney()));

        mAdapter = new PaymentAdapter(getContext(),mListPayments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
