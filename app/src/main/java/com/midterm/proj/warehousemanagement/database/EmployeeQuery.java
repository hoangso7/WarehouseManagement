package com.midterm.proj.warehousemanagement.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.midterm.proj.warehousemanagement.constant.Constants;
import com.midterm.proj.warehousemanagement.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeQuery implements QueryContract.EmployeeQuery {

    private final SqliteDatabaseHelper databaseHelper = SqliteDatabaseHelper.getInstance();

    @Override
    public void createEmployee(Employee employee, QueryResponse<Boolean> response){
        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForEmployee(employee);
            long id = sqLiteDatabase.insertOrThrow(Constants.EMPLOYEE_TABLE, null, contentValues);
            if (id > 0) {
                response.onSuccess(true);
                employee.setID_Employee((int) id);
            } else
                response.onFailure("Failed to create employee. Unknown Reason!");
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void readEmployee(int EmployeeID, QueryResponse<Employee> response){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query(Constants.EMPLOYEE_TABLE, null,
                    Constants.__EMPLOYEE_ID + " =? ", new String[]{String.valueOf(EmployeeID)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()) {
                Employee employee = getEmployeeFromCursor(cursor);
                response.onSuccess(employee);
            }
            else
                response.onFailure("Employee not found with this ID in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void readAllEmployee(QueryResponse<List<Employee>> response){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        List<Employee> employeeList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.EMPLOYEE_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Employee employee = getEmployeeFromCursor(cursor);
                    employeeList.add(employee);
                } while (cursor.moveToNext());

                response.onSuccess(employeeList);
            } else
                response.onFailure("There are no employee in database");
        }catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateEmployee(Employee employee, QueryResponse<Boolean> response){}

    @Override
    public void deleteEmployee(int employeeID, QueryResponse<Boolean> response){}

    private ContentValues getContentValuesForEmployee(Employee employee){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.EMPLOYEE_NAME, employee.getName());
        contentValues.put(Constants.EMPLOYEE_PHONE, employee.getPhone());

        return contentValues;
    }

    private Employee getEmployeeFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(Constants.__EMPLOYEE_ID));
        String name = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_PHONE));

        return new Employee(id, name, phone);
    }
}
