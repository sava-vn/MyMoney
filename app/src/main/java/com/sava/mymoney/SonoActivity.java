package com.sava.mymoney;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.adapter.TimePaymentAdpter;
import com.sava.mymoney.model.TimePayment;

import java.util.ArrayList;

public class SonoActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnDiVay;
    private Button btnChoVay;
    private TextView tvMoney;
    private RecyclerView mRecyclerView;
    private ArrayList<TimePayment> mList;

    private TimePaymentAdpter mAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sono);
        initView();
        initAction();
        initData();
    }
    private void initView(){
        btnBack = findViewById(R.id.btn_back_sono);
        btnDiVay = findViewById(R.id.btn_divay);
        btnChoVay = findViewById(R.id.btn_chovay);
        tvMoney = findViewById(R.id.tv_money_sono);
        mRecyclerView = findViewById(R.id.rcv_soNo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private  void initAction(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
    private void initData(){
        mList = MainActivity.mWallet.getAllNgay();
        mAdpter = new TimePaymentAdpter(this, mList, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SonoActivity.this, "abc" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdpter);
    }

}
