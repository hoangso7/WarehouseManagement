package com.midterm.proj.warehousemanagement.warehouse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
    private String sID;
    private String sName;
    private String sAddress;
    private ArrayList<ImportTicket> importTickets = new ArrayList<>();
    private ArrayList<ExportTicket> exportTickets = new ArrayList<>();
    private HashMap<String, Integer> inStock = new HashMap<>();

    public static final String TABLE_NAME               = "WAREHOUSE";

    public static final String COLUMN_ID                = "ID";
    public static final String COLUMN_NAME              = "NAME";
    public static final String COLUMN_ADDRESS           = "ADDRESS";
    public static final String COLUMN_IMPORT_TICKETS    = "IMPORT_TICKETS";
    public static final String COLUMN_EXPORT_TICKETS    = "EXPORT_TICKETS";
    public static final String COLUMN_INSTOCK           = "INSTOCK";

    public static final String CREATE_TABLE
            = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_IMPORT_TICKETS + " TEXT, "
            + COLUMN_EXPORT_TICKETS + " TEXT, "
            + COLUMN_INSTOCK + "TEXT)";

    public Warehouse(){}


    public Warehouse(String sID, String sName, String sAddress) {
        this.sID = sID;
        this.sName = sName;
        this.sAddress = sAddress;
    }

    public Warehouse(String sID, String sName, String sAddress, String importTickets, String exportTickets, String inStock){
        this.sID = sID;
        this.sName = sName;
        this.sAddress = sAddress;
        // Parse JSON to Arraylist
        Gson gson = new Gson();

        Type ImportTicketType = new TypeToken<ArrayList<ImportTicket>>() {}.getType();
        this.importTickets = gson.fromJson(importTickets, ImportTicketType);

        Type ExportTicketType = new TypeToken<ArrayList<ExportTicket>>() {}.getType();
        this.exportTickets = gson.fromJson(exportTickets, ExportTicketType);

        Type InStockType = new TypeToken<HashMap<String, Integer>>() {}.getType();
        this.inStock = gson.fromJson(inStock, InStockType);

    }


    public void importToStock(ImportTicket ticket){
        importTickets.add(ticket);
        Product importProduct   = ticket.getProduct();
        int iNumber            = ticket.getiNumber();
        // Check product already in stock
        if(inStock.containsKey(importProduct.getsName())){
            // Add more quantum
            inStock.put(importProduct.getsName(), inStock.get(importProduct.getsName()) + iNumber) ;
        }
        else{
            // Else, put new product to stock
            inStock.put(importProduct.getsName(), iNumber);
        }
    }

    public boolean exportFromStock(ExportTicket ticket){
        boolean status = false;
        String sProductName = "";

        try{
            int iNumber            = ticket.getiNumber();
            sProductName            = ticket.getExportedProductName();

            if(inStock.containsKey(sProductName)){
                int iProductsQuantumAvailable = inStock.get(sProductName);
                // Check product enough to be export
                if (iProductsQuantumAvailable >= iNumber) {
                    inStock.put(sProductName, inStock.get(sProductName) - iNumber);
                    exportTickets.add(ticket);
                    status = true;
                }
            }

        }catch(NullPointerException e){

        }
        return status;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public ArrayList<ImportTicket> getImportTickets() {
        return importTickets;
    }

    public void setImportTickets(ArrayList<ImportTicket> importTickets) {
        this.importTickets = importTickets;
    }

    public ArrayList<ExportTicket> getExportTickets() {
        return exportTickets;
    }

    public void setExportTickets(ArrayList<ExportTicket> exportTickets) {
        this.exportTickets = exportTickets;
    }

    public HashMap<String, Integer> getInStock() {
        return inStock;
    }

    public void setInStock(HashMap<String, Integer> inStock) {
        this.inStock = inStock;
    }
}
