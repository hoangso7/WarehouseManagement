package com.midterm.proj.warehousemanagement.features.product.show;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.product.create.CreateProductFragment;
import com.midterm.proj.warehousemanagement.features.warehouse.manager.WarehouseManagerFragment;
import com.midterm.proj.warehousemanagement.features.warehouse.show.WarehouseAdapter;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.model.Warehouse;
import com.midterm.proj.warehousemanagement.util.ThirdPartyApp;

import java.util.ArrayList;
import java.util.List;

public class ShowInstockFragment extends Fragment {
    private ListView instockListView;
    private ShowProductListAdapter adapter;
    private ArrayList<Product> productArrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkForEmptyInstock();
        setControl();
        setEvent();
    }

    private void checkForEmptyInstock() {
        DAO.ProductQuery productQuery = new ProductQuery();
        productQuery.readAllProduct(new QueryResponse<List<Product>>() {
            @Override
            public void onSuccess(List<Product> data) {
                if(data.isEmpty()){
                    askForNewProduct();
                }
            }

            @Override
            public void onFailure(String message) {
                askForNewProduct();
            }
        });
    }

    private void askForNewProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Danh sách sản phẩm trống!");
        builder.setMessage("Hãy bắt đầu bằng cách thêm một sản phẩm mới...");
        builder.setIcon(R.drawable._warehouse);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_dashboard_container, new CreateProductFragment());
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

    private void setControl() {
        instockListView = getView().findViewById(R.id.lv_product_list);
        fetchInstock();
    }

    private void setEvent() {
        instockListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showOptions(position);
                return true;
            }
        });
    }

    private void showOptions(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tùy chọn");
        builder.setIcon(R.drawable.customer);
        String[] options = {"Chỉnh sửa thông tin", "Gọi điện", "Xóa"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Xóa
                        deleteProduct(position);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteProduct(int position) {
        instockListView.getSelectedItem();
        DAO.ProductQuery productQuery=new ProductQuery();

       // productQuery.deleteProduct();
    }

    public void fetchInstock(){
        DAO.ProductQuery productQuery = new ProductQuery();
        productQuery.readAllProduct(new QueryResponse<List<Product>>() {
            @Override
            public void onSuccess(List<Product> data) {
                productArrayList.addAll(data);
                adapter = new ShowProductListAdapter(getContext(), productArrayList);
                instockListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
