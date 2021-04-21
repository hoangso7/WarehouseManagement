package com.midterm.proj.warehousemanagement.database.DAO;

import com.midterm.proj.warehousemanagement.database.QueryResponse;
import com.midterm.proj.warehousemanagement.model.Customer;
import com.midterm.proj.warehousemanagement.model.Employee;
import com.midterm.proj.warehousemanagement.model.ExportTicket;
import com.midterm.proj.warehousemanagement.model.ExportTicketDetail;
import com.midterm.proj.warehousemanagement.model.ImportTicket;
import com.midterm.proj.warehousemanagement.model.Product;
import com.midterm.proj.warehousemanagement.model.Supplier;
import com.midterm.proj.warehousemanagement.model.Warehouse;

import java.util.List;

public class DataAccessObject {

    public interface EmployeeQuery{
        void createEmployee(Employee employee, QueryResponse<Boolean> response);
        void readEmployee(int EmployeeID, QueryResponse<Employee> response);
        void readAllEmployee(QueryResponse<List<Employee>> response);
        void updateEmployee(Employee employee, QueryResponse<Boolean> response);
        void deleteEmployee(int employeeID, QueryResponse<Boolean> response);
    }

    public interface CustomerQuery{
        void createCustomer(Customer customer, QueryResponse<Boolean> response);
        void readCustomer(int CustomerID, QueryResponse<Customer> response);
        void readAllCustomer(QueryResponse<List<Customer>> response);
        void updateCustomer(Customer customer, QueryResponse<Boolean> response);
        void deleteCustomer(int CustomerID, QueryResponse<Boolean> response);
    }

    public interface ProductQuery{
        void createProduct(Product product, QueryResponse<Boolean> response);
        void readProduct(int ProductID, QueryResponse<Product> response);
        void readAllProduct(QueryResponse<List<Product>> response);
        void updateProduct(Product product, QueryResponse<Boolean> response);
        void deleteProduct(int ProductID, QueryResponse<Boolean> response);
    }

    public interface WarehouseQuery{
        void createWarehouse(Warehouse warehouse, QueryResponse<Boolean> response);
        void readWarehouse(int WarehouseID, QueryResponse<Warehouse> response);
        void readAllWarehouse(QueryResponse<List<Warehouse>> response);
        void updateWarehouse(Warehouse warehouse, QueryResponse<Boolean> response);
        void deleteWarehouse(int WarehouseID, QueryResponse<Boolean> response);
    }

    public interface SupplierQuery{
        void createSupplier(Supplier supplier, QueryResponse<Boolean> response);
        void readSupplier(int SupplierID, QueryResponse<Supplier> response);
        void readAllSupplier(QueryResponse<List<Supplier>> response);
        void updateSupplier(Supplier supplier, QueryResponse<Boolean> response);
        void deleteSupplier(int SupplierID, QueryResponse<Boolean> response);
    }

    public interface ImportTicketQuery{
        void createImportTicket(int pkWarehouseID, int pkEmployeeID,int fkProductID, int fkSupplierID,ImportTicket importTicket, QueryResponse<Boolean> response);
        void readImportTicket(int ImportTicketID, QueryResponse<ImportTicket> response);
        void readAllImpoprtTicketFromWarehouse(int WarehouseID, QueryResponse<List<ImportTicket>> response);
    }

    public interface ExportTicketQuery{
        void createExportTicket(ExportTicket exportTicket, QueryResponse<Boolean> response);
        void readExportTicket(int ExportTicketID, QueryResponse<ExportTicket> response);
        void readAllExpoprtTicket(QueryResponse<List<ExportTicket>> response);
    }

    public interface ExportTicketDetailQuery{
        void createExportTicketDetail(ExportTicketDetail exportTicketDetail, QueryResponse<Boolean> response);
        void readExportTicketDetail(int ExportTicketDetailID, QueryResponse<ExportTicketDetail> response);
    }
}
