package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG="ChatDatabaseHelper";
    static String DATABASE_NAME="Messages.db";
    static Integer VERSION_NUM=10;
    final static String KEY_ID="ID";
    final static String KEY_MESSAGE="MESSAGE";
    public final static String KEY_TABLE="DATABASE_TABLE";
    private static final String DATABASE_CREATE="create table " + KEY_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_MESSAGE + " text not null);";
    public ChatDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME, null,VERSION_NUM);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.i(TAG, "Calling onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLE);
        onCreate(db);
        Log.i(TAG, "Calling onUpgrade, oldVersion = "+oldVersion+" newVersion = "+newVersion);
    }




}
