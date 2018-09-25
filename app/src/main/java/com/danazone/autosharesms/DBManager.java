package com.danazone.autosharesms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "run_list";
    private static final String TABLE_NAME = "run";
    private static final String ID = "id";
    private static final String TIME = "phone";
    private static final String DATE = "name";


    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + "(" + ID +
                " integer primary key, " +
                TIME + " TEXT, " +
                DATE + " TEXT)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add new a student
    public void addPhone(Phone run) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, run.getName());
        values.put(DATE, run.getPhone());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /*
     Getting All Student
      */

    public List<Phone> getAllPhone() {
        List<Phone> listStudent = new ArrayList<Phone>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Phone run = new Phone();
                run.setId(cursor.getInt(0));
                run.setName(cursor.getString(1));
                run.setPhone(cursor.getString(2));

                listStudent.add(run);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listStudent;
    }

    /*
    Delete a student by ID
     */
    public void deletePhone(Phone phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(phone.getId())});
        db.close();
    }
}

