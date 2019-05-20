package com.sava.mymoney;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.adapter.TimePaymentAdpter;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.Time;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.TimePayment;
import com.sava.mymoney.model.Wallet;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static String[] TYPE_EXPENDITURES;
    public static String[] TYPE_INCOMES;
    public static String[] ICON_EXPENDITURES;
    public static String[] ICON_INCOMES;
    public static String[] TYPE_PARENT_STRING;
    public static int[] TYPE_PARENT_INT;

    public static Wallet mWallet;

    private RecyclerView mRecyclerView;
    private TimePaymentAdpter mAdpter;
    private ArrayList<TimePayment> mListTimePayment;

    private Toolbar mToolbar;
    private TextView tvToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView mNavigationView;
    private EditText edtSodu;

    private ProgressBar prMain;

    private int TYPE_SHOW;
    private int oldMoney;
    private int dodai;
    private FloatingActionMenu fMenu;
    private FloatingActionButton fIncome;
    private FloatingActionButton fExpenditure;
    private FloatingActionButton fDiVay;
    private FloatingActionButton fChoVay;
    private FloatingActionButton fSodu;

    private MenuItem item1;
    private MenuItem item2;
    private MenuItem item3;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            Intent loginInten = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginInten);
            finish();
        } else {
            try {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            } catch (Exception e) {

            }
            mData = FirebaseDatabase.getInstance().getReference().child(mUser.getPhoneNumber());
            mData.keepSynced(true);
            initData();
            initView();
            initAction();
        }
    }

    public void initView() {
        prMain = findViewById(R.id.pr_main);

        fMenu = findViewById(R.id.fab_main);
        fIncome = findViewById(R.id.fab1);
        fExpenditure = findViewById(R.id.fab2);
        fDiVay = findViewById(R.id.fab3);
        fChoVay = findViewById(R.id.fab4);
        fSodu = findViewById(R.id.fab5);

        tvToolbar = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tvToolbar.setLayoutParams(lp);
        tvToolbar.setText("All Day");
        tvToolbar.setTextSize(20);
        tvToolbar.setTextColor(getColor(R.color.white));
        tvToolbar.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        tvToolbar.setTypeface(Typeface.DEFAULT_BOLD);
        mRecyclerView = findViewById(R.id.rcv_day);
        drawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.tb_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tvToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mToolbar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mAdpter = new TimePaymentAdpter(this, mListTimePayment, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                fMenu.close(true);
                if (TYPE_SHOW != MyValues.SHOW_YEARPAY) {
                    TimePayment timePayment = mListTimePayment.get(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyValues.DAY, timePayment.getmTime().getmDay());
                    bundle.putInt(MyValues.MONTH, timePayment.getmTime().getmMonth());
                    bundle.putInt(MyValues.YEAR, timePayment.getmTime().getmYear());
                    bundle.putInt(MyValues.TYPE_SHOW, TYPE_SHOW);
                    intent.putExtra(MyValues.BUNDLEDAY, bundle);
                    startActivity(intent);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdpter);
    }

    public void initData() {
        TYPE_EXPENDITURES = getResources().getStringArray(R.array.type_exspenditure);
        TYPE_INCOMES = getResources().getStringArray(R.array.type_income);
        ICON_EXPENDITURES = getResources().getStringArray(R.array.icon_expenditure);
        ICON_INCOMES = getResources().getStringArray(R.array.icon_income);
        TYPE_PARENT_STRING = getResources().getStringArray(R.array.TYPE_PARENT_STRING);
        TYPE_PARENT_INT = getResources().getIntArray(R.array.TYPE_PARENT_INT);
        TYPE_SHOW = MyValues.SHOW_DAYPAY;
        mWallet = new Wallet();
        mListTimePayment = new ArrayList<>();
    }

    public void initAction() {
        fSodu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar today = Calendar.getInstance();
                int ngay = today.get(Calendar.DAY_OF_MONTH);
                int thang = today.get(Calendar.MONTH);
                int nam = today.get(Calendar.YEAR);
                final Time toDay = new Time(ngay, thang + 1, nam);

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.dialog_sodu);

                fMenu.close(true);
                ImageView imgClose = dialog.findViewById(R.id.img_d_close);
                final TextView tvSodu = dialog.findViewById(R.id.tv_d_sodu);
                edtSodu = dialog.findViewById(R.id.edt_d_sodu);
                edtSodu.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dodai = edtSodu.length();
                            edtSodu.removeTextChangedListener(this);
                            if (dodai > 3) {
                                int iM = MySupport.StringToMoney(edtSodu.getText().toString());
                                edtSodu.setText(MySupport.converToMoney(iM));
                            }
                            edtSodu.addTextChangedListener(this);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        edtSodu.setSelection(edtSodu.length());
                    }
                });
                Button btnAdd = dialog.findViewById(R.id.btn_d_add);
                for (TimePayment item : mListTimePayment) {
                    if (item.getmTime() != null && item.getmTime().comperTiem(toDay) <= 0) {
                        oldMoney = item.getmBalance();
                        break;
                    }
                }
                tvSodu.setText("Số dư hiện tại của bạn là " + MySupport.converToMoney(oldMoney));

                dialog.show();

                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int newMoney = MySupport.StringToMoney(edtSodu.getText().toString());
                            Payment payment = new Payment();
                            String idPayment = mData.push().getKey();

                            payment.setmIdPayment(idPayment);
                            payment.setmNote("Thay Đổi số dư");
                            payment.setmType(0);
                            payment.setmTime(toDay);
                            payment.setmMoney(newMoney - oldMoney);
                            mData.child(idPayment).setValue(payment);
                            mWallet.addPayment(payment);
                            dialog.dismiss();
                            show();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Xem laị số tiền", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        fIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra(MyValues.WHATNEW, MyValues.KHOANTHU);
                fMenu.close(true);
                startActivity(intent);
            }
        });
        fExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra(MyValues.WHATNEW, MyValues.KHOANCHI);
                fMenu.close(true);
                startActivity(intent);
            }
        });
        fDiVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BorrowActivity.class);
                intent.putExtra(MyValues.WHATNEW, 1);
                startActivity(intent);
                fMenu.close(true);
            }
        });
        fChoVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BorrowActivity.class);
                intent.putExtra(MyValues.WHATNEW, -1);
                startActivity(intent);
                fMenu.close(true);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_dangxuat:
                        mAuth.signOut();
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                        break;
                    case R.id.nav_sono:
                        Intent sonoIntent = new Intent(MainActivity.this, SonoActivity.class);
                        startActivity(sonoIntent);
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                }
                return true;
            }
        });
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {
                    Payment payment = dataSnapshot.getValue(Payment.class);
                    mWallet.addPayment(payment);
                }
                mListTimePayment.clear();
                mListTimePayment.addAll(mWallet.getAllNgay());
                mAdpter.notifyDataSetChanged();
                prMain.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mToolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    private void show() {
        if (TYPE_SHOW == MyValues.SHOW_DAYPAY) {
            mListTimePayment.clear();
            mListTimePayment.addAll(0, mWallet.getAllNgay());
            this.tvToolbar.setText("All day");
        }
        if (TYPE_SHOW == MyValues.SHOW_MONTHPAY) {
            mListTimePayment.clear();
            mListTimePayment.addAll(0, mWallet.getAllThang());
            this.tvToolbar.setText("All month");
        }
        if (TYPE_SHOW == MyValues.SHOW_YEARPAY) {
            mListTimePayment.clear();
            mListTimePayment.addAll(0, mWallet.getAllNam());
            this.tvToolbar.setText("All year");
        }
        mAdpter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item1 = menu.getItem(0);
        item2 = menu.getItem(1);
        item3 = menu.getItem(2);
        item1.setIcon(R.drawable.ic_1);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ngay:
                if (TYPE_SHOW != MyValues.SHOW_DAYPAY) {
                    mListTimePayment.clear();
                    mListTimePayment.addAll(0, mWallet.getAllNgay());
                    this.tvToolbar.setText("All day");
                    TYPE_SHOW = MyValues.SHOW_DAYPAY;
                    item1.setIcon(R.drawable.ic_1);
                    item2.setIcon(R.drawable.alpha_m_box);
                    item3.setIcon(R.drawable.alpha_y_box);
                }
                break;
            case R.id.action_thang:
                if (TYPE_SHOW != MyValues.SHOW_MONTHPAY) {
                    mListTimePayment.clear();
                    mListTimePayment.addAll(0, mWallet.getAllThang());
                    this.tvToolbar.setText("All month");
                    TYPE_SHOW = MyValues.SHOW_MONTHPAY;
                    item1.setIcon(R.drawable.alpha_d_box);
                    item2.setIcon(R.drawable.ic_2);
                    item3.setIcon(R.drawable.alpha_y_box);

                }
                break;
            case R.id.action_nam:
                if (TYPE_SHOW != MyValues.SHOW_YEARPAY) {
                    mListTimePayment.clear();
                    mListTimePayment.addAll(0, mWallet.getAllNam());
                    this.tvToolbar.setText("All year");
                    TYPE_SHOW = MyValues.SHOW_YEARPAY;
                    item1.setIcon(R.drawable.alpha_d_box);
                    item2.setIcon(R.drawable.alpha_m_box);
                    item3.setIcon(R.drawable.ic_3);

                }
                break;
        }
        this.mAdpter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}
