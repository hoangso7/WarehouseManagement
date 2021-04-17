package com.midterm.proj.warehousemanagement.core;

public class ImportTicket extends Ticket{
    private static int number = 0;

    public void increaseTicketID(){
        number++;
    }

    public static int getNumber() {
        return number;
    }

    public ImportTicket(Product product, int iQuantum, String sCalcUnit) {
        super(product, iQuantum, sCalcUnit);
    }

    @Override
    String generateTicketID() {
        increaseTicketID();
        return "IN-"+String.valueOf(getNumber());
    }
}
