package com.midterm.proj.warehousemanagement.core;

import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
    private String sID;
    private String sName;
    private String sAddress;

    private ArrayList<ImportTicket> importTickets = new ArrayList<>();
    private ArrayList<ExportTicket> exportTickets = new ArrayList<>();

    private HashMap<String, Integer> inStock = new HashMap<>();

    public Warehouse(){}

    public Warehouse(String sID, String sName, String sAddress) {
        this.sID = sID;
        this.sName = sName;
        this.sAddress = sAddress;
    }

    public void importToStock(ImportTicket ticket){
        importTickets.add(ticket);
        Product importProduct = ticket.getProduct();
        int iQuantum = ticket.getiQuantum();
        // Check product already in stock
        if(inStock.containsKey(importProduct.getsName())){
            // Add more quantum
            inStock.put(importProduct.getsName(), inStock.get(importProduct.getsName()) + iQuantum) ;
        }
        else{
            // Else, put new product to stock
            inStock.put(importProduct.getsName(), iQuantum);
        }
    }

    public boolean exportFromStock(ExportTicket ticket){
        boolean status = false;
        String sProductName = "";

        try{
            Product exportProduct   = ticket.getProduct();
            int iQuantum            = ticket.getiQuantum();
            sProductName            = exportProduct.getsName();

            if(inStock.containsKey(sProductName)){
                int iProductsQuantumAvailable = inStock.get(sProductName);
                // Check product enough to be export
                if (iProductsQuantumAvailable >= iQuantum) {
                    inStock.put(exportProduct.getsName(), inStock.get(sProductName) - iQuantum);
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
