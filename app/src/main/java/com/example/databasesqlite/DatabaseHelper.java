package com.example.databasesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String nameDatabase = null;
    private static final String TABLE_LOP = "lop";
    private static final String ID_LOP = "malop";
    private static final String TEN_LOP = "tenlop";
    private static final String TABLE_SV = "sinhvien";
    private static final String ID_SV = "masv";
    private static final String TEN_SV = "tensv";
    private static final String ID_MALOP = "malop";

    public String getNameDatabase() {
        return nameDatabase;
    }

    public void setNameDatabase(String nameDatabase) {
        this.nameDatabase = nameDatabase;
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        this.nameDatabase = name;
    }

    //Truy van khong tra ve ket qua
    public void queryData(String query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(query);
    }
    public boolean deldb(String name, Context context){
//        SQLiteDatabase database = getWritableDatabase();
        SQLiteDatabase database = getReadableDatabase();
       return database.deleteDatabase(context.getApplicationContext().getDatabasePath(name + ".db"));
    }
    //Truy van tra ve ket qua
    public Cursor getData(String query) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(query, null);
    }
    public Cursor getDataTable(String table) {
        SQLiteDatabase database = getReadableDatabase();
        return database.query(table, null, null, null, null, null, null);
    }
    public Cursor getDataSv(String table,String malop) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM sinhvien WHERE malop >= ?", new String[]{String.valueOf(malop)});
        return cursor;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_LOP + "(" + ID_LOP + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEN_LOP + " TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_SV + "(" + ID_SV + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEN_SV + " TEXT," + ID_MALOP + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SV);
        onCreate(sqLiteDatabase);
    }

    public boolean checkTable(String name) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=" + name;
        return getData(query).getCount() > 0 ? true : false;
    }

    public long insertDataLop(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenlop", name);
        long result = db.insert("lop", null, values);
        return result;
    }
    public boolean checkmalop(String malop){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lop WHERE malop = ?", new String[]{malop});
        return cursor.getCount() > 0;
    }
    public long insertDataSv(String name, String malop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensv", name);
        values.put("malop", malop);

        long result = db.insert("sinhvien", null, values);
        return result;
    }
    public int deleteDataLop(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("lop", "tenlop = ?", new String[] {name});
        return result;
    }
    public int updateDataLop(String nameOld,String nameNew) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenlop", nameNew);
        int result = db.update("lop", values, "tenlop = ?", new String[] {nameOld});
        return result;
    }
}
