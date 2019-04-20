package com.sava.mymoney.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.mymoney.DayPayActivity;
import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.MainActivity;
import com.sava.mymoney.R;
import com.sava.mymoney.adapter.PaymentAdapter;
import com.sava.mymoney.adapter.TimePaymentAdpter;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.DayPayment;
import com.sava.mymoney.model.MonthPayment;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.Time;
import com.sava.mymoney.model.TimePayment;

import java.util.ArrayList;


public class DayHomeFragment extends Fragment {
    private TextView tvVi;
    private TextView tvMoney;
    private RecyclerView mRecyclerView;
    private int ngay;
    private int thang;
    private int nam;
    private PaymentAdapter mPaymentAdapter;
    private ArrayList<Payment> mListPayments;
    private ArrayList<TimePayment> mListDayPayments;
    private TimePaymentAdpter mTimePaymentAdpter;
    private Bundle bundle;

    public DayHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_DAYPAY)
            initData1();
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_MONTHPAY)
            initData2();
        return view;
    }

    public void initView(View view) {
        tvVi = view.findViewById(R.id.tvVi_fragHome);
        tvMoney = view.findViewById(R.id.tv_money_fragHome);
        mRecyclerView = view.findViewById(R.id.rcv_itempayment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void initData() {
        bundle = getArguments();
        ngay = bundle.getInt(MyValues.DAY);
        thang = bundle.getInt(MyValues.MONTH);
        nam = bundle.getInt(MyValues.YEAR) - 2015;
    }

    public void initData1() {
        DayPayment dayPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay];
        mListPayments = dayPayment.getmListPayment();
        tvVi.setText(MySupport.converToMoney(dayPayment.getmVi()));
        tvMoney.setText(MySupport.converToMoney(dayPayment.getmMoney()));
        mPaymentAdapter = new PaymentAdapter(getContext(), mListPayments);
        mRecyclerView.setAdapter(mPaymentAdapter);
    }

    public void initData2() {
        MonthPayment monthPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang];
        mListDayPayments = new ArrayList<>();
        for (int i = 31; i >0 ; i--) {
            if(monthPayment.getmArrDayPayment()[i]!=null)
                mListDayPayments.add(monthPayment.getmArrDayPayment()[i]);
        }
        tvVi.setText(MySupport.converToMoney(monthPayment.getmVi()));
        tvMoney.setText(MySupport.converToMoney(monthPayment.getmMoney()));
        mTimePaymentAdpter = new TimePaymentAdpter(getContext(), mListDayPayments, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TimePayment timePayment = mListDayPayments.get(position);
                Intent intent = new Intent(getActivity(), DayPayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(MyValues.DAY, timePayment.getmTime().getmDay());
                bundle.putInt(MyValues.MONTH, timePayment.getmTime().getmMonth());
                bundle.putInt(MyValues.YEAR, timePayment.getmTime().getmYear());
                bundle.putInt(MyValues.TYPE_SHOW,1);
                intent.putExtra(MyValues.BUNDLEDAY, bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mTimePaymentAdpter);
    }

}
