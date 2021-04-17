package com.midterm.proj.warehousemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.midterm.proj.warehousemanagement.core.ExportTicket;
import com.midterm.proj.warehousemanagement.core.ImportTicket;
import com.midterm.proj.warehousemanagement.core.Product;
import com.midterm.proj.warehousemanagement.core.Warehouse;

public class MainActivity extends AppCompatActivity {
    Button logInButton;

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();

    }

    public void test(){
        rootNode = FirebaseDatabase.getInstance();
        databaseReference = rootNode.getReference("warehouse");

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

        databaseReference.child(warehouse.getsID()).setValue(warehouse);
    }
}