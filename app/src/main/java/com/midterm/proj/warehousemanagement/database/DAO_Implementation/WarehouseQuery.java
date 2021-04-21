package com.midterm.proj.warehousemanagement.database.DAO_Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.midterm.proj.warehousemanagement.constant.Constants;
import com.midterm.proj.warehousemanagement.database.DAO.DAO;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.SqliteDatabaseHelper;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class WarehouseQuery implements DAO.WarehouseQuery {
    private final SqliteDatabaseHelper databaseHelper = SqliteDatabaseHelper.getInstance();

    @Override
    public void createWarehouse(Warehouse warehouse, QueryResponse<Boolean> response) {
        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForWarehouse(warehouse);
            long id = sqLiteDatabase.insertOrThrow(Constants.WAREHOUSE_TABLE, null, contentValues);
            if (id > 0) {
                response.onSuccess(true);
                warehouse.setID_Warehouse((int) id);
            } else
                response.onFailure("Failed to create warehouse. Unknown Reason!");
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    private ContentValues getContentValuesForWarehouse(Warehouse warehouse) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.WAREHOUSE_NAME, warehouse.getName());
        contentValues.put(Constants.WAREHOUSE_ADDRESS, warehouse.getAddress());

        return contentValues;
    }

    @Override
    public void readWarehouse(int WarehouseID, QueryResponse<Warehouse> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query(Constants.WAREHOUSE_TABLE, null,
                    Constants.__WAREHOUSE_ID + " =? ", new String[]{String.valueOf(WarehouseID)},
                    null, null, null,null);

            if(cursor!=null && cursor.moveToFirst()) {
                Warehouse warehouse = getWarehouseFromCursor(cursor);
                response.onSuccess(warehouse);
            }
            else
                response.onFailure("Warehouse not found with this ID in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void readAllWarehouse(QueryResponse<List<Warehouse>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        List<Warehouse> warehousesList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.WAREHOUSE_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Warehouse warehouse = getWarehouseFromCursor(cursor);
                    warehousesList.add(warehouse);
                } while (cursor.moveToNext());

                response.onSuccess(warehousesList);
            } else
                response.onFailure("There are no warehouse in database");
        }catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateWarehouse(Warehouse warehouse, QueryResponse<Boolean> response) {

    }

    @Override
    public void deleteWarehouse(int WarehouseID, QueryResponse<Boolean> response) {

    }

    private Warehouse getWarehouseFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(Constants.__WAREHOUSE_ID));
        String name = cursor.getString(cursor.getColumnIndex(Constants.WAREHOUSE_NAME));
        String address = cursor.getString(cursor.getColumnIndex(Constants.WAREHOUSE_ADDRESS));

        return new Warehouse(id, name, address);
    }
}
