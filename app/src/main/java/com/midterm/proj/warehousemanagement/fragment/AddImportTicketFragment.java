package com.midterm.proj.warehousemanagement.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.MainActivity;
import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class AddImportTicketFragment extends Fragment{
    private Spinner spnWarehouse;
    private TextView tvWarehouseID, tvWarehouseName, tvWarehouseAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setControl() {
        spnWarehouse = getView().findViewById(R.id.spiner_choose_warehouse);
        tvWarehouseID = getView().findViewById(R.id.textview_warehouseID);
        tvWarehouseName = getView().findViewById(R.id.textview_warehouseName);
        tvWarehouseAddress = getView().findViewById(R.id.textview_warehouseAddress);
    }
    private void setEvent() {
        List<String> warehouseNameList = new ArrayList<>();
        for (Warehouse w: MainActivity.warehouses) {
            warehouseNameList.add(w.getsName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getView().getContext(),R.layout.spiner_item,warehouseNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spnWarehouse.setAdapter(dataAdapter);
        spnWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                tvWarehouseID.setText(MainActivity.warehouses.get(i).getsID());
                tvWarehouseName.setText(MainActivity.warehouses.get(i).getsName());
                tvWarehouseAddress.setText(MainActivity.warehouses.get(i).getsAddress());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
