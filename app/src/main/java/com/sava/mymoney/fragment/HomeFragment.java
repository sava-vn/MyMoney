package com.sava.mymoney.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sava.mymoney.DetailActivity;
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
import com.sava.mymoney.model.TimePayment;
import com.sava.mymoney.swipe.RecyclerItemTouchHelper;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private TextView tvBalance;
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
    public HomeFragment() {
    }

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mData;
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
        tvBalance = view.findViewById(R.id.tvSodu_fragHome);
        tvMoney = view.findViewById(R.id.tv_thuChi_fragHome);
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
        mListDayPayments = null;
        mListPayments =null;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mData= FirebaseDatabase.getInstance().getReference().child(mUser.getPhoneNumber());
    }

    public void initData1() {
        DayPayment dayPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang].getmArrDayPayment()[ngay];
        mListPayments = dayPayment.getmListPayment();
        tvBalance.setText(MySupport.converToMoney(dayPayment.getmBalance()));
        tvMoney.setText(MySupport.converToMoney(dayPayment.getmMoney()));
        mPaymentAdapter = new PaymentAdapter(getContext(),mListPayments);
        mRecyclerView.setAdapter(mPaymentAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    }

    public void initData2() {
        MonthPayment monthPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang];
        mListDayPayments = new ArrayList<>();
        for (int i = 31; i >0 ; i--) {
            if(monthPayment.getmArrDayPayment()[i].getmCountPay()>0)
                mListDayPayments.add(monthPayment.getmArrDayPayment()[i]);
        }
        tvBalance.setText(MySupport.converToMoney(monthPayment.getmBalance()));
        tvMoney.setText(MySupport.converToMoney(monthPayment.getmMoney()));
        mTimePaymentAdpter = new TimePaymentAdpter(getContext(), mListDayPayments, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TimePayment timePayment = mListDayPayments.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
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
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
            Payment payment = mListPayments.get(position);
            DayPayment dayPayment = MainActivity.mWallet.removePayment(payment);
            tvMoney.setText(MySupport.converToMoney(dayPayment.getmMoney()));
            tvBalance.setText(MySupport.converToMoney(dayPayment.getmBalance()));
            mPaymentAdapter.notifyDataSetChanged();
            mData.child(payment.getmIdPayment()).removeValue();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_MONTHPAY && mListDayPayments !=null) {
            MonthPayment monthPayment = MainActivity.mWallet.getmArrYearPayment()[nam].getmArrMonthPayment()[thang];
            mListDayPayments.clear();
            for (int i = 31; i > 0; i--) {
                if (monthPayment.getmArrDayPayment()[i].getmCountPay()>0)
                    mListDayPayments.add(monthPayment.getmArrDayPayment()[i]);
            }
            tvBalance.setText(MySupport.converToMoney(monthPayment.getmBalance()));
            tvMoney.setText(MySupport.converToMoney(monthPayment.getmMoney()));
            mTimePaymentAdpter.notifyDataSetChanged();
        }
    }
}
