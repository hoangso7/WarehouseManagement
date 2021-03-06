package com.midterm.proj.warehousemanagement.features.customer.show;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.CustomerQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.EmployeeQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.customer.CustomerCrudListener;
import com.midterm.proj.warehousemanagement.features.customer.update.UpdateCustomerDialogFragment;
import com.midterm.proj.warehousemanagement.features.employee.show.ShowEmployeeFragment;
import com.midterm.proj.warehousemanagement.features.employee.update.UpdateEmployeeDialogFragment;
import com.midterm.proj.warehousemanagement.features.product.show.ShowProductListAdapter;
import com.midterm.proj.warehousemanagement.model.Customer;
import com.midterm.proj.warehousemanagement.model.Employee;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.util.MyApp;
import com.midterm.proj.warehousemanagement.util.ThirdPartyApp;

import java.util.ArrayList;
import java.util.List;

public class ShowCustomerListFragment extends Fragment implements CustomerCrudListener {
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
        lvCustomerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                showOptions(pos);
                return true;
            }
        });
    }

    private void showOptions(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("T??y ch???n");
        builder.setIcon(R.drawable.customer);
        String[] options = {"Ch???nh s???a th??ng tin", "G???i ??i???n", "X??a"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Ch???nh s???a th??ng tin
                        editCustomerInfo(pos);
                        break;
                    case 1: // G???i
                        ThirdPartyApp.dialPhoneNumber(customerArrayList.get(pos).getPhone());
                        break;
                    case 2: // X??a
                        deleteCustomer(pos);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteCustomer(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("C???NH B??O");
        builder.setIcon(R.drawable.ic_dangerous);
        builder.setMessage("B???n ch???c mu???n x??a kh??ch h??ng n??y ch????");
        int id = adapter.getItem(pos).getCustomerID();
        // add the buttons
        builder.setPositiveButton("X??a", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DAO.CustomerQuery customerQuery = new CustomerQuery();
                customerQuery.deleteCustomer(id, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        updateCustomerList();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(MyApp.context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        builder.setNegativeButton("H???y", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editCustomerInfo(int pos) {
        int id = adapter.getItem(pos).getCustomerID();
        UpdateCustomerDialogFragment updateCustomerDialogFragment = UpdateCustomerDialogFragment.newInstance("S???a th??ng tin kh??ch h??ng",id ,ShowCustomerListFragment.this);
        updateCustomerDialogFragment.show(getFragmentManager(), "update_customer");
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

    @Override
    public void onCustomerListUpdate(boolean isUpdated){
        if (isUpdated)
            updateCustomerList();
    }

    private void updateCustomerList() {
        DAO.CustomerQuery customerQuery = new CustomerQuery();
        customerQuery.readAllCustomer(new QueryResponse<List<Customer>>() {
            @Override
            public void onSuccess(List<Customer> data) {
                customerArrayList.clear();
                customerArrayList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
