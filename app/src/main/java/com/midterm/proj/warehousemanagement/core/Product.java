package com.midterm.proj.warehousemanagement.core;

public class Product {
    private String sID;
    private String sName;
    private String sOrigin;
    private String sImageURL;

    public Product(){}

    public Product(String sID, String sName, String sOrigin) {
        this.sID = sID;
        this.sName = sName;
        this.sOrigin = sOrigin;
    }

    public Product(String sID, String sName, String sOrigin, String sImageURL) {
        this.sID = sID;
        this.sName = sName;
        this.sOrigin = sOrigin;
        this.sImageURL = sImageURL;
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

    public String getsOrigin() {
        return sOrigin;
    }

    public void setsOrigin(String sOrigin) {
        this.sOrigin = sOrigin;
    }

    public String getsImageURL() {
        return sImageURL;
    }

    public void setsImageURL(String sImageURL) {
        this.sImageURL = sImageURL;
    }
}
