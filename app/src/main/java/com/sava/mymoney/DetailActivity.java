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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.fragment.ExpenditureFragment;
import com.sava.mymoney.fragment.HomeFragment;
import com.sava.mymoney.fragment.IncomeFragment;
import com.sava.mymoney.model.SDate;

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigation;

    private HomeFragment homeFragment;
    private IncomeFragment upFragment;
    private ExpenditureFragment downFragment;

    private Bundle bundle;
    private TextView tvToolbar;
    private View dectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dectorView = getWindow().getDecorView();
        dectorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    dectorView.setSystemUiVisibility(hideSystemNavigation());
                }
            }
        });
        Intent intent = getIntent();
        bundle = intent.getBundleExtra(MyValues.BUNDLEDAY);
        initView();
    }

    public void initView() {
        tvToolbar = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tvToolbar.setLayoutParams(lp);
        SDate sDate = new SDate(bundle.getInt(MyValues.DAY), bundle.getInt(MyValues.MONTH), bundle.getInt(MyValues.YEAR));
        String toolbarTitle;
        if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_DAYPAY)
            toolbarTitle = sDate.showDay();
        else if (bundle.getInt(MyValues.TYPE_SHOW) == MyValues.SHOW_MONTHPAY)
            toolbarTitle = sDate.showMonth();
        else
            toolbarTitle = bundle.getInt(MyValues.YEAR) + "";
        tvToolbar.setText(toolbarTitle);
        MySupport.setFontBold(this, tvToolbar, MyValues.FONT_U);
        tvToolbar.setTextSize(20);
        tvToolbar.setTextColor(getColor(R.color.white));
        homeFragment = new HomeFragment();
        upFragment = new IncomeFragment();
        downFragment = new ExpenditureFragment();
        initFragment(homeFragment, MyValues.HOME);

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
            fragmentTransaction.replace(R.id.framLayout_detail, fragment, tag);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
