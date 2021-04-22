package com.midterm.proj.warehousemanagement.features.employee.show;

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

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.database.daoImplementation.EmployeeQuery;
import com.midterm.proj.warehousemanagement.features.employee.EmployeeCrudListener;
import com.midterm.proj.warehousemanagement.features.employee.create.CreateEmployeeDialogFragment;
import com.midterm.proj.warehousemanagement.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class ShowEmployeeFragment extends Fragment implements EmployeeCrudListener {
    private ListView lvEmployeeList;
    public EmployeeAdapter employeeAdapter;
    private FragmentManager fragmentManager;
    private FragmentActivity myContext;
    private Button btnAddEmployee;
    private ArrayList<Employee> employees = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee_list, container, false);
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
        lvEmployeeList = getView().findViewById(R.id.lv_employee_list);
        btnAddEmployee = getView().findViewById(R.id.btn_create_employee);
        fragmentManager = myContext.getSupportFragmentManager();

        fetchWarehouseList();
    }

    private void fetchWarehouseList() {
        DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
        employeeQuery.readAllEmployee(new QueryResponse<List<Employee>>() {
            @Override
            public void onSuccess(List<Employee> data) {
                employees.addAll(data);
                employeeAdapter = new EmployeeAdapter(getContext(), employees);
                lvEmployeeList.setAdapter(employeeAdapter);
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void setEvent() {
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEmployeeDialogFragment createEmployeeDialogFragment = CreateEmployeeDialogFragment.newInstance("Thêm nhân viên mới", ShowEmployeeFragment.this);
                createEmployeeDialogFragment.show(getFragmentManager(), "create_warehouse");
            }
        });
    }

    @Override
    public void onEmployeeListUpdate(boolean isUpdated){
        if(isUpdated)
            updateEmployeeList();
    }

    private void updateEmployeeList() {
        DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
        employeeQuery.readAllEmployee(new QueryResponse<List<Employee>>() {
            @Override
            public void onSuccess(List<Employee> data) {
                employees.clear();
                employees.addAll(data);
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
