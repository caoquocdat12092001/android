package com.example.app_zing.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_zing.R;
import com.google.android.material.textfield.TextInputLayout;

public class dangki extends AppCompatActivity {
    Button btndangkitaikhoan, btnhuydangkitaikhoan;
    TextInputLayout ettendangnhap, etemail, etpassword, etcfpassword;
    final String partenemail = "[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+";
    DatabaseHepper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        anhxadangki();

        db = new DatabaseHepper(this,DatabaseHepper.DBNAME,null,1);


        btnhuydangkitaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dangki.this,manhinhapp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

        btndangkitaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                String tendangnhap = ettendangnhap.getEditText().getText().toString().trim();
                String email = etemail.getEditText().getText().toString().trim();
                String password = etpassword.getEditText().getText().toString().trim();
                String cfpassword = etcfpassword.getEditText().getText().toString().trim();
                if (tendangnhap.equals("") || email.equals("") || password.equals("") || cfpassword.equals("")){
                    ettendangnhap.setError("Field can't be empty");
                    etemail.setError("Field can't be empty");
                    etpassword.setError("Field can't be empty");
                    etcfpassword.setError("Field can't be empty");
                }else {
                    if (password.equals(cfpassword)){
                        Boolean checkuser = db.checkusser(tendangnhap);
                        if (checkuser == false){
                            Boolean insert = db.insertdata(tendangnhap,password,email);
                            if (insert == true){
                                Toast.makeText(dangki.this, "đăng kí thành công  ", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder buider = new AlertDialog.Builder(dangki.this);
                                buider.setMessage("bạn đãn đăng kí thành công, bạn có muốn chuyển qua trang đăng nhập để đăng nhập vào dứng dụng không ?");
                                buider.setPositiveButton("đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent1 = new Intent(dangki.this, dangnhap.class);
                                        startActivity(intent1);
                                        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                                    }
                                });
                                buider.show();
                            }else {
                                Toast.makeText(dangki.this, "đăng kí không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(dangki.this, "tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(dangki.this, "mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }





            }
        });

    }

    private void anhxadangki() {
        btndangkitaikhoan = findViewById(R.id.btndangkitaikhoan);
        btnhuydangkitaikhoan = findViewById(R.id.btnhuydangkitaikhoan);
        ettendangnhap = findViewById(R.id.ettendangnhap);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        etcfpassword = findViewById(R.id.etcfpassword);
    }

    private void validate() {
        boolean ktra;
        String tendangnhap = ettendangnhap.getEditText().getText().toString().trim();
        String email = etemail.getEditText().getText().toString().trim();
        String password = etpassword.getEditText().getText().toString().trim();
        String cfpassword = etcfpassword.getEditText().getText().toString().trim();
        if (tendangnhap.length() > 30 || tendangnhap.length() <= 5){
            ettendangnhap.setError("Name must be greater than 5 characters and less than 30 characters");
            ktra = false;
        }else {
            ettendangnhap.setError(null);
        }
        if (!email.matches(partenemail)) {
            etemail.setError("Wrong email address eg: abc@gmail.com");
            ktra = false;
        }else {
            etemail.setError(null);
        }
        if (password.length() < 9){
            etpassword.setError("Password must be more than 9 characters");
            ktra = false;
        }else {
            etpassword.setError(null);
        }
        if (!cfpassword.equals(password)){
            etcfpassword.setError("Password confirmation must match the password");
            ktra = false;
        }else {
            etcfpassword.setError(null);
        }
        if ((tendangnhap.length() < 30) && (tendangnhap.length() >= 5) && email.matches(partenemail)
                && (password.length() > 9) && cfpassword.equals(password)){
            Toast.makeText(this, "thành công", Toast.LENGTH_SHORT).show();
            ktra = true;
        }


    }
}