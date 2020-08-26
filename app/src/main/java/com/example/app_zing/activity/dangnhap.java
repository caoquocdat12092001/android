package com.example.app_zing.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_zing.R;
import com.google.android.material.textfield.TextInputLayout;

public class dangnhap extends AppCompatActivity {
    Button dangnhap ,btndnchuyendangki;
    TextInputLayout etuser, etpass;
    CheckBox cbremember;
    DatabaseHepper db;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dangnhap);
//        db = new DatabaseHepper(this);
        db = new DatabaseHepper(this,DatabaseHepper.DBNAME,null,1);
        anhxa();
        sharedPreferences = getSharedPreferences("datalogin",MODE_PRIVATE);
        String idname = sharedPreferences.getString("taikhoan","");
        String idpass = sharedPreferences.getString("matkhau","");
        boolean idcheck = sharedPreferences.getBoolean("check", false);

//        if (idcheck == true){
//            Intent intent = new Intent(dangnhap.this,manhinhgiaodien.class);
//            startActivity(intent);
//        }


        btndnchuyendangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(dangnhap.this,dangki.class);
                startActivity(intent1);
            }
        });

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String user = etuser.getEditText().getText().toString().trim();
            String pass = etpass.getEditText().getText().toString().trim();
                if (user.equals("") || pass.equals("")) {
                    etuser.setError("Field can't be empty");
                    etpass.setError("Field can't be empty");
                }
                Boolean checkuserpass = db.checkuserpass(user, pass);
                if (checkuserpass == true && cbremember.isChecked()){
                    Toast.makeText(dangnhap.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    //cjinhr sửa nội dung file
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //put giá trị vào
                    editor.putString("taikhoan", user);
                    editor.putString("matkhau", pass);
                    editor.putBoolean("check", true);
                    editor.commit();
                    Intent intent = new Intent(dangnhap.this,manhinhgiaodien.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    overridePendingTransition(R.anim.top, R.anim.top);
                }else if (checkuserpass == true){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //put giá trị vào
                    editor.putString("taikhoan", user);
                    editor.putString("matkhau", pass);
                    editor.putBoolean("check", false);
                    editor.commit();
                    Intent intent = new Intent(dangnhap.this,manhinhgiaodien.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    overridePendingTransition(R.anim.top, R.anim.top);
                }else {
                    Toast.makeText(dangnhap.this, "tân tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void anhxa() {
        dangnhap = findViewById(R.id.dangnhap);
        btndnchuyendangki = findViewById(R.id.btndnchuyendangki);
        etpass = findViewById(R.id.etpass);
        etuser = findViewById(R.id.etuser);
        cbremember = findViewById(R.id.cbremember);
    }
}