package com.midterm.proj.warehousemanagement.model;

public class ExportTicket {
    int ID_ExportTicket;    // primary key
    String createDate;

    public ExportTicket(int ID_ExportTicket, String createDate) {
        this.ID_ExportTicket = ID_ExportTicket;
        this.createDate = createDate;
    }

    public int getID_ExportTicket() {
        return ID_ExportTicket;
    }

    public void setID_ExportTicket(int ID_ExportTicket) {
        this.ID_ExportTicket = ID_ExportTicket;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
