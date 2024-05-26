package com.example.quizappraman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay for SPLASH_TIME_OUT milliseconds and then start the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close this activity so the user won't able to go back to it by pressing the back button
            }
        }, SPLASH_TIME_OUT);
    }
}
