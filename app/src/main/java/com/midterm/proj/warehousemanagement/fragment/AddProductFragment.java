package com.midterm.proj.warehousemanagement.fragment;

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

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.MainActivity;
import com.midterm.proj.warehousemanagement.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends Fragment {
    private static ImageView imageView;

    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private static final int REQUEST_IMAGE_GALLERY = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_product,
                container, false);

        Button button = (Button) view.findViewById(R.id.add_image);
        imageView = (ImageView) view.findViewById(R.id.gallery_image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions( // Method of Fragment
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_IMAGE_CAPTURE
                    );
                } else {
                    launchGalleryImagePicker();
                }
            }
        });
        return view;

    }

    public static String getPathFromCameraData(Intent data, Context context) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String path = getPathFromCameraData(data, this.getActivity());
            Log.d("hihi", "onActivityResult: " + path);
            imageView.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    private void launchGalleryImagePicker() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);//one can be replaced with any action code
    }
}
