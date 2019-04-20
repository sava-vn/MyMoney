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

import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.fragment.DayExpenditureFragment;
import com.sava.mymoney.fragment.DayHomeFragment;
import com.sava.mymoney.fragment.DayIncomeFragment;

public class DayPayActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigation;

    private DayHomeFragment homeFragment;
    private DayIncomeFragment upFragment;
    private DayExpenditureFragment downFragment;

    private Bundle bundle;
    private TextView tvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pay);
        Intent intent = getIntent();
        bundle = intent.getBundleExtra(MyValues.BUNDLEDAY);
        initView();
    }

    public void initView() {
        tvToolbar = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tvToolbar.setLayoutParams(lp);
        String toolbarTitle;
        if(bundle.getInt(MyValues.TYPE_SHOW)==MyValues.SHOW_DAYPAY)
            toolbarTitle = bundle.getInt(MyValues.DAY)+ " tháng "+ bundle.getInt(MyValues.MONTH)+ " năm "+ bundle.getInt(MyValues.YEAR);
        else if (bundle.getInt(MyValues.TYPE_SHOW)==MyValues.SHOW_MONTHPAY)
            toolbarTitle = bundle.getInt(MyValues.MONTH)+ " năm "+ bundle.getInt(MyValues.YEAR);
        else
            toolbarTitle = bundle.getInt(MyValues.YEAR) + "";
        tvToolbar.setText(toolbarTitle);
        tvToolbar.setTextSize(20);
        tvToolbar.setTextColor(getColor(R.color.white));
        MySupport.setFontBold(this, tvToolbar, MyValues.FONT_AGENCY);

        homeFragment = new DayHomeFragment();
        upFragment = new DayIncomeFragment();
        downFragment = new DayExpenditureFragment();
        initFragment(homeFragment,MyValues.HOME);

        mToolbar = findViewById(R.id.tb_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(tvToolbar);

        mBottomNavigation = findViewById(R.id.navi_detail);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        initFragment(homeFragment, MyValues.HOME);
                        return true;
                    case R.id.action_up:
                        initFragment(upFragment, MyValues.INCOMES);
                        return true;
                    case R.id.action_down:
                        initFragment(downFragment, MyValues.EXPENDITURE);
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
            fragmentTransaction.replace(R.id.framLayout_detail, fragment,tag);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
