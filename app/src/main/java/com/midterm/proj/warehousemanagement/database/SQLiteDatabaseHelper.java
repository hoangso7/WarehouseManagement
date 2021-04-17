package com.midterm.proj.warehousemanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.midterm.proj.warehousemanagement.warehouse.ExportTicket;
import com.midterm.proj.warehousemanagement.warehouse.ImportTicket;
import com.midterm.proj.warehousemanagement.warehouse.Product;
import com.midterm.proj.warehousemanagement.warehouse.Warehouse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "warehouse_db";

    public SQLiteDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Warehouse.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Warehouse.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertWarehouse(Warehouse warehouse) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // no need to add them
        values.put(Warehouse.COLUMN_ID, warehouse.getsID());
        values.put(Warehouse.COLUMN_NAME, warehouse.getsName());
        values.put(Warehouse.COLUMN_ADDRESS, warehouse.getsAddress());

        // insert row
        long id = db.insert(Warehouse.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Warehouse getWarehouse(String wareHouseID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Warehouse.TABLE_NAME,
                new String[]{
                        Warehouse.COLUMN_ID,
                        Warehouse.COLUMN_NAME,
                        Warehouse.COLUMN_ADDRESS,
                        Warehouse.COLUMN_IMPORT_TICKETS,
                        Warehouse.COLUMN_EXPORT_TICKETS,
                        Warehouse.COLUMN_INSTOCK},
                Warehouse.COLUMN_ID + " =?",
                new String[]{wareHouseID}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Warehouse warehouse = new Warehouse(
                cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_IMPORT_TICKETS)),
                cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_EXPORT_TICKETS)),
                cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_INSTOCK)));

        cursor.close();
        return warehouse;

    }

    public int insertImportTicket(ImportTicket importTicket, String warehouseID){
        ArrayList<ImportTicket> importTickets = getAllImportTicketOfWarehouse(warehouseID);
        importTickets.add(importTicket);

        Gson gson = new Gson();
        String strJsonImportTickets = gson.toJson(importTickets);

        ContentValues values = new ContentValues();
        values.put(Warehouse.COLUMN_IMPORT_TICKETS, strJsonImportTickets);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.update(Warehouse.COLUMN_IMPORT_TICKETS, values, Warehouse.COLUMN_ID + " =?",
                new String[]{warehouseID});

    }

    public ArrayList<ImportTicket> getAllImportTicketOfWarehouse(String warehouseID){
        ArrayList<ImportTicket> importTickets = new ArrayList<>();
        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Warehouse.TABLE_NAME,
                new String[]{Warehouse.COLUMN_IMPORT_TICKETS},
                Warehouse.COLUMN_ID+ " =?",
                new String[]{String.valueOf(warehouseID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String strJsonImportTickets = cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_IMPORT_TICKETS));
        Type type = new TypeToken<ArrayList<ImportTicket>>() {}.getType();
        importTickets = gson.fromJson(strJsonImportTickets, type);
        return importTickets;
    }

    public int insertExportTicket(ExportTicket exportTicket, String warehouseID){
        ArrayList<ExportTicket> exportTickets = getAllExportTicketOfWarehouse(warehouseID);
        exportTickets.add(exportTicket);

        Gson gson = new Gson();
        String strJsonExportTickets = gson.toJson(exportTickets);

        ContentValues values = new ContentValues();
        values.put(Warehouse.COLUMN_IMPORT_TICKETS, strJsonExportTickets);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.update(Warehouse.COLUMN_EXPORT_TICKETS, values, Warehouse.COLUMN_ID + " =?",
                new String[]{warehouseID});

    }

    public ArrayList<ExportTicket> getAllExportTicketOfWarehouse(String warehouseID){
        ArrayList<ExportTicket> exportTickets = new ArrayList<>();
        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Warehouse.TABLE_NAME,
                new String[]{Warehouse.COLUMN_EXPORT_TICKETS},
                Warehouse.COLUMN_ID+ " =?",
                new String[]{String.valueOf(warehouseID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String strJsonExportTickets = cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_EXPORT_TICKETS));
        Type type = new TypeToken<ArrayList<ExportTicket>>() {}.getType();
        exportTickets = gson.fromJson(strJsonExportTickets, type);
        return exportTickets;
    }

    public int importToStock(ImportTicket ticket, String warehouseID){
        insertImportTicket(ticket, warehouseID);
        HashMap<String, Integer> inStock = getInStockOfWarehouse(warehouseID);
        Product importProduct   = ticket.getProduct();
        int iNumber            = ticket.getiNumber();
        // Check product already in stock
        if(inStock.containsKey(importProduct.getsName())){
            // Add more quantum
            inStock.put(importProduct.getsName(), inStock.get(importProduct.getsName()) + iNumber) ;
        }
        else{
            // Else, put new product to stock
            inStock.put(importProduct.getsName(), iNumber);
        }

        Gson gson = new Gson();
        String strJsonExportTickets = gson.toJson(inStock);

        ContentValues values = new ContentValues();
        values.put(Warehouse.COLUMN_INSTOCK, strJsonExportTickets);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.update(Warehouse.COLUMN_INSTOCK, values, Warehouse.COLUMN_ID + " =?",
                new String[]{warehouseID});
    }

    public int exportFromStock(ExportTicket ticket, String warehouseID){

        HashMap<String, Integer> inStock = getInStockOfWarehouse(warehouseID);

        boolean status = false;
        String sProductName = "";

        try{
            int iNumber  = ticket.getiNumber();
            sProductName = ticket.getExportedProductName();

            if(inStock.containsKey(sProductName)){
                int iProductsQuantumAvailable = inStock.get(sProductName);
                // Check product enough to be export
                if (iProductsQuantumAvailable >= iNumber) {
                    inStock.put(sProductName, inStock.get(sProductName) - iNumber);
                    insertExportTicket(ticket, warehouseID);

                    status = true;
                    Gson gson = new Gson();
                    String strJsonExportTickets = gson.toJson(inStock);

                    ContentValues values = new ContentValues();
                    values.put(Warehouse.COLUMN_INSTOCK, strJsonExportTickets);

                    SQLiteDatabase db = this.getWritableDatabase();

                    return db.update(Warehouse.COLUMN_INSTOCK, values, Warehouse.COLUMN_ID + " =?",
                            new String[]{warehouseID});
                }
            }

        }catch(NullPointerException e){

        }
        return -1;
    }

    public HashMap<String , Integer> getInStockOfWarehouse(String warehouseID){
        HashMap<String, Integer> instock = new HashMap<>();
        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Warehouse.TABLE_NAME,
                new String[]{Warehouse.COLUMN_INSTOCK},
                Warehouse.COLUMN_ID+ " =?",
                new String[]{String.valueOf(warehouseID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String strJsonInstock = cursor.getString(cursor.getColumnIndex(Warehouse.COLUMN_INSTOCK));
        Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
        instock = gson.fromJson(strJsonInstock, type);
        return instock;
    }
}
