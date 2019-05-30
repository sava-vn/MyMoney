package com.sava.mymoney;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sava.mymoney.common.MySupport;
import com.sava.mymoney.model.SBL;
import com.sava.mymoney.model.SDate;

import java.util.Calendar;

public class AddBorrowActivity extends AppCompatActivity {
    private ImageView imgMoney,imgDate, imgDate2,imgPerson, imgNote;
    private TextView tvDate,tvDate2;
    private EditText edtMoney,edtPerson,edtNote;
    private Button btnAdd;
    private ImageButton btnClose;
    private CardView layoutDate;
    private CardView layoutDate2;
    private int mTextSize;
    private int mTypeDate;
    private View dectorView;
    private DatePickerDialog mDatePicker;
    private SBL mSBL;

    private Calendar toDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_borrow);
        dectorView = getWindow().getDecorView();
        dectorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    dectorView.setSystemUiVisibility(hideSystemNavigation());
                }
            }
        });
        initView();
        initTransaction();
        initAction();
        initData();
    }

    private void initView(){
        imgMoney = findViewById(R.id.addborrow_img_money);
        imgDate = findViewById(R.id.addborrow_img_date);
        imgDate2 = findViewById(R.id.addborrow_img_date2);
        imgPerson = findViewById(R.id.addborrow_img_perrson);
        imgNote = findViewById(R.id.addborrow_img_note);
        tvDate = findViewById(R.id.addborrow_tv_date);
        tvDate2 = findViewById(R.id.addborrow_tv_date2);
        edtMoney = findViewById(R.id.addborrow_edt_money);
        edtNote= findViewById(R.id.addborrow_edt_note);
        edtPerson= findViewById(R.id.addborrow_edt_person);
        btnAdd = findViewById(R.id.addborrow_btn_add);
        btnClose = findViewById(R.id.addborrow_btn_close);
        layoutDate = findViewById(R.id.addborrow_layout_date);
        layoutDate2 = findViewById(R.id.addborrow_layout_date2);
        edtPerson.setHint("Người cho vay");
    }
    private void initTransaction(){
        imgMoney.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_transition));
        imgDate2.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_transition));
        imgDate.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_transition));
        imgPerson.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_transition));
        imgNote.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_transition));
    }
    private void initAction(){
        edtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextSize = edtMoney.length();
                edtMoney.removeTextChangedListener(this);
                if (mTextSize > 3) {
                    int iM = MySupport.StringToMoney(edtMoney.getText().toString());
                    edtMoney.setText(MySupport.converToMoney(iM));
                }
                edtMoney.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtMoney.setSelection(edtMoney.length());
            }
        });
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTypeDate =1;
                mDatePicker.show();
            }
        });
        layoutDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTypeDate =2;
                mDatePicker.show();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSBL.getmSDate().getmDay()==0 || mSBL.getmSDate2().getmDay()==0 || mSBL.getmSDate2().comperTiem(mSBL.getmSDate())<0)
                    Toast.makeText(AddBorrowActivity.this, "Xin hãy kiểm tra lại ngày", Toast.LENGTH_SHORT).show();
                else{
                    if(edtPerson.getText().toString().length()==0)
                        Toast.makeText(AddBorrowActivity.this, "Xin hãy kiểm tra lại người vay", Toast.LENGTH_SHORT).show();
                    else{
                        try{
                            int money = MySupport.StringToMoney(edtMoney.getText().toString());
                            mSBL.setmMoney(money);
                            mSBL.setmType(6);
                            mSBL.setmPerson(edtPerson.getText().toString());
                            mSBL.setmNote(edtNote.getText().toString());

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(user.getUid());

                            String idBorrow = data.push().getKey();
                            mSBL.setmIdPayment(idBorrow);

                            data.child(idBorrow).setValue(mSBL);
                            MainActivity.mWallet.addPayment(mSBL);
                            onBackPressed();
                            finish();
                        }catch (Exception e){
                            Toast.makeText(AddBorrowActivity.this, "Xin hãy kiểm tra lại số tiền", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private void initData(){
        toDay = Calendar.getInstance();
        mSBL = new SBL();
        mDatePicker = new DatePickerDialog(AddBorrowActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                toDay.set(year,month,dayOfMonth);
                if(mTypeDate==1){
                    mSBL.setmSDate(new SDate(dayOfMonth,month+1,year,toDay.get(Calendar.DAY_OF_WEEK)));
                    tvDate.setText(mSBL.getmSDate().showDay());
                }else{
                    mSBL.setmSDate2(new SDate(dayOfMonth,month+1,year,toDay.get(Calendar.DAY_OF_WEEK)));
                    tvDate2.setText(mSBL.getmSDate2().showDay());
                }
            }
        },toDay.get(Calendar.YEAR),toDay.get(Calendar.MONTH),toDay.get(Calendar.DAY_OF_MONTH));
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
