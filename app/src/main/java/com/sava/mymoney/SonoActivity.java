package com.sava.mymoney;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sava.mymoney.adapter.BorrowAdapter;
import com.sava.mymoney.model.SDMY;

import java.util.ArrayList;

public class SonoActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnDiVay;
    private Button btnChoVay;
    private TextView tvMoney;
    private RecyclerView mRecyclerView;
    private ArrayList<SDMY> mList;

    private BorrowAdapter mAdpter;
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
        btnDiVay.setBackground(getDrawable(R.drawable.c_tv_2));
        btnChoVay.setBackgroundColor(Color.TRANSPARENT);
        btnDiVay.setTextColor(Color.BLACK);
        btnChoVay.setTextColor(Color.WHITE);
    }

    private  void initAction(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        btnDiVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDiVay.setBackground(getDrawable(R.drawable.c_tv_2));
                btnChoVay.setBackgroundColor(Color.TRANSPARENT);
                btnDiVay.setTextColor(Color.BLACK);
                btnChoVay.setTextColor(Color.WHITE);
            }
        });
        btnChoVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnChoVay.setBackground(getDrawable(R.drawable.c_tv_2));
                btnDiVay.setBackgroundColor(Color.TRANSPARENT);
                btnChoVay.setTextColor(Color.BLACK);
                btnDiVay.setTextColor(Color.WHITE);
            }
        });
    }
    private void initData(){
        mList = MainActivity.mWallet.getAllDay();
        mAdpter = new BorrowAdapter(this, MainActivity.mWallet.getmList1());
        mRecyclerView.setAdapter(mAdpter);
    }

}
