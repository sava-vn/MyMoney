package com.sava.mymoney;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sava.mymoney.ITF.ItemClickListener;
import com.sava.mymoney.adapter.TypeAdapter;
import com.sava.mymoney.common.MyValues;

public class TypePayActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private int[] mListType;
    private TypeAdapter mAdapter;
    private int mWhatNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_pay);
        initView();
    }
    public void initView(){
        mWhatNew = getIntent().getIntExtra(MyValues.WHATNEW,0);
        if(mWhatNew==1)
            mListType = new int[9];
        else
            mListType = new int[41];
        mAdapter = new TypeAdapter(this, mListType, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(MyValues.TYPE,position);
                setResult(MyValues.TYPE_RETURN_ADD,intent);
                finish();
            }
        },mWhatNew);
        mRecyclerView = findViewById(R.id.rcv_type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
