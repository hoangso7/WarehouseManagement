package com.midterm.proj.warehousemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.midterm.proj.warehousemanagement.fragment.AddExportTicketFragment;
import com.midterm.proj.warehousemanagement.fragment.AddImportTicketFragment;
import com.midterm.proj.warehousemanagement.fragment.AddProductFragment;
import com.midterm.proj.warehousemanagement.fragment.WarehouseManagementFragment;
import com.midterm.proj.warehousemanagement.warehouse.ExportTicket;
import com.midterm.proj.warehousemanagement.warehouse.ImportTicket;
import com.midterm.proj.warehousemanagement.warehouse.Product;
import com.midterm.proj.warehousemanagement.warehouse.Warehouse;

public class MainActivity extends AppCompatActivity {
//    Button logInButton;
private ActionBar toolbar;
    BottomNavigationView bottomNavigationView;

//    FirebaseDatabase rootNode;
//    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test();
        setControl();
        setEvent();
    }

    private void setEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private void setControl() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
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

    public void test(){
//        rootNode = FirebaseDatabase.getInstance();
//        databaseReference = rootNode.getReference("warehouse");

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

//        databaseReference.child(warehouse.getsID()).setValue(warehouse);
    }
}