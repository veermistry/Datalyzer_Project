package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

import static com.example.firstgame.R.mipmap.ic_launcher;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#1225fd"))
                .withHeaderText("")
                .withFooterText("")
                .withBeforeLogoText("")
                .withLogo(ic_launcher);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
