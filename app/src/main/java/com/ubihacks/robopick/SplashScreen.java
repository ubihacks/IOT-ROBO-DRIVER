package com.ubihacks.robopick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                timer.cancel();
                startActivity(new Intent(SplashScreen.this,MainActivity.class)); //this will cancel the timer of the system
                finish();
            }
        }, 1800);
    }
}
