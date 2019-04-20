package com.sava.mymoney;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.fragment_month.MonthExpenditureFragment;
import com.sava.mymoney.fragment_month.MonthHomeFragment;
import com.sava.mymoney.fragment_month.MonthIncomeFragment;

public class MonthPayActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private MonthHomeFragment homeFragment;
    private MonthIncomeFragment incomeFragment;
    private MonthExpenditureFragment expenditureFragment;

    private Bundle bundle;
    private TextView tvToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_pay2);
        Intent intent = getIntent();
        bundle = intent.getBundleExtra(MyValues.BUNDLEDAY);
        initView();
    }
    public void initView(){
        tvToolbar = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tvToolbar.setLayoutParams(lp);
        tvToolbar.setText(bundle.getInt(MyValues.MONTH)+ " năm "+ bundle.getInt(MyValues.YEAR));
        tvToolbar.setTextSize(20);
        tvToolbar.setTextColor(getColor(R.color.white));
        MySupport.setFontBold(this, tvToolbar, MyValues.FONT_AGENCY);
        mToolbar = findViewById(R.id.tb_monthPay);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(tvToolbar);

        homeFragment = new MonthHomeFragment();
        incomeFragment = new MonthIncomeFragment();
        expenditureFragment = new MonthExpenditureFragment();
        initFragment(homeFragment,MyValues.HOME);

        mBottomNavigationView = findViewById(R.id.bottom_nav_MonthPayment);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        initFragment(homeFragment, MyValues.HOME);
                        return true;
                    case R.id.action_up:
                        initFragment(incomeFragment, MyValues.INCOMES);
                        return true;
                    case R.id.action_down:
                        initFragment(expenditureFragment, MyValues.EXPENDITURE);
                        return true;
                }
                return false;
            }
        });
    }
    public void initFragment(Fragment fragment, String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        if (frag == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.framLayout_MonthPament, fragment,tag);
            fragmentTransaction.commit();
        }
    }
}
