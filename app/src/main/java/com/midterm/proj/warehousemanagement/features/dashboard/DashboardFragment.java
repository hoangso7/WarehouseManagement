package com.midterm.proj.warehousemanagement.features.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.customer.show.ShowCustomerListFragment;
import com.midterm.proj.warehousemanagement.features.employee.manager.EmployeeManagerFragment;
import com.midterm.proj.warehousemanagement.features.export_ticket.show.ShowExportTicketFragment;
import com.midterm.proj.warehousemanagement.features.import_ticket.show.ShowImportTicketFragment;
import com.midterm.proj.warehousemanagement.features.product.show.ShowInstockFragment;
import com.midterm.proj.warehousemanagement.features.supplier.show.ShowSupplierListFragment;
import com.midterm.proj.warehousemanagement.features.warehouse.manager.WarehouseManagerFragment;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.List;

public class DashboardFragment extends Fragment{
    Button btn_instock, btn_tickets_list, btn_warehouse_management, btn_employee_info, btn_customer_info, btn_supplier_info;
    private FragmentActivity myContext;
    LinearLayout dashboardMenu;

    // Animation
    Animation ani1, ani2, ani3, ani4, ani5, ani6;
    LinearLayout item1, item2, item3, item4, item5, item6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkEmptyWarehouseList();
        setControl();
        setEvent();

        ani1 = AnimationUtils.loadAnimation(myContext, R.anim.thuancute_animation);
        ani2 = AnimationUtils.loadAnimation(myContext, R.anim.ani2);
        ani3 = AnimationUtils.loadAnimation(myContext, R.anim.ani3);
        ani4 = AnimationUtils.loadAnimation(myContext, R.anim.ani4);
        ani5 = AnimationUtils.loadAnimation(myContext, R.anim.ani5);
        ani6 = AnimationUtils.loadAnimation(myContext, R.anim.ani6);

        item1 = getView().findViewById(R.id.item_main_1);
        item2 = getView().findViewById(R.id.item_main_2);
        item3 = getView().findViewById(R.id.item_main_3);
        item4 = getView().findViewById(R.id.item_main_4);
        item5 = getView().findViewById(R.id.item_main_5);
        item6 = getView().findViewById(R.id.item_main_6);

        ani1.setStartOffset(200);
        ani2.setStartOffset(400);
        ani3.setStartOffset(600);
        ani4.setStartOffset(800);
        ani5.setStartOffset(1000);
        ani6.setStartOffset(1200);

        item1.setAnimation(ani1);

        item2.setAnimation(ani2);

        item3.setAnimation(ani3);
        item4.setAnimation(ani4);
        item5.setAnimation(ani5);
        item6.setAnimation(ani6);
    }

    private void checkEmptyWarehouseList() {
        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                if(data.isEmpty()){
                    askForFirstWarehouse();
                }
            }

            @Override
            public void onFailure(String message) {
                askForFirstWarehouse();
            }
        });
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



    }

    private void setEvent() {
        btn_instock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new ShowInstockFragment());
                fragmentTransaction.commit();
            }
        });
        btn_tickets_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new ImportExportFragment());
                fragmentTransaction.commit();
            }
        });
        btn_warehouse_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new WarehouseManagerFragment());
                fragmentTransaction.commit();
            }
        });

        btn_employee_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new EmployeeManagerFragment());
                fragmentTransaction.commit();
            }
        });

        btn_customer_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new ShowCustomerListFragment());
                fragmentTransaction.commit();
            }
        });

        btn_supplier_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new ShowSupplierListFragment());
                fragmentTransaction.commit();
            }
        });
    }

    private void askForFirstWarehouse(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Danh sách nhà kho trống!");
        builder.setMessage("Hãy bắt đầu bằng cách thêm một nhà kho mới...");
        builder.setIcon(R.drawable._warehouse);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dashboardMenu.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new WarehouseManagerFragment());
                fragmentTransaction.commit();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//
//    @Override
//    public void onCrudCallback(String message){
//        Log.d("onCrudCallback: ", message);
//    }
}
