package com.midterm.proj.warehousemanagement.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import com.midterm.proj.warehousemanagement.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapHelper {
    public static String saveProductImageToFile(Product product) {
        String imagePath = "";
        byte[] image = product.getBytesImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(image,0, image.length);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + product.getName());
        try{
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            imagePath = f.getAbsolutePath();
            fo.close();
        }catch (Exception e){
            return "";
        }
        return imagePath;
    }
}
