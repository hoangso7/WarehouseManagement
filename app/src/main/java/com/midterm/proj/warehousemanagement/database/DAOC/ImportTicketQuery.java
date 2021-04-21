package com.midterm.proj.warehousemanagement.database.DAOC;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.midterm.proj.warehousemanagement.constant.Constants;
import com.midterm.proj.warehousemanagement.database.DAO.DataAccessObject;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.SqliteDatabaseHelper;
import com.midterm.proj.warehousemanagement.model.ImportTicket;

import java.util.ArrayList;
import java.util.List;

public class ImportTicketQuery implements DataAccessObject.ImportTicketQuery{
    private final SqliteDatabaseHelper databaseHelper = SqliteDatabaseHelper.getInstance();

    @Override
    public void createImportTicket(int pkWarehouseID, int pkEmployeeID,int fkProductID, int fkSupplierID,ImportTicket importTicket, QueryResponse<Boolean> response){

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        // put the primary key
        contentValues.put(Constants._WAREHOUSE_ID, pkWarehouseID);
        contentValues.put(Constants._EMPLOYEE_ID, pkEmployeeID);
        // put creation date & num of products
        contentValues.put(Constants.IMPORT_TICKET_CREATION_DATE, importTicket.getCreateDate());
        contentValues.put(Constants.IMPORT_TICKET_NUMBER_OF_PRODUCTS, importTicket.getNumber());
        // put the foreign key
        contentValues.put(Constants.IMPORT_TICKET_PRODUCTS_ID_FK, fkProductID);
        contentValues.put(Constants.IMPORT_TICKET_SUPPLIER_ID_FK, fkSupplierID);
        try{
            long rowCount = sqLiteDatabase.insertOrThrow(Constants.IMPORT_TICKET_TABLE, null,contentValues);

            if (rowCount > 0)
                response.onSuccess(true);
            else
                response.onFailure("Create import ticket failed!");

        }catch (SQLiteException e){
            response.onFailure(e.getMessage());
        }finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void readImportTicket(int ImportTicketID, QueryResponse<ImportTicket> response){
//        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
//        //  select * from import_ticket_table where warehouse_id = 1
//        String QUERY = "SELECT * FROM "
//                +Constants.IMPORT_TICKET_TABLE + " WHERE "
//                +Constants._WAREHOUSE_ID + " = "
//                +ImportTicketID;
//        Cursor cursor = null;
//        try{
//            ImportTicket importTicket = new ImportTicket();
//            cursor = sqLiteDatabase.rawQuery(QUERY,null);
//            if(cursor.moveToFirst()){
//                importTicket.setID_Employee(cursor.getInt(Integer.parseInt(Constants._EMPLOYEE_ID)));
//                importTicket.setID_Warehouse(cursor.getInt(Integer.parseInt(Constants._WAREHOUSE_ID)));
//                importTicket.setCreateDate(cursor.getString(cursor.getColumnIndex(Constants.IMPORT_TICKET_CREATION_DATE)));
//                importTicket.setNumber(cursor.getInt(Integer.parseInt(Constants.IMPORT_TICKET_NUMBER_OF_PRODUCTS)));
//                importTicket.setProductID(cursor.getInt(Integer.parseInt(Constants.IMPORT_TICKET_PRODUCTS_ID_FK)));
//                importTicket.setSupplierID(cursor.getInt(Integer.parseInt(Constants.IMPORT_TICKET_SUPPLIER_ID_FK)));
//
//                response.onSuccess(importTicket);
//            }
//            else
//                response.onFailure("Could not find the ticket with provied ID");
//        }catch (Exception e){
//            response.onFailure(e.getMessage());
//        }finally {
//            sqLiteDatabase.close();
//            if(cursor != null)
//                cursor.close();
//        }
    }

    @Override
    public void readAllImpoprtTicketFromWarehouse(int WarehouseID, QueryResponse<List<ImportTicket>> response){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        //  select * from import_ticket_table where warehouse_id = 1
        String QUERY = "SELECT * FROM "
                +Constants.IMPORT_TICKET_TABLE + " WHERE "
                +Constants._WAREHOUSE_ID + " = "
                +WarehouseID;
        Cursor cursor = null;
        try{
            List<ImportTicket> importTickets= new ArrayList<>();
            cursor = sqLiteDatabase.rawQuery(QUERY,null);

            if(cursor.moveToFirst()) {
                do {
                    ImportTicket importTicket = new ImportTicket();

                    importTicket.setID_Employee(cursor.getInt(Integer.parseInt(Constants._EMPLOYEE_ID)));
                    importTicket.setID_Warehouse(cursor.getInt(Integer.parseInt(Constants._WAREHOUSE_ID)));
                    importTicket.setCreateDate(cursor.getString(cursor.getColumnIndex(Constants.IMPORT_TICKET_CREATION_DATE)));
                    importTicket.setNumber(cursor.getInt(Integer.parseInt(Constants.IMPORT_TICKET_NUMBER_OF_PRODUCTS)));
                    importTicket.setProductID(cursor.getInt(Integer.parseInt(Constants.IMPORT_TICKET_PRODUCTS_ID_FK)));
                    importTicket.setSupplierID(cursor.getInt(Integer.parseInt(Constants.IMPORT_TICKET_SUPPLIER_ID_FK)));

                    importTickets.add(importTicket);
                } while (cursor.moveToNext());

                response.onSuccess(importTickets);
            }
        }catch (Exception e){
            response.onFailure(e.getMessage());
        }finally {
            sqLiteDatabase.close();
            if(cursor != null)
                cursor.close();
        }
    }

}
