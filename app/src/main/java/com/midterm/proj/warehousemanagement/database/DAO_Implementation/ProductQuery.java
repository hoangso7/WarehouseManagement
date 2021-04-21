package com.midterm.proj.warehousemanagement.database.DAO_Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.midterm.proj.warehousemanagement.constant.Constants;
import com.midterm.proj.warehousemanagement.database.DAO.DAO;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.SqliteDatabaseHelper;
import com.midterm.proj.warehousemanagement.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductQuery implements DAO.ProductQuery {
    private final SqliteDatabaseHelper databaseHelper = SqliteDatabaseHelper.getInstance();
    @Override
    public void createProduct(Product product, QueryResponse<Boolean> response) {
        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForProduct(product);
            long id = sqLiteDatabase.insertOrThrow(Constants.PRODUCT_TABLE, null, contentValues);
            if (id > 0) {
                response.onSuccess(true);
                product.setID_Product((int) id);
            } else
                response.onFailure("Failed to create product. Unknown Reason!");
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    private ContentValues getContentValuesForProduct(Product product) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.PRODUCT_NAME, product.getName());
        contentValues.put(Constants.PRODUCT_UNIT, product.getUnit());
        contentValues.put(Constants.PRODUCT_INSTOCK_NUMBER, product.getNumber());
        contentValues.put(Constants.PRODUCT_PRICE, product.getPrice());

        return contentValues;
    }

    @Override
    public void readProduct(int ProductID, QueryResponse<Product> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query(Constants.PRODUCT_TABLE, null,
                    Constants.PRODUCT_ID + " =? ", new String[]{String.valueOf(ProductID)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()) {
                Product product = getProductFromCursor(cursor);
                response.onSuccess(product);
            }
            else
                response.onFailure("Product not found with this ID in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void readAllProduct(QueryResponse<List<Product>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        List<Product> productsList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.PRODUCT_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Product product = getProductFromCursor(cursor);
                    productsList.add(product);
                } while (cursor.moveToNext());

                response.onSuccess(productsList);
            } else
                response.onFailure("There are no product in database");
        }catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateProduct(Product product, QueryResponse<Boolean> response) {

    }

    @Override
    public void deleteProduct(int ProductID, QueryResponse<Boolean> response) {

    }
    private Product getProductFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(Constants.PRODUCT_ID));
        String name = cursor.getString(cursor.getColumnIndex(Constants.PRODUCT_NAME));
        String unit= cursor.getString(cursor.getColumnIndex(Constants.PRODUCT_UNIT));
        int number= cursor.getInt(cursor.getColumnIndex(Constants.PRODUCT_INSTOCK_NUMBER));
        int price= cursor.getInt(cursor.getColumnIndex(Constants.PRODUCT_PRICE));

        return new Product(id,name,unit,number,price);
    }
}
