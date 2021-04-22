package com.midterm.proj.warehousemanagement.features.product.create;

import androidx.fragment.app.Fragment;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.BitmapFactory;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.dao.DAO;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
public class CreateProductFragment extends Fragment {
    private ImageView imgvProductImage;
    private EditText edtProductName;
    private EditText edtProductUnit;
    private EditText edtProductPrice;
    private Button btnAddImage;
    private Button btnAddProduct;
    private String path;
    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private static final int REQUEST_IMAGE_GALLERY = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_product, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setControl(View view) {
        imgvProductImage =  view.findViewById(R.id.imgv_product_image);
        edtProductName = view.findViewById(R.id.edt_create_product_name);
        edtProductUnit = view.findViewById(R.id.edt_create_product_unit);
        edtProductPrice = view.findViewById(R.id.edt_create_product_price);
        btnAddImage = view.findViewById(R.id.btn_add_image);
        btnAddProduct = view.findViewById(R.id.btn_confirm_new_image);
        btnAddProduct.setVisibility(View.INVISIBLE);
    }

    private void setEvent() {
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_IMAGE_CAPTURE
                    );
                } else {
                    launchGalleryImagePicker();
                }
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewProduct();
            }
        });

    }

    private void createNewProduct() {
        // add new product to database
        String name = edtProductName.getText().toString().trim();
        String unit = edtProductUnit.getText().toString().trim();
        String sPrice = edtProductPrice.getText().toString();
        int price = Integer.parseInt(sPrice);
        Bitmap bitmap = BitmapFactory.decodeFile(this.path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream.toByteArray();

        if(productValid(name,unit,sPrice,path)){
            Product product = new Product(name,unit,price,bytesImage);
            DAO.ProductQuery productQuery = new ProductQuery();
            productQuery.createProduct(product, new QueryResponse<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    //Toast.makeText(getActivity(), "Tạo sản phẩm mới thành công!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private boolean productValid(String name, String unit, String sPrice, String path) {
        if(name.length()==0){
            Toast.makeText(getActivity(), "Tên sản phẩm không được trống", Toast.LENGTH_LONG).show();
            return false;
        }else if(unit.length() == 0){
            Toast.makeText(getActivity(), "Đơn vị tính sản phẩm không được trống", Toast.LENGTH_LONG).show();
            return false;
        }else if (sPrice.length() == 0){
            Toast.makeText(getActivity(), "Giá sản phẩm không được trống", Toast.LENGTH_LONG).show();
            return false;
        }else if (path.length() == 0){
            Toast.makeText(getActivity(), "Vui lòng thêm hình ảnh của sản phẩm", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            this.path = getPathFromCameraData(data, this.getActivity());
//            Log.d("hihi", "onActivityResult: " + path);
            imgvProductImage.setImageBitmap(BitmapFactory.decodeFile(this.path));
            btnAddProduct.setVisibility(View.VISIBLE);
        }
    }

    private static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        Log.d("hihi", "getPathFromCameraData: " + picturePath);

        cursor.close();
//        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        return picturePath;
    }

    private void launchGalleryImagePicker() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);
    }
}
