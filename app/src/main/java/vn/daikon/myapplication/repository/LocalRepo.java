package vn.daikon.myapplication.repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.prefs.Preferences;

import vn.daikon.myapplication.model.Translation;

public class LocalRepo extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "daumanhtuan.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "translation";
    private static final String KEY_ID = "id";
    private static final String KEY_FROM = "'from'";
    private static final String KEY_TO = "'to'";
    private static final String KEY_FROM2 = "from2";
    private static final String KEY_TO2 = "to2";

    public LocalRepo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_translation_table = String.format("" +
                "CREATE TABLE %s " +
                "( %s INTEGER IDENTITY(1,1) " +
                "PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT,  %s TEXT);", TABLE_NAME, KEY_ID, KEY_FROM, KEY_TO, KEY_FROM2, KEY_TO2);
        db.execSQL(create_translation_table);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);
    }

    public void addTranslation(Translation translation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FROM, translation.getFrom());
        values.put(KEY_FROM2, translation.getFrom2());
        values.put(KEY_TO, translation.getTo());
        values.put(KEY_TO2, translation.getTo2());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public int getID() {
        List<Translation>  studentList = new ArrayList<>();
        String query = "SELECT MAX(ID) FROM " + TABLE_NAME + " LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int answer = 0;
        while(cursor.isAfterLast() == false) {
            answer = cursor.getInt(0);
            cursor.moveToNext();
        }
        return answer;
    }
    public List<Translation> getAllTranslation() {
        List<Translation>  studentList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Translation translation = new Translation(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
            studentList.add(translation);
            cursor.moveToNext();
        }
        return studentList;
    }
}
