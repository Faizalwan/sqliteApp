package com.example.sqliteapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
// TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        String sql = "CREATE TABLE IF NOT EXISTS contact" +
                "(id_contact INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " + "phone TEXT, " + "type TEXT NULL, " +
                "groups TEXT NULL, " +
                "sim TEXT NULL);";
        Log.d("Data", "onCreate: " + sql); db.execSQL(sql);
        sql = "INSERT INTO contact (name, phone, type, groups, sim) " +
                "VALUES ('Abdul', '082118192021', 'Mobile', 'Family', 'SIM 1');";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
// TODO Auto-generated method stub
    }

}
