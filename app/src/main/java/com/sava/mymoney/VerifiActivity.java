package com.sava.mymoney;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.AuthProvider;
import java.util.concurrent.TimeUnit;

public class VerifiActivity extends AppCompatActivity {
    private EditText edtv1;
    private EditText edtv2;
    private EditText edtv3;
    private EditText edtv4;
    private EditText edtv5;
    private EditText edtv6;
    private TextView tvTime60;
    private String phoneNumber;
    private Button btnVerifi;
    private Button btnReverifi;
    private ImageButton btnBack;
    private TextView tvVphone;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerifiCationId;
    private PhoneAuthProvider.ForceResendingToken mResendingToken;
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
        btnVerifi = findViewById(R.id.btn_verifi);
        btnReverifi = findViewById(R.id.btn_reverifi);
        tvVphone = findViewById(R.id.tv_v_phone);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifiActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
        phoneNumber = getIntent().getStringExtra("PHONELG");
        if(phoneNumber.charAt(0)=='0')
            phoneNumber = phoneNumber.substring(1);
        phoneNumber = "+84"+ phoneNumber;
        tvVphone.setText("Một mã xác nhận đã được gửi tới "+ phoneNumber + " . Xin vui lòng kiểm tra và nhập mã xác nhận");
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                tvTime60.setText("Không thể gửi mã xác nhận");
                btnReverifi.setEnabled(true);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerifiCationId =s;
                mResendingToken = forceResendingToken;
                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvTime60.setText("Mã xác nhận sẽ bị hủy trong 0 : " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        tvTime60.setText("Mã xác nhận đã bị hủy");
                        btnReverifi.setEnabled(true);
                    }

                }.start();
                btnVerifi.setEnabled(true);
            }
        };

        verification();
        btnVerifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifiCode = edtv1.getText().toString()+
                        edtv2.getText().toString()+
                        edtv3.getText().toString()+
                        edtv4.getText().toString()+
                        edtv5.getText().toString()+
                        edtv6.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerifiCationId,verifiCode);
                singInWithPhoneAuthCredential(credential);
            }
        });
        btnReverifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification();
            }
        });
    }
    private void autoNext(EditText edt,EditText edt2) {
        if (edt.getText().toString().trim().length() > 0) {
            edt.clearFocus();
            edt2.requestFocus();
        }
    }
    private void verification(){
        btnReverifi.setEnabled(false);
        btnVerifi.setEnabled(false);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                VerifiActivity.this,
                mCallbacks
        );
    }
    private void singInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mainIntent = new Intent(VerifiActivity.this,MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }else{
                            tvTime60.setText("Vui lòng kiểm tra lại mã xác minh");
                        }
                    }
                });
    }
}
