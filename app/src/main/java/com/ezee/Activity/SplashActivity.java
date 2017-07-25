package com.ezee.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ezee.R;
import com.ezee.Utils.BaseData;
import com.ezee.Utils.Prefrences;

public class SplashActivity extends AppCompatActivity {
    boolean UserLoggedIn = false;
    BaseData basedata;
    SharedPreferences loginSharedPreferences;
    SharedPreferences.Editor loginEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WidgetInitialization();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserLoggedIn) {
                    Intent intent = new Intent(SplashActivity.this, MainCategoryActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }, 1000);
    }

    private void WidgetInitialization() {
        loginSharedPreferences = getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE);
        loginEditor = loginSharedPreferences.edit();
        UserLoggedIn = loginSharedPreferences.getBoolean("isLoggedIn", false);
        basedata.setLoggedInStatus(UserLoggedIn);
    }
}
