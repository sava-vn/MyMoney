package com.sava.mymoney;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.Time;

import java.util.Calendar;

public class BorrowActivity extends AppCompatActivity {
    private EditText edtBrMoney;
    private LinearLayout layoutBrTime1;
    private TextView tvBrTime1;
    private LinearLayout layoutBrTime2;
    private TextView tvBrTime2;
    private EditText edtBrNote;
    private ImageButton btnBrClose;
    private TextView tvBrWhatnew;
    private Button btnBrAdd;
    private DatePickerDialog dp1;
    private DatePickerDialog dp2;
    private int type;
    private int mDay;
    private int mMonth;
    private int mYear;
    private Payment payment;
    private boolean day1;
    private boolean day2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        initData();
        initView();
        initAction();
    }
    private void initView(){
        edtBrMoney = findViewById(R.id.edt_bradd_money);
        layoutBrTime1 = findViewById(R.id.layout_bradd_time);
        tvBrTime1 = findViewById(R.id.tv_bradd_time);
        layoutBrTime2 = findViewById(R.id.layout_bradd_time2);
        tvBrTime2 = findViewById(R.id.tv_bradd_time2);
        edtBrNote = findViewById(R.id.edt_bradd_note);
        btnBrClose = findViewById(R.id.btn_brclose);
        tvBrWhatnew = findViewById(R.id.tv_brwhat_new);
        btnBrAdd = findViewById(R.id.btn_bradd);
        if(type==1){
            tvBrWhatnew.setText("Khoản vay mới");
        }else
            tvBrWhatnew.setText("Làm việc tốt");
    }
    private void initAction(){
        btnBrClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        layoutBrTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp1.show();
            }
        });
        layoutBrTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp2.show();
            }
        });
        btnBrAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(day1 && day2 && (payment.getmTime().comperTiem(payment.getmTime2())<=0)){
                    try{
                        int money = Integer.parseInt(edtBrMoney.getText().toString());
                        payment.setmMoney(money*type);
                        payment.setmNote(edtBrNote.getText().toString());
                        if(type==1)
                            payment.setmType(6);
                        else
                            payment.setmType(39);
                        MainActivity.mWallet.addPayment(payment);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(BorrowActivity.this, "Kiểm tra lại số tiền !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BorrowActivity.this, "Kiểm tra lại ngày", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initData(){
        type = getIntent().getIntExtra(MyValues.WHATNEW,0);
        Calendar toDay = Calendar.getInstance();
        mDay = toDay.get(Calendar.DAY_OF_MONTH);
        mMonth = toDay.get(Calendar.MONTH);
        mYear = toDay.get(Calendar.YEAR);
        payment = new Payment();
        day1 = false;
        day2 =false;
        dp1 = new DatePickerDialog(BorrowActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(mDay==dayOfMonth && mMonth ==month && mYear ==year){
                    tvBrTime1.setText("Hôm nay");
                }else
                    tvBrTime1.setText(dayOfMonth + " - " + (month +1) + " - " + year);
                payment.setmTime(new Time(dayOfMonth,mMonth+1,year));
                day1 = true;
                tvBrTime1.setTextColor(Color.BLACK);
            }
        },mYear,mMonth,mDay);
        dp2 = new DatePickerDialog(BorrowActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(mDay==dayOfMonth && mMonth ==month && mYear ==year){
                    tvBrTime2.setText("Hôm nay");
                }else
                    tvBrTime2.setText(dayOfMonth + " - " + (month +1) + " - " + year);
                payment.setmTime2(new Time(dayOfMonth,mMonth+1,year));
                day2 =true;
                tvBrTime2.setTextColor(Color.BLACK);
            }
        },mYear,mMonth,mDay);
    }
}
