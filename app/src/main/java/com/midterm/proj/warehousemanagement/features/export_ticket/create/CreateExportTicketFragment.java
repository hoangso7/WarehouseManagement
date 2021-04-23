package com.midterm.proj.warehousemanagement.features.export_ticket.create;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.import_ticket.create.CreateImportTicketFragment;
import com.midterm.proj.warehousemanagement.features.product.SearchProductItemListener;
import com.midterm.proj.warehousemanagement.features.product.search.ProductSearchDialogFragment;
import com.midterm.proj.warehousemanagement.model.ExportTicket;
import com.midterm.proj.warehousemanagement.model.ImportTicket;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class CreateExportTicketFragment extends Fragment implements SearchProductItemListener {
    private Spinner spnWarehouse;
    private TextView tvWarehouseID, tvWarehouseName, tvWarehouseAddress, tvProductUnit;
    private EditText edtCustomerName, edtCustomerPhone;
    private Button btnSubmitExportForm, btnChooseEmployee, btnChooseProduct;
    private ArrayList<ExportTicket> exportTickets = new ArrayList<>();
    private ArrayList<Warehouse> warehouses = new ArrayList<>();
    private TableLayout tableLayout;
    private static int warehouseID;
    private static int tableIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_export_ticket, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    private void setControl(View view) {
        spnWarehouse = view.findViewById(R.id.spiner_choose_warehouse);
        tvWarehouseID = view.findViewById(R.id.textview_warehouseID);
        tvWarehouseName = view.findViewById(R.id.textview_warehouseName);
        tvWarehouseAddress = view.findViewById(R.id.textview_warehouseAddress);
        btnSubmitExportForm = view.findViewById(R.id.btn_confirm_new_export_ticket);
        btnChooseEmployee = view.findViewById(R.id.btn_choose_employee);
        btnChooseProduct = view.findViewById(R.id.btn_choose_product);
        edtCustomerName = view.findViewById(R.id.edt_customer_name);
        edtCustomerPhone = view.findViewById(R.id.edt_customer_phone);
        tableLayout = view.findViewById(R.id.tableLayout);
    }
    private void setEvent() {
        fetchWarehouseList();
        ArrayList<String> warehouseNameList = new ArrayList<>();
        for(Warehouse w:warehouses){
            warehouseNameList.add(w.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getView().getContext(),R.layout.spiner_item,warehouseNameList);
        dataAdapter.setDropDownViewResource(R.layout.custom_spiner_item);

        spnWarehouse.setAdapter(dataAdapter);
        spnWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvWarehouseID.setText(String.valueOf( warehouses.get(i).getID_Warehouse()));
                tvWarehouseName.setText(warehouses.get(i).getName());
                tvWarehouseAddress.setText(warehouses.get(i).getAddress());
                warehouseID = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChooseProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductSearchDialogFragment productSearchDialogFragment = ProductSearchDialogFragment.newInstance(CreateExportTicketFragment.this);
                productSearchDialogFragment.show(getFragmentManager(), "searchproduct");
            }
        });
    }

    private void fetchWarehouseList() {
        DAO.WarehouseQuery warehouseQuery = new WarehouseQuery();
        warehouseQuery.readAllWarehouse(new QueryResponse<List<Warehouse>>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                warehouses.clear();
                warehouses.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void setProductNameCallback(String productName){
        btnChooseProduct.setText(productName);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edittext = new EditText(getContext());
        edittext.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        alert.setMessage("Vui lòng nhập số lượng sản phẩm");
        //alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String productNumber = edittext.getText().toString();
                addProductToTable(productName, productNumber);
            }
        });
        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                btnChooseProduct.setText("Chọn sản phẩm");
            }
        });
        alert.show();

    }

    private void addProductToTable(String productName, String productNumber) {
        Context context = getContext();
        TableRow tableRow = new TableRow(context);
        tableLayout.addView(tableRow);
        TextView tvTableIndex = new TextView(context);
        TextView tvProductName = new TextView(context);
        TextView tvProductNumber = new TextView(context);
        TextView tvProductPrice = new TextView(context);
        tableIndex++;

        TableLayout.LayoutParams params = new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tvTableIndex.setText(String.valueOf(tableIndex));
        tvTableIndex.setTextSize(15f);

        tvProductName.setText(productName);
        tvProductName.setTextSize(15f);

        tvProductNumber.setText(productNumber);
        tvProductNumber.setTextSize(15f);

        tvProductPrice.setText(String.valueOf(getProductPriceFromName()));
        tvProductPrice.setTextSize(15f);

        tableRow.addView(tvTableIndex);
        tableRow.addView(tvProductName);
        tableRow.addView(tvProductNumber);
        tableRow.addView(tvProductPrice);
    }

    private int getProductPriceFromName() {
        int price=-1;
        String name = btnChooseProduct.getText().toString();
        if(name.equals("Chọn sản phẩm")){
            return price;
        }
        ArrayList<Product> products = new ArrayList<>();
        DAO.ProductQuery productQuery = new ProductQuery();
        productQuery.readAllProduct(new QueryResponse<List<Product>>() {
            @Override
            public void onSuccess(List<Product> data) {
                products.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        for(Product p : products){
            if(p.getName().equals(name)){
                price = p.getPrice();
                break;
            }
        }
        return price;
    }
}
