package com.sava.mymoney;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class FingerActivity extends AppCompatActivity implements FingerPrintAuthCallback {
    private FingerPrintAuthHelper mFingerPrintAuthHelper;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        mAuth  = FirebaseAuth.getInstance();
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this,this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {

    }

    @Override
    public void onNoFingerPrintRegistered() {

    }

    @Override
    public void onBelowMarshmallow() {
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Intent intent = new Intent(FingerActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

    }
}
