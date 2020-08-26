package com.example.app_zing.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_zing.Adapter.listnhacAdapter;
import com.example.app_zing.R;
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

public class manhinhnhaccanhan extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    // public static khai báo đb kiểu này dể qua class khác có thể sử dụng được,, nếu khai báo hằng số thì thêm final
    public static DatabaseHepper MyDB;
    FloatingActionButton floatingthem;
    ListView lvnhac;
    Toolbar toolbarnhaccanhan;
    ImageView anhnhac,anhnhacsua;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btndangxuat , btnthongtin, listnhaccanhan;
    TextView tvnguoidung;
    ImageButton btnfolder;
    ArrayList<listnhac> arraylistnhac;
    listnhacAdapter adapter;
    int REQUREQUES_CODE_FOLDER = 1;
    int REQUREQUES_CODE_FOLDERSUA = 2;
    ArrayList<itemmenu> arrayList;
    com.example.app_zing.Adapter.menuAdapter menuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhnhaccanhan);
        anhxa1();
        sukientoolbar1();
        lvnhac = findViewById(R.id.lvnhac1);
        arraylistnhac = new ArrayList<>();
        adapter = new listnhacAdapter(this,R.layout.dong_bai_hat,arraylistnhac);
        lvnhac.setAdapter(adapter);


        bottomNavigationView = findViewById(R.id.menu_bottom);
//        bottomNavigationView.setOnNavigationItemSelectedListener(bottomabc);
//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new home_fragment()).commit();

        final Intent intent = getIntent();
        final String value1 = intent.getStringExtra("user");
//        SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
//        tvnguoidung.setText(sharedPreferences.getString("taikhoan",""));

        MyDB = new DatabaseHepper(this,DatabaseHepper.DBNAME,null,1);

        //tạo bảng danh sách nhạc MyDB
        MyDB.Querydata("CREATE TABLE IF NOT EXISTS nhaccanhan(Id INTEGER PRIMARY KEY AUTOINCREMENT,hinhanh BLOB, tenbaihat VACHAR(200), casi VACHAR(100))");
        hienthidanhsachnhac();

        floatingthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogformthem();
            }
        });




//        btndangxuat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.remove("taikhoan");
//                editor.remove("matkhau");
//                editor.remove("check");
//                editor.commit();
//                Intent intent1 = new Intent(manhinhnhaccanhan.this,manhinhapp.class);
//                startActivity(intent1);
//                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
//            }
//        });
    }


    public void Dialogformthem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them);
        dialog.show();
        anhnhac = dialog.findViewById(R.id.anhnhac);
        final TextInputLayout tenbathat = dialog.findViewById(R.id.tenbathat);
        final TextInputLayout tencasi = dialog.findViewById(R.id.tencasi);
        Button dialogbtnthem = dialog.findViewById(R.id.dialogbtnthem);

        dialogbtnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển hình từ bitmap qua byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) anhnhac.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                //định dạng lại kiểu dữ liệu
                //100 là chất lượng hình ảnh
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                //chứa dữ liệu
                //chuyển mảng byte
                byte[] hinh = byteArrayOutputStream.toByteArray();

                String tenbaihat = tenbathat.getEditText().getText().toString().trim();

                if (tenbaihat.equals("")){
                    Toast.makeText(manhinhnhaccanhan.this, "tên bài hát không được trống", Toast.LENGTH_SHORT).show();
                    tenbathat.setError("hông được trống");
                }else {
                    MyDB.INSERT_NHAC(
                            hinh,
                            tenbathat.getEditText().getText().toString().trim(),
                            tencasi.getEditText().getText().toString().trim()
                    );
                    Toast.makeText(manhinhnhaccanhan.this, "thanh cong", Toast.LENGTH_SHORT).show();
                    arraylistnhac.clear();
                    hienthidanhsachnhac();
                    dialog.dismiss();
                }
            }
        });

        anhnhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1,REQUREQUES_CODE_FOLDER);
            }
        });


    }


    public void Dialogsuasp(final byte[] hinh, String tennhac, final String tencasi, final int id){
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialogsuanhac);
        dialog1.show();
        anhnhacsua = dialog1.findViewById(R.id.anhnhacsua);
        final TextInputLayout tenbathatsua = dialog1.findViewById(R.id.tenbathatsua);
        final TextInputLayout tencasisua = dialog1.findViewById(R.id.tencasisua);
        Button dialogbtnsua = dialog1.findViewById(R.id.dialogbtnsua);

        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
        anhnhacsua.setImageBitmap(bitmap);
        tenbathatsua.getEditText().setText(tennhac);
        tencasisua.getEditText().setText(tencasi);


        dialogbtnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) anhnhacsua.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                //định dạng lại kiểu dữ liệu
                //100 là chất lượng hình ảnh
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                //chứa dữ liệu
                //chuyển mảng byte
                byte[] hinh = byteArrayOutputStream.toByteArray();
                String tenbaihatmoi = tenbathatsua.getEditText().getText().toString().trim();
                String tencasimoi = tencasisua.getEditText().getText().toString().trim();
                Toast.makeText(manhinhnhaccanhan.this, ""+id, Toast.LENGTH_SHORT).show();
                MyDB.UPDATE_NHAC(
                        hinh,
                        tenbathatsua.getEditText().getText().toString().trim(),
                        tencasisua.getEditText().getText().toString().trim(),
                        id
                );
                arraylistnhac.clear();
                hienthidanhsachnhac();
                dialog1.dismiss();

            }
        });

        anhnhacsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1,REQUREQUES_CODE_FOLDERSUA);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUREQUES_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                anhnhac.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUREQUES_CODE_FOLDERSUA && resultCode == RESULT_OK ){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                anhnhacsua.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void sukientoolbar() {
//        //hàm hỗ trợ cho toolbar
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        // gán icon
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//    }
    private void hienthidanhsachnhac() {
        Cursor cursor =  MyDB.getData("SELECT * FROM nhaccanhan");
        arraylistnhac.clear();
        while (cursor.moveToNext()){
            arraylistnhac.add(new listnhac(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getString(3)
            ));
        }
        adapter.notifyDataSetChanged();
    }
    public void xoabainhac(final String tenbainhacmuonxoa, final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("bạn có đồng ý xóa sản phẩm '" + tenbainhacmuonxoa + "' không ?");
        builder.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                manhinhgiaodien.MyDB.Querydata("DELETE FROM nhaccanhan WHERE Id = '" + id + "'");
                hienthidanhsachnhac();
            }
        });
        builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void anhxa1() {
        final Dialog dialog = new Dialog(this);
        toolbarnhaccanhan = findViewById(R.id.toolbarnhaccanhan);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        btndangxuat = findViewById(R.id.btndangxuat);
        tvnguoidung = findViewById(R.id.tvnguoidung);
        btnthongtin = findViewById(R.id.btnthongtin);
        floatingthem = findViewById(R.id.floatingthem1);
        listnhaccanhan = findViewById(R.id.listnhaccanhan);
    }
    private void sukientoolbar1() {
        //hàm hỗ trợ cho toolbar
        setSupportActionBar(toolbarnhaccanhan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // gán icon
        toolbarnhaccanhan.setNavigationIcon(R.drawable.ic_baseline_keyboard_arrow_left_24);
        toolbarnhaccanhan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manhinhnhaccanhan.this,manhinhgiaodien.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
    }
}