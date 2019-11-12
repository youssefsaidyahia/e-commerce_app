package com.example.android.project_mc;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.project_mc.buyer.Login_activity;

public class Welcom_Page extends AppCompatActivity {
  private static int time_out=10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom__page);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Welcom_Page.this, Login_activity.class);
                startActivity(intent);
                finish();
            }
        },time_out);
    }
}
