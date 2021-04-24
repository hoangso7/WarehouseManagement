package com.midterm.proj.warehousemanagement.features.customer.show;

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
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.product.show.ShowProductListAdapter;
import com.midterm.proj.warehousemanagement.model.Customer;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.util.ThirdPartyApp;

import java.util.ArrayList;
import java.util.List;

public class ShowCustomerListFragment extends Fragment {
    private ListView lvCustomerList;
    private ShowCustomerListAdapter adapter;
    private ArrayList<Customer> customerArrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setControl() {
        lvCustomerList = getView().findViewById(R.id.lv_customer_list);
        fetchCustomerList();
    }

    private void setEvent() {
        lvCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = customerArrayList.get(position).getPhone();
                ThirdPartyApp.dialPhoneNumber(phone);
            }
        });
    }
    public void fetchCustomerList(){
        DAO.CustomerQuery customerQuery = new CustomerQuery();
        customerQuery.readAllCustomer(new QueryResponse<List<Customer>>() {
            @Override
            public void onSuccess(List<Customer> data) {
                customerArrayList.addAll(data);
                adapter = new ShowCustomerListAdapter(getContext(), customerArrayList);
                lvCustomerList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
