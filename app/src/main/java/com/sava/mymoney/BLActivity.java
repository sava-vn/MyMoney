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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.adapter.BLAdapter;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.SBL;
import com.sava.mymoney.model.SDate;

import java.util.ArrayList;
import java.util.Calendar;

public class BLActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnDiVay;
    private Button btnChoVay;
    private TextView tvMoney;
    private RecyclerView mRecyclerView;
    private ArrayList<SBL> mListSBL;
    private int mTypeView;
    private Calendar calendar;
    private BLAdapter mAdpter;
    private View dectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sono);
        dectorView = getWindow().getDecorView();
        dectorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    dectorView.setSystemUiVisibility(hideSystemNavigation());
                }
            }
        });
        calendar = Calendar.getInstance();
        initView();
        initAction();
        initData();
    }

    private void initView() {
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

    private void initAction() {
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
                if (mTypeView != 0) {
                    btnDiVay.setBackground(getDrawable(R.drawable.c_tv_2));
                    btnChoVay.setBackgroundColor(Color.TRANSPARENT);
                    btnDiVay.setTextColor(Color.BLACK);
                    btnChoVay.setTextColor(Color.WHITE);
                    mListSBL.clear();
                    mListSBL.addAll(MainActivity.mWallet.getmListBorrow());
                    mAdpter.notifyDataSetChanged();
                    tvMoney.setText(MySupport.converToMoney(MainActivity.mWallet.getmMoneyBorrow()));
                    mTypeView = 0;
                }
            }
        });
        btnChoVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTypeView != 1) {
                    btnChoVay.setBackground(getDrawable(R.drawable.c_tv_2));
                    btnDiVay.setBackgroundColor(Color.TRANSPARENT);
                    btnChoVay.setTextColor(Color.BLACK);
                    btnDiVay.setTextColor(Color.WHITE);
                    mListSBL.clear();
                    mListSBL.addAll(MainActivity.mWallet.getmListLoan());
                    mAdpter.notifyDataSetChanged();
                    tvMoney.setText(MySupport.converToMoney(MainActivity.mWallet.getmMoneyLoan()));
                    mTypeView = 1;
                }
            }
        });
    }

    private void initData() {
        mListSBL = new ArrayList<>();
        mListSBL.addAll(MainActivity.mWallet.getmListBorrow());
        mAdpter = new BLAdapter(this, mListSBL, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SBL sbl = mListSBL.get(position);
                //Nếu khoản vay là chửa trả
                if (sbl.getIsPayment() == 0) {
                    SDate toDay = new SDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_WEEK));

                    //Chinh thông tin ngày trả , xác định là đã trả
                    sbl.setmSDate3(toDay);
                    sbl.setIsPayment(1);

                    //Tạo một giao dịch thu nợ mới
                    SBL payment = new SBL();
                    payment.setmMoney(-sbl.getmMoney());
                    payment.setmSDate(toDay);
                    payment.setmPerson(sbl.getmIdPayment());
                    payment.setmSDate2(sbl.getmSDate());

                    //Đi vay
                    if(sbl.getmType()==6){
                        payment.setmType(38);//Trả nợ
                        payment.setmNote("Trả nợ cho "+ sbl.getmPerson());
                        Toast.makeText(BLActivity.this, "Trả nợ thành công ", Toast.LENGTH_SHORT).show();
                    }else{//Cho vay
                        payment.setmType(7);//Thu nợ
                        payment.setmNote("Thu nợ từ "+ sbl.getmPerson());
                        Toast.makeText(BLActivity.this, "Thu nợ thành công", Toast.LENGTH_SHORT).show();
                    }

                    //Xác định con trỏ data
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(user.getUid());

                    //Cập nhập thông tin về ngày trả , xác nhận đã trả cho sbl
                    data.child(sbl.getmIdPayment()).setValue(sbl);

                    //Tạo một bản ghi mới
                    String idPayment = data.push().getKey();
                    payment.setmIdPayment(idPayment);

                    //Ghi dữ liệu
                    data.child(idPayment).setValue(payment);
                    MainActivity.mWallet.addPayment(payment);

                    //Cập nhật thay đổi
                    mAdpter.notifyDataSetChanged();
                }else {
                    Toast.makeText(BLActivity.this, "Khoản vay đã được thanh toán trước đó", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvMoney.setText(MySupport.converToMoney(MainActivity.mWallet.getmMoneyBorrow()));
        mRecyclerView.setAdapter(mAdpter);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            dectorView.setSystemUiVisibility(hideSystemNavigation());
        }
    }

    private int hideSystemNavigation() {
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}
