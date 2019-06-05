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
import com.sava.mymoney.model.SDate;

import java.util.Calendar;


public class AddActivity extends AppCompatActivity {
    private int x;
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

    private int mTypePayment;
    private int mWhatnew;
    private int mMoney;
    private int dodai;
    private Payment mPayment;

    private Calendar toDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initData();
        initView();
    }

    public void initView() {
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
                    if (mPayment.getmSDate().getmDay() == 0)
                        Toast.makeText(AddActivity.this, "Xin hãy kiểm tra lại ngày", Toast.LENGTH_SHORT).show();
                    else
                        try {
                            mMoney = MySupport.StringToMoney(edtAddMoney.getText().toString()) * mWhatnew;
                            mPayment.setmMoney(mMoney);
                            mPayment.setmNote(edtAddNote.getText().toString());
                            mPayment.setmType(mTypePayment);

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(user.getUid());

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
                dodai = edtAddMoney.length();
                edtAddMoney.removeTextChangedListener(this);
                if (dodai > 3) {
                    int iM = MySupport.StringToMoney(edtAddMoney.getText().toString());
                    edtAddMoney.setText(MySupport.converToMoney(iM));
                }
                edtAddMoney.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtAddMoney.setSelection(edtAddMoney.length());
            }
        });
    }

    public void initData() {
        toDay = Calendar.getInstance();
        mPayment = new Payment();
        mTypePayment = -1;
        mWhatnew = getIntent().getIntExtra(MyValues.WHATNEW, 0);

        dp = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                toDay.set(year, month, dayOfMonth);
                mPayment.setmSDate(new SDate(dayOfMonth, month + 1, year, toDay.get(Calendar.DAY_OF_WEEK)));
                tvAddTime.setText(mPayment.getmSDate().showDay());
                tvAddTime.setTextColor(Color.BLACK);
            }
        }, toDay.get(Calendar.YEAR), toDay.get(Calendar.MONTH), toDay.get(Calendar.DAY_OF_MONTH));
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
