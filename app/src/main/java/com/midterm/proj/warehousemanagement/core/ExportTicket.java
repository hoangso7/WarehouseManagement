package com.midterm.proj.warehousemanagement.core;

public class ExportTicket extends Ticket{
    private static int number = 0;
    private String sCustomer;

    public void increaseTicketID(){
        number++;
    }

    public static int getNumber() {
        return number;
    }

    public ExportTicket(Product product, int iQuantum, String sCalcUnit, String sCustomer) {
        super(product, iQuantum, sCalcUnit);
        this.sCustomer = sCustomer;
    }
    @Override
    String generateTicketID() {
        increaseTicketID();
        return "OUT-"+String.valueOf(getNumber());
    }

    public String getsCustomer() {
        return sCustomer;
    }

    public void setsCustomer(String sCustomer) {
        this.sCustomer = sCustomer;
    }
}
