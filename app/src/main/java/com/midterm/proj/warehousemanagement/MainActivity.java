package com.midterm.proj.warehousemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.midterm.proj.warehousemanagement.database.SQLiteDatabaseHelper;
import com.midterm.proj.warehousemanagement.fragment.AddExportTicketFragment;
import com.midterm.proj.warehousemanagement.fragment.AddImportTicketFragment;
import com.midterm.proj.warehousemanagement.fragment.AddProductFragment;
import com.midterm.proj.warehousemanagement.fragment.WarehouseManagementFragment;
import com.midterm.proj.warehousemanagement.warehouse.ExportTicket;
import com.midterm.proj.warehousemanagement.warehouse.ImportTicket;
import com.midterm.proj.warehousemanagement.warehouse.Product;
import com.midterm.proj.warehousemanagement.warehouse.Ticket;
import com.midterm.proj.warehousemanagement.warehouse.TicketInfoAdapter;
import com.midterm.proj.warehousemanagement.warehouse.Warehouse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;
    SQLiteDatabaseHelper sqlDatabase;
    public static List<Warehouse> warehouses = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    private ListView listView;
    private TicketInfoAdapter ticketInfoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test();
        setDatabase();
        setControl();
        setEvent();
    }

    private void setDatabase() {
        //sqlDatabase = new SQLiteDatabaseHelper(this);
        createTestData();
        //sqlDatabase.insertWarehouse(warehouses.get(0));
    }

    private void setEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WarehouseManagementFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.item_warehouses_management:
                            selectedFragment = new WarehouseManagementFragment();
                            break;
                        case R.id.item_import_ticket:
                            selectedFragment = new AddImportTicketFragment();
                            break;
                        case R.id.item_export_ticket:
                            selectedFragment = new AddExportTicketFragment();
                            break;
                        case R.id.item_add_product:
                            selectedFragment = new AddProductFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    public void createTestData(){
        Warehouse warehouse = new Warehouse("K1","Thủ Đức","69 Đặng Văn Bi");

        Product product = new Product("GO","Gạch ống","Thanh Hóa");
        ImportTicket importTicket = new ImportTicket(product,1999,"Viên");
        warehouse.importToStock(importTicket);

        Product product2 = new Product("SA","Sắt tròn","Nam Định");
        ImportTicket importTicket2 = new ImportTicket(product2,2000,"Thùng");
        warehouse.importToStock(importTicket2);

        ImportTicket importTicket3 = new ImportTicket(product,100,"Viên");
        warehouse.importToStock(importTicket3);

        ExportTicket exportTicket = new ExportTicket(product2, 155, "Thùng", "anh Hoàng, đường số 2 quận 9");
        warehouse.exportFromStock(exportTicket);

        warehouses.add(warehouse);

        Warehouse warehouse22 = new Warehouse("Q9","Quận 9","87/8 đường 379");

        Product product22 = new Product("XM","Xi Măng","Cát Tiên");
        ImportTicket importTicket22 = new ImportTicket(product22,8985,"Bao");
        warehouse22.importToStock(importTicket22);

        Product product23 = new Product("SC","Sắt cây","Nam Định");
        ImportTicket importTicket23 = new ImportTicket(product23,3456,"Cây");
        warehouse22.importToStock(importTicket23);



        ExportTicket exportTicket33 = new ExportTicket(product22, 888, "Bao", "anh Hoàng, đường số 2 quận 9");
        warehouse22.exportFromStock(exportTicket33);

        ImportTicket importTicket33 = new ImportTicket(product23,234,"Cây");
        warehouse22.importToStock(importTicket33);
        ImportTicket importTicket34 = new ImportTicket(product23,555,"Cây");
        warehouse22.importToStock(importTicket34);

        warehouses.add(warehouse22);


    }


}