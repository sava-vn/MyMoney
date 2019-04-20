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
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.fragment.DayDownFragment;
import com.sava.mymoney.fragment.DayHomeFragment;
import com.sava.mymoney.fragment.DayUpFragment;

public class DayPayActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigation;

    private DayHomeFragment homeFragment;
    private DayUpFragment upFragment;
    private DayDownFragment downFragment;

    private Bundle bundle;
    private TextView tvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_pay);
        Intent intent = getIntent();
        bundle = intent.getBundleExtra(MyValues.BUNDLEDAY);
        initView();
    }

    public void initView() {
        tvToolbar = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tvToolbar.setLayoutParams(lp);
        tvToolbar.setText(bundle.getInt(MyValues.DAY)+ " tháng "+ bundle.getInt(MyValues.MONTH)+ " năm "+ bundle.getInt(MyValues.YEAR));
        tvToolbar.setTextSize(20);
        tvToolbar.setTextColor(getColor(R.color.white));
        MySupport.setFontBold(this, tvToolbar, MyValues.FONT_AGENCY);


        homeFragment = new DayHomeFragment();
        upFragment = new DayUpFragment();
        downFragment = new DayDownFragment();
        initFragment(homeFragment,MyValues.HOME);

        mToolbar = findViewById(R.id.tb_dayPay);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(tvToolbar);

        mBottomNavigation = findViewById(R.id.bottom_nav_dayPayment);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        initFragment(homeFragment, MyValues.HOME);
                        return true;
                    case R.id.action_up:
                        initFragment(upFragment, MyValues.UP);
                        return true;
                    case R.id.action_down:
                        initFragment(downFragment, MyValues.DOWN);
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
            fragmentTransaction.replace(R.id.framLayout_dayPament, fragment,tag);
            fragmentTransaction.commit();
        }
    }
}
