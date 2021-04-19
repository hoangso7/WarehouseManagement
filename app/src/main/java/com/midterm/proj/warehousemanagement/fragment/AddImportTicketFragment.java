package com.midterm.proj.warehousemanagement.fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.MainActivity;
import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.warehouse.ImportTicket;
import com.midterm.proj.warehousemanagement.warehouse.Product;
import com.midterm.proj.warehousemanagement.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class AddImportTicketFragment extends Fragment{
    private Spinner spnWarehouse;
    private TextView tvWarehouseID, tvWarehouseName, tvWarehouseAddress;
    private EditText edtProductID, edtProductName, edtProductUnit, edtProductNumber;
    private Button btnSubmitImportForm;
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
        btnSubmitImportForm = getView().findViewById(R.id.btn_submit_import_form);
        edtProductID = getView().findViewById(R.id.edt_productID);
        edtProductName = getView().findViewById(R.id.edt_productName);
        edtProductUnit = getView().findViewById(R.id.edt_productUnit);
        edtProductNumber = getView().findViewById(R.id.edt_productNumber);
    }
    private void setEvent() {
        List<String> warehouseNameList = new ArrayList<>();
        for (Warehouse w: MainActivity.warehouses) {
            warehouseNameList.add(w.getsName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getView().getContext(),R.layout.spiner_item,warehouseNameList);
        dataAdapter.setDropDownViewResource(R.layout.custom_spiner_item);

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

        btnSubmitImportForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitImportForm();
            }
        });

        edtProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String getCurrentText = s.toString();
                edtProductID.setText(generateProductID(getCurrentText));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void submitImportForm() {
        String productID, productName, productUnit, productNumber;
        productID = edtProductID.getText().toString().trim();
        productName = edtProductName.getText().toString().trim();
        productUnit = edtProductUnit.getText().toString().trim();
        productNumber = edtProductNumber.getText().toString().trim();

        if(productID.length() == 0 ||
                productName.length()==0 ||
                productUnit.length()==0 ||
                productNumber.length()==0){
            Toast.makeText(getActivity(),"Vui lòng kiểm tra lại phiếu nhập kho!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (productNameExisted(productName)){
            String exinfo = productNumber + " " + productUnit;
            Toast.makeText(getActivity(),"Tên hàng đã tồn tại, thêm vào kho: "+exinfo,Toast.LENGTH_SHORT).show();
        }
        ImportTicket newTicket = new ImportTicket(new Product(),Integer.valueOf(productNumber),productUnit);

    }

    private String generateProductID(String productName){
        String result= "";
        if(productName.length() == 0) return  result;
        for(String s : productName.split(" "))
            result += s.charAt(0);
        return result;
    }

    private boolean productNameExisted(String productName){
        Warehouse warehouse = MainActivity.warehouses.get(spnWarehouse.getSelectedItemPosition());
        ArrayList<ImportTicket> importTickets = warehouse.getImportTickets();
        for(ImportTicket it : importTickets){
            String pProductName = it.getProduct().getsName();
            if(pProductName.equals(productName))
                return true;
        }
        return false;
    }

    private boolean productUnitExisted(String productUnit){
        Warehouse warehouse = MainActivity.warehouses.get(spnWarehouse.getSelectedItemPosition());
        ArrayList<ImportTicket> importTickets = warehouse.getImportTickets();
        for(ImportTicket it : importTickets){
            String pProductUnit = it.getsCalcUnit();
            if(pProductUnit.equals(productUnit))
                return true;
        }
        return false;
    }
}
