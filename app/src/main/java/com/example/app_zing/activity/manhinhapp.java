package com.example.app_zing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.app_zing.R;

public class manhinhapp extends AppCompatActivity {
    Button btnformdangki, btnformdangnhap;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manhinhapp);
        anhxa();
        sharedPreferences = getSharedPreferences("datalogin",MODE_PRIVATE);
        boolean idcheck = sharedPreferences.getBoolean("check", false);
        if (idcheck == true){
            Intent intent = new Intent(manhinhapp.this,manhinhgiaodien.class);
            startActivity(intent);
        }

        btnformdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manhinhapp.this, dangki.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });

        btnformdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten1 = new Intent(manhinhapp.this, dangnhap.class);
                startActivity(inten1);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }

    private void anhxa() {
        btnformdangki = findViewById(R.id.btnformdangki);
        btnformdangnhap = findViewById(R.id.btnformdangnhap);
    }
}