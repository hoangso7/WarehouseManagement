package com.midterm.proj.warehousemanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WarehouseDB";
    private static final int DATABASE_VERSION = 1;
    private static SqliteDatabaseHelper databaseHelper;

    private SqliteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SqliteDatabaseHelper getInstance(Context context ) {
        if (databaseHelper == null) {
            synchronized (SqliteDatabaseHelper.class){ //thread safe singleton
                if (databaseHelper == null)
                    databaseHelper = new SqliteDatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
