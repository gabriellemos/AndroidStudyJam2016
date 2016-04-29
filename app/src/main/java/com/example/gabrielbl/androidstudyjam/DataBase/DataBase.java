package com.example.gabrielbl.androidstudyjam.DataBase;

import android.content.Context;
import android.database.sqlite.*;

/**
 * Created by gabrielbl on 26/04/16.
 */
public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CINESERCLA";
    public static final String FILME_TABLE_NAME = "FILMES";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ScriptSQL.getCreateFilme());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
