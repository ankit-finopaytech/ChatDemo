package com.chatdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.chatdemo.utils.AppConstants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvYou;
    private TextView tvComputer;
    private String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvYou=(TextView) findViewById(R.id.tv_you);
        tvComputer=(TextView) findViewById(R.id.tv_computer);
        tvYou.setOnClickListener(this);
        tvComputer.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_you:{
                Intent intent=new Intent(this,ChatWindowActivity.class);
                intent.putExtra(AppConstants.KEY_SOURCE,AppConstants.YOU);
                intent.putExtra(AppConstants.KEY_USERNAME,getString(R.string.str_computer));
                startActivity(intent);
            }
            break;
            case R.id.tv_computer:{
                Intent intent=new Intent(this,ChatWindowActivity.class);
                intent.putExtra(AppConstants.KEY_SOURCE,AppConstants.COMPUTER);
                intent.putExtra(AppConstants.KEY_USERNAME,getString(R.string.str_random_user));
                startActivity(intent);
            }
            break;
        }
    }
}
