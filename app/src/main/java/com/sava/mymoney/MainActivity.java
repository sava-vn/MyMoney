package com.sava.mymoney;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.adapter.TimePaymentAdpter;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.Time;
import com.sava.mymoney.model.DayPayment;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.TimePayment;
import com.sava.mymoney.model.Wallet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String[] TYPE_EXPENDITURES;
    public static String[] TYPE_INCOMES;
    public static String[] ICON_EXPENDITURES;
    public static String[] ICON_INCOMES;
    public static Wallet mWallet;
    private RecyclerView mRecyclerView;
    private TimePaymentAdpter mAdpter;
    private Toolbar mToolbar;
    private ArrayList<TimePayment> mListTimePayment;
    private TextView tvToolbar;
    private DrawerLayout drawerLayout;
    private int TYPE_SHOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    public void initView() {
        tvToolbar = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tvToolbar.setLayoutParams(lp);
        tvToolbar.setText("Tất cả các ngày");
        tvToolbar.setTextSize(22);
        tvToolbar.setTextColor(getColor(R.color.white));
        MySupport.setFontBold(this, tvToolbar, MyValues.FONT_AGENCY);

        mRecyclerView = findViewById(R.id.rcv_day);
        drawerLayout = findViewById(R.id.drawer_layout);

        mToolbar = findViewById(R.id.tb_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tvToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mAdpter = new TimePaymentAdpter(this, mListTimePayment, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TimePayment timePayment = mListTimePayment.get(position);
                Intent intent = new Intent(MainActivity.this,DayPayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(MyValues.DAY, timePayment.getmTime().getmDay());
                bundle.putInt(MyValues.MONTH, timePayment.getmTime().getmMonth());
                bundle.putInt(MyValues.YEAR, timePayment.getmTime().getmYear());
                bundle.putInt(MyValues.TYPE_SHOW,TYPE_SHOW);
                intent.putExtra(MyValues.BUNDLEDAY, bundle);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdpter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ngay:
                if (TYPE_SHOW != MyValues.SHOW_DAYPAY) {
                    mListTimePayment.clear();
                    mListTimePayment.addAll(0, mWallet.getAllNgay());
                    this.tvToolbar.setText("Tất cả các ngày");
                    TYPE_SHOW = MyValues.SHOW_DAYPAY;
                }
                break;
            case R.id.action_thang:
                if (TYPE_SHOW != MyValues.SHOW_MONTHPAY) {
                    mListTimePayment.clear();
                    mListTimePayment.addAll(0, mWallet.getAllThang());
                    this.tvToolbar.setText("Tất cả các tháng");
                    TYPE_SHOW = MyValues.SHOW_MONTHPAY;
                }
                break;
            case R.id.action_nam:
                if (TYPE_SHOW != MyValues.SHOW_YEARPAY) {
                    mListTimePayment.clear();
                    mListTimePayment.addAll(0, mWallet.getAllNam());
                    this.tvToolbar.setText("Tất cả các năm");
                    TYPE_SHOW = MyValues.SHOW_YEARPAY;
                }
                break;
        }
        this.mAdpter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void initData() {
        mWallet = new Wallet();
        TYPE_EXPENDITURES = getResources().getStringArray(R.array.type_exspenditure);
        TYPE_INCOMES = getResources().getStringArray(R.array.type_income);
        ICON_EXPENDITURES = getResources().getStringArray(R.array.icon_expenditure);
        ICON_INCOMES = getResources().getStringArray(R.array.icon_income);
        TYPE_SHOW = MyValues.SHOW_DAYPAY;
        Payment payment = new Payment(new Time(8, 4, 2019), 3000000, 0, "Tiền còn lại");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(8, 4, 2019), -102000, 24, "Kem đánh răng ,bàn chải");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(8, 4, 2019), -72000, 23, "Mua thuốc");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(8, 4, 2019), 3000000, 6, "Hẹn trả trong tuần");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(8, 4, 2019), -70000, 12, "Hẹn trả trong tuần");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(9, 4, 2019), 1500000, 0, "Rút từ ngân hàng");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(9, 4, 2019), -6200000, 38, "Trả học bổng Thoa");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(10, 4, 2019), 22700000, 7, "Lấy nợ của Dì");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(11, 4, 2019), -300000, 22, "Đi khám zona");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(11, 4, 2019), -180000, 9, "Về quê");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(12, 4, 2019), -180000, 9, "Về quê");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(12, 4, 2019), 180000, 0, "Thay đổi số dư");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(5, 4, 2019), 1000000, 0, "Thay đổi số dư");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(5, 3, 2019), 1000000, 0, "Thay đổi số dư");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(4, 3, 2019), 1000000, 0, "Thay đổi số dư");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(5, 4, 2018), 1000000, 0, "Thay đổi số dư");
        mWallet.addPayment(payment);
        payment = new Payment(new Time(5, 4, 2018), 1000000, 0, "Thay đổi số dư");
        mWallet.addPayment(payment);
        mListTimePayment = mWallet.getAllNgay();
    }
}
