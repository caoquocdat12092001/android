package com.example.app_zing.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_zing.Adapter.listnhacAdapter;
import com.example.app_zing.Fragment.canhan_fragment;
import com.example.app_zing.Fragment.home_fragment;
import com.example.app_zing.Fragment.yeuthich_fragment;
import com.example.app_zing.R;
import com.example.app_zing.Adapter.menuAdapter;
import com.example.app_zing.modul.itemmenu;
import com.example.app_zing.modul.listnhac;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class manhinhgiaodien extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    // public static khai báo đb kiểu này dể qua class khác có thể sử dụng được,, nếu khai báo hằng số thì thêm final
    public static DatabaseHepper MyDB;
    Toolbar toolbar;
    FloatingActionButton floatingthem;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btndangxuat , btnthongtin, listnhaccanhan;
    TextView tvnguoidung;
    com.example.app_zing.Adapter.menuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manhinhgiaodien);
        anhxa();






        sukientoolbar();
        bottomNavigationView = findViewById(R.id.menu_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomabc);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new home_fragment()).commit();

        final Intent intent = getIntent();
        final String value1 = intent.getStringExtra("user");
        SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
        tvnguoidung.setText(sharedPreferences.getString("taikhoan",""));

        MyDB = new DatabaseHepper(this,DatabaseHepper.DBNAME,null,1);


        listnhaccanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(manhinhgiaodien.this,manhinhnhaccanhan.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });

        btnthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog2 = new Dialog(manhinhgiaodien.this);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.dialogthongtin);
                dialog2.show();
                TextView tennguoidung = dialog2.findViewById(R.id.tennguoidung);
                TextView matkhaunguoidung = dialog2.findViewById(R.id.matkhaunguoidung);
                SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
                tennguoidung.setText(sharedPreferences.getString("taikhoan",""));
                matkhaunguoidung.setText(sharedPreferences.getString("matkhau",""));

            }
        });



        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("taikhoan");
                editor.remove("matkhau");
                editor.remove("check");
                editor.commit();
                Intent intent1 = new Intent(manhinhgiaodien.this,manhinhapp.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
    }





    private BottomNavigationView.OnNavigationItemSelectedListener bottomabc = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.menuhome:
                    fragment = new home_fragment();
                    break;
                case R.id.menucanhan:
                    fragment = new canhan_fragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();
            return true;
        }
    };
    private void sukientoolbar() {
        //hàm hỗ trợ cho toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // gán icon
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void anhxa() {
        final Dialog dialog = new Dialog(this);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        btndangxuat = findViewById(R.id.btndangxuat);
        tvnguoidung = findViewById(R.id.tvnguoidung);
        btnthongtin = findViewById(R.id.btnthongtin);
        listnhaccanhan = findViewById(R.id.listnhaccanhan);
    }


}