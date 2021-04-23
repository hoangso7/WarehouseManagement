package com.midterm.proj.warehousemanagement.features.import_ticket.create;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
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

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.daoImplementation.EmployeeQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.SupplierQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ImportTicketQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.features.product.search.ProductSearchDialogFragment;
import com.midterm.proj.warehousemanagement.model.Employee;
import com.midterm.proj.warehousemanagement.model.ImportTicket;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.model.Supplier;
import com.midterm.proj.warehousemanagement.model.Warehouse;
import com.midterm.proj.warehousemanagement.util.MyApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class CreateImportTicketFragment extends Fragment {
    private Spinner spnWarehouse;
    private TextView tvWarehouseID, tvWarehouseName, tvWarehouseAddress, tvProductUnit;
    private EditText edtProductNumber, edtSupplierName, edtSupplierAddress;
    private Button btnSubmitImportForm, btnChooseEmployee, btnChooseProduct;
    private ArrayList<ImportTicket> importTickets = new ArrayList<>();
    private ArrayList<Warehouse> warehouses = new ArrayList<>();

    private int warehouseID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_import_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControl();
        setEvent();
    }

    private void fetchImportTicketFromWarehouse(int warehouseID) {
        DAO.ImportTicketQuery importTicketQuery = new ImportTicketQuery();
        importTicketQuery.readAllImportTicketFromWarehouse(warehouseID, new QueryResponse<List<ImportTicket>>() {
            @Override
            public void onSuccess(List<ImportTicket> data) {
                importTickets.clear();
                importTickets.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void fetchWarehouseList(){
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

    private void setControl() {
        spnWarehouse = getView().findViewById(R.id.spiner_choose_warehouse);
        tvWarehouseID = getView().findViewById(R.id.textview_warehouseID);
        tvWarehouseName = getView().findViewById(R.id.textview_warehouseName);
        tvWarehouseAddress = getView().findViewById(R.id.textview_warehouseAddress);
        tvProductUnit = getView().findViewById(R.id.tv_productUnit);
        btnSubmitImportForm = getView().findViewById(R.id.btn_submit_import_form);
        btnChooseEmployee = getView().findViewById(R.id.btn_ctk_choose_employee);
        btnChooseProduct = getView().findViewById(R.id.btn_ctk_choose_product);
        edtSupplierName = getView().findViewById(R.id.edt_supplier_name);
        edtSupplierAddress = getView().findViewById(R.id.edt_supplier_address);
        edtProductNumber = getView().findViewById(R.id.edt_productNumber);
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

        btnSubmitImportForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitImportForm();
            }
        });

        btnChooseProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductSearchDialogFragment productSearchDialogFragment = new ProductSearchDialogFragment();
                productSearchDialogFragment.show(getFragmentManager(), "searchproduct");
            }
        });

    }

    private void submitImportForm() {
        ImportTicket importTicket = new ImportTicket();

        int productID = getProductIdFromName();
        if(productID == -1){
            Toast.makeText(MyApp.context, "Vui lòng chọn sản phẩm", Toast.LENGTH_LONG).show();
            return;
        }

        int employeeID = getEmployeeIdFromName();
        if(employeeID == -1){
            Toast.makeText(MyApp.context, "Vui lòng chọn nhân viên", Toast.LENGTH_LONG).show();
            return;
        }

        String strProductNumber=edtProductNumber.getText().toString();
        if(strProductNumber.length() == 0){
            Toast.makeText(MyApp.context, "Vui lòng nhập số lượng", Toast.LENGTH_LONG).show();
            return;
        }

        // set primary key
        importTicket.setID_Warehouse(warehouseID);
        importTicket.setID_Employee(getEmployeeIdFromName());
        // set import ticket info
        importTicket.setCreateDate(getCreationDate());
        importTicket.setNumber(Integer.parseInt(edtProductNumber.getText().toString()));
        // set foreign key
        importTicket.setSupplierID(getSupplierID());
        importTicket.setProductID(getProductIdFromName());

        sanityCheck(importTicket);
//        if(){
//            Toast.makeText(getActivity(),"Không tạo được phiếu nhập. Vui lòng kiểm tra lại.",Toast.LENGTH_SHORT).show();
//            return;
//        }

        //DAO.ImportTicketQuery importTicketQuery = new ImportTicketQuery();
        //importTicketQuery.createImportTicket();
    }

    private int getSupplierID() {
        int id = -1;
        String supplierName = edtSupplierName.getText().toString().trim();
        String supplierAddress = edtSupplierAddress.getText().toString().trim();
        if(supplierName.length() == 0 || supplierAddress.length() == 0){
            Toast.makeText(MyApp.context, "Vui lòng kiểm tra lại thông tin nhà cung cấp", Toast.LENGTH_LONG).show();
            return id;
        }

        ArrayList<Supplier> suppliers = new ArrayList<>();
        DAO.SupplierQuery supplierQuery = new SupplierQuery();
        supplierQuery.readAllSupplier(new QueryResponse<List<Supplier>>() {
            @Override
            public void onSuccess(List<Supplier> data) {
                suppliers.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        // check if this supplier already in database and get its id
        for(Supplier s : suppliers){
            if(s.getName().equals(supplierName)){
                return s.getID_Supplier();
            }
        }
        Supplier supplier = new Supplier(supplierName,supplierAddress);
        supplierQuery.createSupplier(supplier, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                // now this supplier exists in db, re-run the function to get the ID
                // what a nice recursion! :))
                getSupplierID();
            }

            @Override
            public void onFailure(String message) {

            }
        });
        return id;
    }

    private String getCreationDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    private int getProductIdFromName() {
        int id=-1;
        String name = btnChooseProduct.getText().toString();
        if(name.equals("Chọn sản phẩm")){
            return id;
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
                id = p.getID_Product();
                break;
            }
        }
        return id;
    }

    private int getEmployeeIdFromName() {
        int id=-1;
        String name = btnChooseEmployee.getText().toString();
        if(name.equals("Chọn nhân viên")){
            return id;
        }
        ArrayList<Employee> employees = new ArrayList<>();
        DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
        employeeQuery.readAllEmployee(new QueryResponse<List<Employee>>() {
            @Override
            public void onSuccess(List<Employee> data) {
                employees.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        for(Employee e : employees){
            if(e.getName().equals(name)){
                id = e.getID_Employee();
                break;
            }
        }
        return id;
    }

    private void sanityCheck(ImportTicket importTicket) {
        DAO.ImportTicketQuery importTicketQuery = new ImportTicketQuery();
        ArrayList<ImportTicket>importTickets = new ArrayList<>();
        importTicketQuery.readAllImportTicketFromWarehouse(warehouseID, new QueryResponse<List<ImportTicket>>() {
            @Override
            public void onSuccess(List<ImportTicket> data) {
                importTickets.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        for(ImportTicket i : importTickets){
            int pId = i.getProductID();
            if(pId == importTicket.getProductID()){
                updateInstock(importTicket);
                break;
            }
        }
        createImportTicket(importTicket);
    }

    private void updateInstock(ImportTicket importTicket) {
        int productNumber = importTicket.getNumber();
        int productId = importTicket.getProductID();
        DAO.ProductQuery productQuery = new ProductQuery();
        productQuery.readProduct(productId, new QueryResponse<Product>() {
            @Override
            public void onSuccess(Product data) {
                int currentProductNumber = data.getNumber();
                currentProductNumber += productNumber;
                data.setNumber(currentProductNumber);
                productQuery.updateProduct(data, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {

                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void createImportTicket(ImportTicket importTicket) {
        DAO.ImportTicketQuery importTicketQuery = new ImportTicketQuery();
        importTicketQuery.createImportTicket(importTicket, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private String generateProductID(String productName){
        String result= "";
        if(productName.length() == 0) return  result;
        for(String s : productName.split(" "))
            result += s.charAt(0);
        return result;
    }


}
