package com.chatdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.chatdemo.MainActivity;
import com.chatdemo.R;
import com.chatdemo.utils.UtilFunctions;

public class SplashActivity extends AppCompatActivity {

    private static final int TIME_MILLIS = 3000;
    private TextView tvVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        tvVersion=(TextView) findViewById(R.id.tv_version);
        tvVersion.setText(UtilFunctions.getAppVersion(this));
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },TIME_MILLIS);

    }


}
