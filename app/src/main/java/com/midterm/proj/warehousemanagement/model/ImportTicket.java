package com.midterm.proj.warehousemanagement.model;

public class ImportTicket {
    private int ID_Warehouse;    // primary key
    private int ID_Employee;     // primary key
    private String createDate;
    private int number;

    public ImportTicket(int ID_Warehouse, int ID_Employee, String createDate, int number) {
        this.ID_Warehouse = ID_Warehouse;
        this.ID_Employee = ID_Employee;
        this.createDate = createDate;
        this.number = number;
    }

    public int getID_Warehouse() {
        return ID_Warehouse;
    }

    public void setID_Warehouse(int ID_Warehouse) {
        this.ID_Warehouse = ID_Warehouse;
    }

    public int getID_Employee() {
        return ID_Employee;
    }

    public void setID_Employee(int ID_Employee) {
        this.ID_Employee = ID_Employee;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
