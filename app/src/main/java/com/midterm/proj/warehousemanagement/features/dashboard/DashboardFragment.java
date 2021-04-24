package com.midterm.proj.warehousemanagement.features.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.features.employee.manager.EmployeeManagerFragment;
import com.midterm.proj.warehousemanagement.features.warehouse.manager.WarehouseManagerFragment;

public class DashboardFragment extends Fragment {
    Button btn_instock, btn_tickets_list, btn_warehouse_management, btn_employee_info, btn_customer_info, btn_supplier_info;
    private FragmentActivity myContext;
    TextView edtWarehouseDashboard;
    LinearLayout dashboardMenu;
    private FragmentManager fragmentManager;
    int hidden_dashboard_status = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
    }

    private void setControl() {
        btn_instock = getView().findViewById(R.id.btn_instock);
        btn_tickets_list = getView().findViewById(R.id.btn_tickets_list);
        btn_warehouse_management = getView().findViewById(R.id.btn_warehouse_management);
        btn_employee_info = getView().findViewById(R.id.btn_employee_info);
        btn_customer_info = getView().findViewById(R.id.btn_customer_info);
        btn_supplier_info = getView().findViewById(R.id.btn_supplier_info);
        dashboardMenu = getView().findViewById(R.id.dashboard_menu);
        fragmentManager = myContext.getSupportFragmentManager();
        //edtWarehouseDashboard = getView().findViewById(R.id.textview_main_dasboard);
    }

    private void setEvent() {
        btn_warehouse_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                //edtWarehouseDashboard.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_dashboard_container, new WarehouseManagerFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_employee_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                //edtWarehouseDashboard.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_dashboard_container, new EmployeeManagerFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
