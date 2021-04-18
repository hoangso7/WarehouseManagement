package com.midterm.proj.warehousemanagement.warehouse;

public class ImportTicket extends Ticket{
    private static int index = 0;

    public ImportTicket() {

    }

    public void increaseTicketID(){
        index++;
    }

    public static int getIndex() {
        return index;
    }

    public ImportTicket(Product product, int iNumber, String sCalcUnit) {
        super(product, iNumber, sCalcUnit);
    }

    @Override
    String generateTicketID() {
        increaseTicketID();
        return "IN-"+getIndex();
    }
}
