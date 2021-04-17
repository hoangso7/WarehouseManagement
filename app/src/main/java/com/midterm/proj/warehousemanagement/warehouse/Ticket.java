package com.midterm.proj.warehousemanagement.warehouse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ticket {
    private String sID;
    private String sDate;
    private String sCalcUnit;
    private int iNumber;
    private Product product;


    public Ticket(){}

    // for importing products
    public Ticket(Product product, int iNumber, String sCalcUnit){
        this.product = product;
        this.iNumber = iNumber;
        this.sCalcUnit = sCalcUnit;
        this.sID = generateTicketID();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.sDate = dateFormat.format(date);
    }

    // for exporting products
    public Ticket(int iNumber, String sCalcUnit){
        this.iNumber = iNumber;
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

    public int getiNumber() {
        return iNumber;
    }

    public void setiNumber(int iNumber) {
        this.iNumber = iNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
