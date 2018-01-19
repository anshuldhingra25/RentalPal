package com.example.jasycdell3.rentalpal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class FormSubmission extends AppCompatActivity {
    TextView txt_time;
    Button menu;
    private AdView adView_form_submission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.light_grey));
            getWindow().setStatusBarColor(getResources().getColor(R.color.light_grey));
        }
        setContentView(R.layout.activity_form_submission);
        txt_time = (TextView) findViewById(R.id.txt_time);
        adView_form_submission = (AdView) findViewById(R.id.adView_form_submission);
        AdRequest adRequest = new AdRequest.Builder()
                // Check the LogCat to get your test device ID
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

                .build();
        adView_form_submission.loadAd(adRequest);
        String delegate = "hh:mm aaa";
        txt_time.setText(DateFormat.format(delegate, Calendar.getInstance().getTime()));
        menu = (Button) findViewById(R.id.menu);
//        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                Calendar c = Calendar.getInstance();
//                txt_time.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
//            }
//
//            public void onFinish() {
//
//            }
//        };
//        newtimer.start();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Share.class));
            }
        });
    }
    @Override
    public void onPause() {
        if (adView_form_submission != null) {
            adView_form_submission.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView_form_submission != null) {
            adView_form_submission.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView_form_submission != null) {
            adView_form_submission.destroy();
        }
        super.onDestroy();
    }
}
