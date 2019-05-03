package com.sava.mymoney;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class VerifiActivity extends AppCompatActivity {
    private EditText edtv1;
    private EditText edtv2;
    private EditText edtv3;
    private EditText edtv4;
    private EditText edtv5;
    private EditText edtv6;
    private TextView tvTime60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifi);
        initWidget();
    }

    private void initWidget() {
        edtv1 = findViewById(R.id.edt_v1);
        edtv2 = findViewById(R.id.edt_v2);
        edtv3 = findViewById(R.id.edt_v3);
        edtv4 = findViewById(R.id.edt_v4);
        edtv5 = findViewById(R.id.edt_v5);
        edtv6 = findViewById(R.id.edt_v6);
        tvTime60 = findViewById(R.id.tv_time60);
        edtv1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                autoNext(edtv1,edtv2);
            }
        });
        edtv2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoNext(edtv2,edtv3);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtv3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoNext(edtv3,edtv4);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtv4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoNext(edtv4,edtv5);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtv5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoNext(edtv5,edtv6);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTime60.setText("Mã xác nhận sẽ bị hủy trong 0 : " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvTime60.setText("Mã xác nhận đã bị hủy");
            }

        }.start();
    }

    private void autoNext(EditText edt,EditText edt2) {
        if (edt.getText().toString().trim().length() > 0) {
            edt.clearFocus();
            edt2.requestFocus();
        }
    }
}
