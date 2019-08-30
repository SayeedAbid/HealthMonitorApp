package com.example.healthmonitorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "medicineessssssssss.db";
    public static final String TABLE_NAME = "medicine_table";
    public static final String COL_1 = "medicine_id";
    public static final String COL_2 = "medicine_name";
    public static final String COL_3 = "medicine_unit";
    public static final String COL_4 = "medicine_time";
    public static final String COL_5 = "medicine_note";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 10685);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, medicine_name TEXT," +
                "medicine_unit TEXT," +
                "medicine_time TEXT,medicine_note TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String unit ,String time, String note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,unit);
        contentValues.put(COL_4,time);
        contentValues.put(COL_5,note);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return res;
    }
}
