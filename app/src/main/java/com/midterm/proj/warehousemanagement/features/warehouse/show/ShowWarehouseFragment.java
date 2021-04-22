package com.midterm.proj.warehousemanagement.features.warehouse.show;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.constant.Constants;
import com.midterm.proj.warehousemanagement.database.DAO.DAO;
import com.midterm.proj.warehousemanagement.database.DAO_Implementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.features.warehouse.WarehouseCrudListener;
import com.midterm.proj.warehousemanagement.features.warehouse.create.CreateWarehouseDialogFragment;
import com.midterm.proj.warehousemanagement.features.warehouse.create.CreateWarehouseFragment;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class ShowWarehouseFragment extends Fragment implements WarehouseCrudListener {
    private ListView lvWarehouseList;
    public WarehouseAdapter warehouseAdapter;
    private FragmentManager fragmentManager;
    private FragmentActivity myContext;
    private Button btnAddWarehouse;
    private ArrayList<Warehouse> warehouses = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_warehouse_list, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setControl() {
        lvWarehouseList = getView().findViewById(R.id.lv_warehouse_list);
        btnAddWarehouse = getView().findViewById(R.id.btn_create_warehouse);
        fragmentManager = myContext.getSupportFragmentManager();

        fetchWarehouseList();
    }

    private void setEvent() {
        btnAddWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.fl_add_warehouse_inside_list, new CreateWarehouseFragment());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                CreateWarehouseDialogFragment createWarehouseDialogFragment = CreateWarehouseDialogFragment.newInstance("Tạo kho mới", ShowWarehouseFragment.this);
                createWarehouseDialogFragment.show(getFragmentManager(), "create_warehouse");
            }
        });
    }

    public void fetchWarehouseList(){
        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                warehouses.addAll(data);
                warehouseAdapter = new WarehouseAdapter(getContext(), warehouses);
                lvWarehouseList.setAdapter(warehouseAdapter);
                warehouseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void updateWarehouseList(){
        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                warehouses.clear();
                warehouses.addAll(data);
                warehouseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public void onWarehouseListUpdate(boolean isUpdated){
        if(isUpdated)
            updateWarehouseList();
    }
}
