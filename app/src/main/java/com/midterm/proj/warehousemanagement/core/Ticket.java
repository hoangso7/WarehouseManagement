package com.midterm.proj.warehousemanagement.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ticket {
    private String sID;
    private String sDate;
    private String sCalcUnit;
    private int iQuantum;
    private Product product;


    public Ticket(){}

    // for importing products
    public Ticket(Product product, int iQuantum, String sCalcUnit){
        this.product = product;
        this.iQuantum = iQuantum;
        this.sCalcUnit = sCalcUnit;
        this.sID = generateTicketID();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.sDate = dateFormat.format(date);
    }

    // for exporting products
    public Ticket(int iQuantum, String sCalcUnit){
        this.iQuantum = iQuantum;
        this.sCalcUnit = sCalcUnit;
        this.sID = generateTicketID();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.sDate = dateFormat.format(date);
    }

    String generateTicketID() {
        return "";
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsCalcUnit() {
        return sCalcUnit;
    }

    public void setsCalcUnit(String sCalcUnit) {
        this.sCalcUnit = sCalcUnit;
    }

    public int getiQuantum() {
        return iQuantum;
    }

    public void setiQuantum(int iQuantum) {
        this.iQuantum = iQuantum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
