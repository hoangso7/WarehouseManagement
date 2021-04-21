package com.midterm.proj.warehousemanagement.features.warehouse.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.DAO.DAO;
import com.midterm.proj.warehousemanagement.database.DAO_Implementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class WarehouseManagerFragment extends Fragment {
    private static int numOfWarehouse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_warehouse_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean oneWarehouseCreated() {
        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        final boolean[] isWarehouseCreated = {false};
        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                ArrayList<Warehouse> warehouses = new ArrayList<>(data);
                numOfWarehouse = warehouses.size();
                isWarehouseCreated[0] = warehouses.isEmpty();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
        return isWarehouseCreated[0];
    }
}
