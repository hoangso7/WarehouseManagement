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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.midterm.proj.warehousemanagement.R;
import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.database.daoImplementation.CustomerQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.EmployeeQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ExportTicketQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.ProductQuery;
import com.midterm.proj.warehousemanagement.database.daoImplementation.WarehouseQuery;
import com.midterm.proj.warehousemanagement.database.daoInterface.DAO;
import com.midterm.proj.warehousemanagement.features.employee.SearchEmployeeItemListener;
import com.midterm.proj.warehousemanagement.features.employee.search.EmployeeSearchDialogFragment;
import com.midterm.proj.warehousemanagement.features.import_ticket.create.CreateImportTicketFragment;
import com.midterm.proj.warehousemanagement.features.product.SearchProductItemListener;
import com.midterm.proj.warehousemanagement.features.product.search.ProductSearchDialogFragment;
import com.midterm.proj.warehousemanagement.model.Customer;
import com.midterm.proj.warehousemanagement.model.Employee;
import com.midterm.proj.warehousemanagement.model.ExportTicket;
import com.midterm.proj.warehousemanagement.model.ImportTicket;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.model.Warehouse;
import com.midterm.proj.warehousemanagement.util.MyApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateExportTicketFragment extends Fragment implements SearchProductItemListener, SearchEmployeeItemListener {
    private Spinner spnWarehouse;
    private TextView tvWarehouseID, tvWarehouseName, tvWarehouseAddress, tvTotalMoney;
    private EditText edtCustomerName, edtCustomerPhone;
    private Button btnSubmitExportForm, btnChooseEmployee, btnChooseProduct;
    private ArrayList<ExportTicket> exportTickets = new ArrayList<>();
    private ArrayList<Warehouse> warehouses = new ArrayList<>();
    private TableLayout tableLayout;
    private static int warehouseID;
    private static int tableIndex = 0;
    private static int totalMoney = 0;

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
        tvTotalMoney = view.findViewById(R.id.tv_total_money);
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

        btnChooseEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeSearchDialogFragment employeeSearchDialogFragment = EmployeeSearchDialogFragment.newInstance(CreateExportTicketFragment.this);
                employeeSearchDialogFragment.show(getFragmentManager(),"searchemployee");
            }
        });

        btnSubmitExportForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitExportForm();
            }
        });
    }

    private void submitExportForm() {
        int employeeID = getEmployeeIdFromName();
        int customerID = getCustomerId();
        String creationDate = getCreationDate();
        ExportTicket exportTicket = new ExportTicket();

        //int productID = getProductIdFromName();

        exportTicket.setEmployeeID(employeeID);
        exportTicket.setCustomerID(customerID);
        exportTicket.setCreateDate(creationDate);
        exportTicket.setWarehouseID(warehouseID);

        DAO.ExportTicketQuery exportTicketQuery = new ExportTicketQuery();
        ArrayList<ExportTicket> exportTickets=new ArrayList<>();
        exportTicketQuery.readAllExpoprtTicket(warehouseID, new QueryResponse<List<ExportTicket>>() {
            @Override
            public void onSuccess(List<ExportTicket> data) {
                exportTickets.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });

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

    private String getCreationDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    private int getCustomerId() {
        int customerID = -1;
        String customerName = edtCustomerName.getText().toString().trim();
        String customerPhone = edtCustomerPhone.getText().toString().trim();
        if(customerName.length() == 0){
            Toast.makeText(MyApp.context, "Vui lòng nhập tên khách hàng", Toast.LENGTH_LONG).show();
            return customerID;
        }
        if(customerPhone.length() != 10){
            Toast.makeText(MyApp.context, "Vui lòng kiểm tra SDT khách hàng", Toast.LENGTH_LONG).show();
            return customerID;
        }
        ArrayList<Customer> customers = new ArrayList<>();
        DAO.CustomerQuery customerQuery = new CustomerQuery();
        customerQuery.readAllCustomer(new QueryResponse<List<Customer>>() {
            @Override
            public void onSuccess(List<Customer> data) {
                customers.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        for(Customer c : customers){
            if(c.getPhone().equals(customerPhone)){
                customerID = c.getCustomerID();
                return customerID;
            }
        }
        Customer customer = new Customer(customerName,customerPhone);
        customerQuery.createCustomer(customer, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
        customerQuery.readAllCustomer(new QueryResponse<List<Customer>>() {
            @Override
            public void onSuccess(List<Customer> data) {
                customers.clear();
                customers.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        for(Customer c : customers){
            if(c.getPhone().equals(customerPhone)){
                customerID = c.getCustomerID();

            }
        }
        return customerID;
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
        edittext.setHint("");
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
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
        tableLayout.setStretchAllColumns(true);
        Context context = getContext();
        TableRow tableRow = new TableRow(context);
        tableLayout.addView(tableRow);
        TextView tvTableIndex = new TextView(context);
        TextView tvProductName = new TextView(context);
        TextView tvProductNumber = new TextView(context);
        TextView tvProductPrice = new TextView(context);
        tableIndex++;
        totalMoney += getProductPriceFromName() * Integer.parseInt(productNumber);
        tvTotalMoney.setText(String.valueOf(totalMoney));

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
    @Override
    public void setEmployeeNameCallback(String employeeName) {
        btnChooseEmployee.setText(employeeName);
        DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
        ArrayList<Employee> employees = new ArrayList<>();
        employeeQuery.readAllEmployee(new QueryResponse<List<Employee>>() {
            @Override
            public void onSuccess(List<Employee> data) {
                employees.addAll(data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        for (Employee employee: employees){
            if(employee.getName().equals(employeeName)){
                String phone = employee.getPhone();
            }
        }
    }
}