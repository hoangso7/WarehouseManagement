package com.midterm.proj.warehousemanagement.database.daoImplementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.midterm.proj.warehousemanagement.constant.Constants;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.SqliteDatabaseHelper;
import com.midterm.proj.warehousemanagement.model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class SupplierQuery implements DAO.SupplierQuery {

    private final SqliteDatabaseHelper databaseHelper = SqliteDatabaseHelper.getInstance();

    @Override
    public void createSupplier(Supplier supplier, QueryResponse<Boolean> response){
        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForSupplier(supplier);
            long id = sqLiteDatabase.insertOrThrow(Constants.EMPLOYEE_TABLE, null, contentValues);
            if (id > 0) {
                response.onSuccess(true);
                supplier.setID_Supplier((int) id);
            } else
                response.onFailure("Failed to create supplier. Unknown Reason!");
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void readSupplier(int SupplierID, QueryResponse<Supplier> response){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query(Constants.SUPPLIER_TABLE, null,
                    Constants._SUPPLIER_ID + " =? ", new String[]{String.valueOf(SupplierID)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()) {
                Supplier supplier = getSupplierFromCursor(cursor);
                response.onSuccess(supplier);
            }
            else
                response.onFailure("Supplier not found with this ID in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void readAllSupplier(QueryResponse<List<Supplier>> response){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        List<Supplier> supplierList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.SUPPLIER_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Supplier supplier = getSupplierFromCursor(cursor);
                    supplierList.add(supplier);
                } while (cursor.moveToNext());

                response.onSuccess(supplierList);
            } else
                response.onFailure("There are no supplier in database");
        }catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateSupplier(Supplier supplier, QueryResponse<Boolean> response){}

    @Override
    public void deleteSupplier(int supplierID, QueryResponse<Boolean> response){}

    private ContentValues getContentValuesForSupplier(Supplier supplier){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.SUPPLIER_NAME, supplier.getName());
        contentValues.put(Constants.SUPPLIER_ADDRESS, supplier.getAddress());

        return contentValues;
    }

    private Supplier getSupplierFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(Constants._SUPPLIER_ID));
        String name = cursor.getString(cursor.getColumnIndex(Constants.SUPPLIER_NAME));
        String address = cursor.getString(cursor.getColumnIndex(Constants.SUPPLIER_ADDRESS));

        return new Supplier(id, name, address);
    }
}