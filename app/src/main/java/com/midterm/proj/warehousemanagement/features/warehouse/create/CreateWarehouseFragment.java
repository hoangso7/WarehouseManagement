package com.midterm.proj.warehousemanagement.features.warehouse.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.DAO.DAO;
import com.midterm.proj.warehousemanagement.database.DAO_Implementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.model.Warehouse;

public class CreateWarehouseFragment extends Fragment {
    private EditText edtWarehouseName, edtWarehouseAddress;
    private Button btnCreateWarehouse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_warehouse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setControl() {
        edtWarehouseAddress = getView().findViewById(R.id.edt_create_warehouse_address);
        edtWarehouseName = getView().findViewById(R.id.edt_create_warehouse_name);
        btnCreateWarehouse = getView().findViewById(R.id.btn_create_warehouse);
    }

    private void setEvent() {

    }

    void createWarehouse(String name, String address){
        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        Warehouse warehouse = new Warehouse(name,address);
        warehouseQuery.createWarehouse(warehouse, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

}
