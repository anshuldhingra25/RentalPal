package com.example.jasycdell3.rentalpal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {
    ProgressBar bar;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            i = 1;

        }
        if (i == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    finish();
                }
            }, 5000);
        } else {
            finish();
        }


    }
}
