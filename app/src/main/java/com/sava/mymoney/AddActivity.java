package com.sava.mymoney;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.common.MyValues;
import com.sava.mymoney.model.Payment;
import com.sava.mymoney.model.Time;

import java.util.Calendar;


public class AddActivity extends AppCompatActivity {
    private DatePickerDialog dp;
    private EditText edtAddMoney;
    private LinearLayout layoutAddType;
    private ImageView imgAddType;
    private TextView tvAddType;
    private LinearLayout layoutAddTime;
    private TextView tvAddTime;
    private EditText edtAddNote;
    private ImageButton btnClose;
    private TextView tvWhatNew;
    private Button btnAdd;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mTypePayment;
    private int mWhatnew;
    private int mMoney;
    private int dodai;
    private String current;
    private Payment mPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initData();
        initView();
    }

    public void initView() {
        // Ánh xạ
        edtAddMoney = findViewById(R.id.edt_add_money);
        layoutAddType = findViewById(R.id.layout_add_type);
        imgAddType = findViewById(R.id.img_add_type);
        tvAddType = findViewById(R.id.tv_add_type);
        layoutAddTime = findViewById(R.id.layout_add_time);
        tvAddTime = findViewById(R.id.tv_add_time);
        edtAddNote = findViewById(R.id.edt_add_note);
        btnClose = findViewById(R.id.bt_close);
        btnAdd = findViewById(R.id.btn_add);
        tvWhatNew = findViewById(R.id.tv_what_new);

        // mWhatnew =1 nghĩa là thêm mới khoản thu , còn không là thêm mới khoản chi
        if (mWhatnew == 1)
            tvWhatNew.setText("Thêm mới khoản thu");
        else {
            tvWhatNew.setText("Thêm mới khoản chi");
        }
        initAction();
    }

    public void initAction() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        layoutAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, TypePayActivity.class);
                intent.putExtra(MyValues.WHATNEW, mWhatnew);
                startActivityForResult(intent, MyValues.ADD_TO_TYPE);
            }
        });
        layoutAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTypePayment == -1)
                    Toast.makeText(AddActivity.this, "Xin hãy chọn loại chi tiêu !", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        mMoney = MySupport.StringToMoney(edtAddMoney.getText().toString());
                        mPayment = new Payment(new Time(mDay, mMonth + 1, mYear), mMoney * mWhatnew, mTypePayment, edtAddNote.getText().toString());

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(user.getPhoneNumber());

                        String idPayment = data.push().getKey();
                        mPayment.setmIdPayment(idPayment);

                        data.child(idPayment).setValue(mPayment);
                        MainActivity.mWallet.addPayment(mPayment);
                        onBackPressed();
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(AddActivity.this, "Xin kiểm tra lại số tiền !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        edtAddMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtAddMoney.length() > dodai) {
                    dodai = edtAddMoney.length();
                    edtAddMoney.removeTextChangedListener(this);
                    if (dodai>3) {
                        int iM = MySupport.StringToMoney(edtAddMoney.getText().toString());
                        edtAddMoney.setText(MySupport.converToMoney(iM));
                    }
                    edtAddMoney.addTextChangedListener(this);
                }
                dodai = edtAddMoney.length();
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtAddMoney.setSelection(dodai);
            }
        });
    }

    public void initData() {
        //Lấy ngày, tháng, năm hiện tại
        Calendar today = Calendar.getInstance();
        mDay = today.get(Calendar.DAY_OF_MONTH);
        mMonth = today.get(Calendar.MONTH);
        mYear = today.get(Calendar.YEAR);

        // Khởi tạo cho kiểu chi tiêu là  -1
        mTypePayment = -1;

        // Kiểu thêm mới là thu hay chi, giá trị này lấy từ mainActivity
        mWhatnew = getIntent().getIntExtra(MyValues.WHATNEW, 0);

        // Khởi tạo datepicker
        dp = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (mDay == dayOfMonth && mMonth == month && mYear == year) {
                    tvAddTime.setText("Hôm nay");
                } else {
                    mDay = dayOfMonth;
                    mMonth = month;
                    mYear = year;
                    tvAddTime.setText(mDay + " - " + (mMonth + 1) + " - " + mYear);
                }
                tvAddTime.setTextColor(Color.BLACK);
            }
        }, mYear, mMonth, mDay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyValues.ADD_TO_TYPE && resultCode == MyValues.TYPE_RETURN_ADD) {
            mTypePayment = data.getIntExtra(MyValues.TYPE, 0);
            if (mWhatnew != MyValues.KHOANTHU) {
                tvAddType.setText(MainActivity.TYPE_EXPENDITURES[mTypePayment]);
                int rID = getResources().getIdentifier(MainActivity.ICON_EXPENDITURES[mTypePayment], "drawable", getPackageName());
                Glide.with(this).load(rID).into(imgAddType);
            } else {
                tvAddType.setText(MainActivity.TYPE_INCOMES[mTypePayment]);
                int rID = getResources().getIdentifier(MainActivity.ICON_INCOMES[mTypePayment], "drawable", getPackageName());
                Glide.with(this).load(rID).into(imgAddType);
            }
            tvAddType.setTextColor(Color.BLACK);
        }
    }
}
