package com.midterm.proj.warehousemanagement.features.supplier.show;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.CustomerQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.SupplierQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.customer.show.ShowCustomerListAdapter;
import com.midterm.proj.warehousemanagement.model.Customer;
import com.midterm.proj.warehousemanagement.model.Supplier;
import com.midterm.proj.warehousemanagement.util.ThirdPartyApp;

import java.util.ArrayList;
import java.util.List;

public class ShowSupplierListFragment extends Fragment {
    private ListView lvSupplierList;
    private ShowSupplierListAdapter adapter;
    private ArrayList<Supplier> supplierArrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_supplier_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setControl() {
        lvSupplierList = getView().findViewById(R.id.lv_supplier_list);
        fetchCustomerList();
    }

    private void setEvent() {
        lvSupplierList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = supplierArrayList.get(position).getAddress();
                ThirdPartyApp.googlemapSearchForAddress(address);
            }
        });
    }
    public void fetchCustomerList(){
        DAO.SupplierQuery supplierQuery = new SupplierQuery();
        supplierQuery.readAllSupplier(new QueryResponse<List<Supplier>>() {
            @Override
            public void onSuccess(List<Supplier> data) {
                supplierArrayList.addAll(data);
                adapter = new ShowSupplierListAdapter(getContext(), supplierArrayList);
                lvSupplierList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
