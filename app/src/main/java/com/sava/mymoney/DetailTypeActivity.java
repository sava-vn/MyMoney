package com.sava.mymoney;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sava.mymoney.adapter.DetailTypeAdapter;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.SDate;
import com.sava.mymoney.model.SDay;

import java.util.ArrayList;

public class DetailTypeActivity extends AppCompatActivity {
    private NestedScrollView nest;
    private static final int DAY = 1;
    private static final int MONTH = 1;
    private ImageView imgBack;
    private TextView tvDate, tvMoney, tvType, tvCountPay;
    private RecyclerView mRecyclerView;
    private int ngay, thang, nam, dayOrMonth, type, money, count;
    private ArrayList<Payment> listPayments;
    private DetailTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_type);
        initView();
        initData();
        setDataToView();
        initAction();
    }

    private void initView() {
        imgBack = findViewById(R.id.a_detail_type_btnBack);
        tvDate = findViewById(R.id.a_detail_type_tvDate);
        tvMoney = findViewById(R.id.a_detail_type_tvMoney);
        tvType = findViewById(R.id.a_detail_type_tvType);
        tvCountPay = findViewById(R.id.a_detail_type_tvCountPay);
        mRecyclerView = findViewById(R.id.a_detail_type_rcvPayment);
        nest = findViewById(R.id.nest);
    }

    private void initData() {
        Intent intent = getIntent();
        ngay = intent.getIntExtra("NGAY", 0);
        thang = intent.getIntExtra("THANG", 0);
        nam = intent.getIntExtra("NAM", 0);
        dayOrMonth = intent.getIntExtra("DAYORMONTH", 0);
        type = intent.getIntExtra("TYPE", 0);
        listPayments = new ArrayList<>();
    }

    private void setDataToView() {
        SDate sDate = new SDate(ngay, thang, nam + 2015, 0);
        if (dayOrMonth == DAY) {
            tvDate.setText(sDate.showDay());
            tvType.setText(MainActivity.TYPE_EXPENDITURES[type]);
            setListDay();
        } else {
            tvDate.setText(sDate.showMonth());
            setListMonth();
            tvType.setText(MainActivity.TYPE_PARENT_STRING[type]);
        }
        mAdapter = new DetailTypeAdapter(this, listPayments);

        tvMoney.setText(MySupport.converToMoney(money));
        tvCountPay.setText(count + "");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        nest.getParent().requestChildFocus(nest, nest);
    }

    private void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void setListDay() {
        listPayments.clear();
        money = 0;
        count = 0;
        for (Payment payment : MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang].getmArrSDay()[ngay].getmListPayment()) {
            if (payment.getmType() == type && payment.getmMoney() < 0) {
                listPayments.add(payment);
                money += payment.getmMoney();
                count++;
            }
        }
    }

    private void setListMonth() {
        listPayments.clear();
        money = 0;
        count = 0;
        SDay[] listDay = MainActivity.mWallet.getsYears()[nam].getmArrMonthPayment()[thang].getmArrSDay();
        for (int i = 31; i >= 1; i--) {
            SDay sDay = listDay[i];
            if (sDay.getmMoneyOut() < 0) {
                int x =0;
                for (Payment payment : sDay.getmListPayment()) {
                    int t = payment.getmType();
                    if (MainActivity.TYPE_PARENT_INT[t] == type && payment.getmMoney() < 0) {
                        if(x==0){
                            Payment pay = new Payment();
                            pay.setmMoney(0);
                            pay.setmNote(payment.getmSDate().showDay());
                            listPayments.add(pay);
                            x++;
                        }
                        listPayments.add(payment);
                        money += payment.getmMoney();
                        count++;
                    }
                }
            }
        }
    }
}
