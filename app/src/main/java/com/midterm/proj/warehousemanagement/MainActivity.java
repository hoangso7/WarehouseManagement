package com.midterm.proj.warehousemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.midterm.proj.warehousemanagement.database.SqliteDatabaseHelper;
import com.midterm.proj.warehousemanagement.features.dashboard.DashboardFragment;
import com.midterm.proj.warehousemanagement.features.export_ticket.create.CreateExportTicketFragment;
import com.midterm.proj.warehousemanagement.features.import_ticket.create.CreateImportTicketFragment;
import com.midterm.proj.warehousemanagement.features.product.create.CreateProductFragment;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;
    public static List<Warehouse> warehouses = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    private ListView listView;
    private Activity mActivity;
    private Context mContext;
    //private TicketInfoAdapter ticketInfoAdapter;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mActivity = MainActivity.this;
        checkPermission();
        setDatabase();
        setControl();
        setEvent();
    }

    private void setDatabase() {
//        SqliteDatabaseHelper db = new SqliteDatabaseHelper();
        createSqlFakeData();
        SqliteDatabaseHelper databaseHelper = SqliteDatabaseHelper.getInstance();
        String dbName = databaseHelper.getDatabaseName();
        Log.d("DATABASE NAME", dbName);
        //createTestData();
    }

    private void setEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
        //SqliteDatabaseHelper sqliteDatabaseHelper = new SQLiteDatabase();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.item_warehouses_management:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.item_import_ticket:
                            selectedFragment = new CreateImportTicketFragment();
                            break;
                        case R.id.item_export_ticket:
                            selectedFragment = new CreateExportTicketFragment();
                            break;
                        case R.id.item_add_product:
                            selectedFragment = new CreateProductFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };


    public void createSqlFakeData(){}

    protected void checkPermission(){
        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.CAMERA)
                    ||  ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("Yêu cầu quyền truy cập Camera và bộ nhớ để" +
                        " thực thi chương trình.");
                builder.setTitle("Vui lòng cấp quyền cho ứng dụng");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                mActivity,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            //Toast.makeText(mContext,"Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        + grantResults[1]

                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    // Permissions are granted
                    //Toast.makeText(mContext,"Permissions granted.",Toast.LENGTH_SHORT).show();
                }else {
                    // Permissions are denied
                    Toast.makeText(mContext,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}