package com.example.app_zing.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHepper extends SQLiteOpenHelper {

    public static final String DBNAME = "appzing.db";


    public DatabaseHepper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    //truy vấn select
    public Cursor getData(String sql){
        SQLiteDatabase database =getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    //truy vấn update,insert, delete
    public void Querydata(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void INSERT_NHAC(byte[] hinh , String tenbaihat, String casi){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO nhaccanhan VALUES(null,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,hinh);
        statement.bindString(2,tenbaihat);
        statement.bindString(3,casi);

        statement.executeInsert();
    }
    public void UPDATE_NHAC(byte[] hinh , String tenbaihatsua, String casisua, int id){
            SQLiteDatabase database = getWritableDatabase();
            String sql = "UPDATE nhaccanhan SET hinhanh = ?,tenbaihat = ?, casi = ? where id = ?";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();

            statement.bindBlob(1,hinh);
            statement.bindString(2,tenbaihatsua);
            statement.bindString(3,casisua);
            statement.bindString(4, String.valueOf(id));
            Log.d("dat", "dat " + id);

        statement.executeInsert();
    }



    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }


    //thêm dữ liệu user vào db
   public Boolean insertdata(String username, String password , String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email",email);
        long result = MyDB.insert("users",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    //kiểm tra user pass có tồn tại trong db chưa
    public Boolean checkusser(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username} );
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkuserpass(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password} );
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

}
