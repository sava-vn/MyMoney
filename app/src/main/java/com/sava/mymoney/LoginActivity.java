package com.sava.mymoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtLg;
    private View dectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dectorView = getWindow().getDecorView();
        dectorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    dectorView.setSystemUiVisibility(hideSystemNavigation());
                }
            }
        });
        btnLogin = findViewById(R.id.btn_login);
        edtLg = findViewById(R.id.edt_lg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edtLg.getText().toString().trim();
                if (phoneNumber.length() < 9 || phoneNumber.length() > 11)
                    Toast.makeText(LoginActivity.this, "Số điện thoại sai", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(LoginActivity.this, VerifiActivity.class);
                    intent.putExtra("PHONELG", phoneNumber);
                    startActivity(intent);
                    finish();
                }
            }
        });
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
