package com.example.app_zing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_zing.R;

public class MainActivity extends AppCompatActivity {


    private static int THOIGIANCHUYEN = 5000;

    Animation topAnima,bottomAnim;
    ImageView logo;
    TextView tendat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnima = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        logo = findViewById(R.id.logo);
        tendat = findViewById(R.id.tendat);

        logo.setAnimation(topAnima);
        tendat.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, manhinhapp.class);
                startActivity(intent);
                finish();
            }
        },THOIGIANCHUYEN);

    }
}