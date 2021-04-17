package com.midterm.proj.warehousemanagement.warehouse;

public class ExportTicket extends Ticket{
    private static int index = 0;
    private String sCustomer;
    private String exportedProductName;
    public void increaseTicketID(){
        index++;
    }

    public static int getIndex() {
        return index;
    }

    public ExportTicket(Product product, int iNumber, String sCalcUnit, String sCustomer) {
        super(iNumber, sCalcUnit);
        this.sCustomer = sCustomer;
        this.exportedProductName = product.getsName();
    }
    @Override
    String generateTicketID() {
        increaseTicketID();
        return "OUT-"+ getIndex();
    }

    public String getsCustomer() {
        return sCustomer;
    }

    public void setsCustomer(String sCustomer) {
        this.sCustomer = sCustomer;
    }

    public String getExportedProductName() {
        return exportedProductName;
    }

    public void setExportedProductName(String exportedProductName) {
        this.exportedProductName = exportedProductName;
    }
}
