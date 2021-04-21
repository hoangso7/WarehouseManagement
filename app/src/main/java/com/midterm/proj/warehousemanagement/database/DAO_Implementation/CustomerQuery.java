package com.midterm.proj.warehousemanagement.database.DAO_Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.midterm.proj.warehousemanagement.constant.Constants;
import com.midterm.proj.warehousemanagement.database.DAO.DAO;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.SqliteDatabaseHelper;
import com.midterm.proj.warehousemanagement.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerQuery implements DAO.CustomerQuery {

    private final SqliteDatabaseHelper databaseHelper = SqliteDatabaseHelper.getInstance();

    @Override
    public void createCustomer(Customer customer, QueryResponse<Boolean> response){
        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForCustomer(customer);
            long id = sqLiteDatabase.insertOrThrow(Constants.CUSTOMER_TABLE, null, contentValues);
            if (id > 0) {
                response.onSuccess(true);
                customer.setCustomerID((int) id);
            } else
                response.onFailure("Failed to create customer. Unknown Reason!");
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void readCustomer(int CustomerID, QueryResponse<Customer> response){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query(Constants.CUSTOMER_TABLE, null,
                    Constants.CUSTOMER_ID + " =? ",
                    new String[]{String.valueOf(CustomerID)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()) {
                Customer customer = getCustomerFromCursor(cursor);
                response.onSuccess(customer);
            }
            else
                response.onFailure("Customer not found with this ID in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void readAllCustomer(QueryResponse<List<Customer>> response){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        List<Customer> customerList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.CUSTOMER_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Customer customer = getCustomerFromCursor(cursor);
                    customerList.add(customer);
                } while (cursor.moveToNext());

                response.onSuccess(customerList);
            } else
                response.onFailure("There are no customer in database");
        }catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateCustomer(Customer customer, QueryResponse<Boolean> response){}

    @Override
    public void deleteCustomer(int customerID, QueryResponse<Boolean> response){}

    private ContentValues getContentValuesForCustomer(Customer customer){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.CUSTOMER_NAME, customer.getName());
        contentValues.put(Constants.CUSTOMER_PHONE, customer.getPhone());

        return contentValues;
    }

    private Customer getCustomerFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(Constants.CUSTOMER_ID));
        String name = cursor.getString(cursor.getColumnIndex(Constants.CUSTOMER_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(Constants.CUSTOMER_PHONE));

        return new Customer(id, name, phone);
    }
}
