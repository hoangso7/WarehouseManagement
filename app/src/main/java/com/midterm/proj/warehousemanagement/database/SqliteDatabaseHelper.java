package com.midterm.proj.warehousemanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.midterm.proj.warehousemanagement.constant.CreateTable;
import com.midterm.proj.warehousemanagement.constant.DropTable;

public class SqliteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WarehouseDB";
    private static final int DATABASE_VERSION = 1;
    private static SqliteDatabaseHelper databaseHelper;

    public SqliteDatabaseHelper(Context context) {
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
        db.execSQL(CreateTable.CREATE_SUPPLIER_TABLE);
        db.execSQL(CreateTable.CREATE_EMPLOYEE_TABLE);
        db.execSQL(CreateTable.CREATE_PRODUCT_TABLE);
        db.execSQL(CreateTable.CREATE_WAREHOUSE_TABLE);
        db.execSQL(CreateTable.CREATE_CUSTOMER_TABLE);
        db.execSQL(CreateTable.CREATE_IMPORT_TICKET_TABLE);
        db.execSQL(CreateTable.CREATE_EXPORT_TICKET_TABLE);
        db.execSQL(CreateTable.CREATE_EXPORT_TICKET_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DropTable.DROP_CUSTOMER_TABLE);
        db.execSQL(DropTable.DROP_EMPLOYEE_TABLE);
        db.execSQL(DropTable.DROP_EXPORT_TICKET_DETAIL_TABLE);
        db.execSQL(DropTable.DROP_EXPORT_TICKET_TABLE);
        db.execSQL(DropTable.DROP_IMPORT_TICKET_TABLE);
        db.execSQL(DropTable.DROP_PRODUCT_TABLE);
        db.execSQL(DropTable.DROP_SUPPLIER_TABLE);
        db.execSQL(DropTable.DROP_WAREHOUSE_TABLE);
    }
}
