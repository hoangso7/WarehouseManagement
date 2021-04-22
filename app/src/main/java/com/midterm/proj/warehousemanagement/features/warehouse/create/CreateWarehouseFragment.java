package com.midterm.proj.warehousemanagement.features.warehouse.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.DAO.DAO;
import com.midterm.proj.warehousemanagement.database.DAO_Implementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.features.warehouse.WarehouseCrudListener;
import com.midterm.proj.warehousemanagement.features.warehouse.show.ShowWarehouseFragment;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.List;

public class CreateWarehouseFragment extends Fragment {
    private EditText edtWarehouseName, edtWarehouseAddress;
    private Button btnCreateWarehouse;
    private static WarehouseCrudListener warehouseCrudListener;
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
        btnCreateWarehouse = getView().findViewById(R.id.btn_submit_new_warehouse);
    }

    private void setEvent() {
        btnCreateWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtWarehouseName.getText().toString().trim();
                String address = edtWarehouseAddress.getText().toString().trim();

                createWarehouse(name,address);

                onDetach();
            }
        });
    }

    void createWarehouse(String name, String address){
        if(name.length()==0){
            Toast.makeText(getActivity(), "Tên kho không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        if(address.length()==0){
            Toast.makeText(getActivity(), "Địa chỉ kho không được trống", Toast.LENGTH_LONG).show();
            return;
        }

        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                for(Warehouse w : data){
                    if (w.getName().equals(name) && w.getName().equals(address)){
                        Toast.makeText(getActivity(), "Tên & Địa chỉ kho đã tồn tại", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
        Warehouse warehouse = new Warehouse(name,address);
        warehouseQuery.createWarehouse(warehouse, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                warehouseCrudListener.onWarehouseListUpdate(data);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });

    }

}
