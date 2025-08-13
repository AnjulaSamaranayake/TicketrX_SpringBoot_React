package com.Anjula.TicketingSystem.cli;

public class Ticket {
    private int ticketID;
    private String eventName;
    private double ticketPrice; //Using Big decimal when dealing with prices

    public Ticket(int ticketID, String eventName, double ticketPrice) {
        this.ticketID = ticketID;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    public Ticket() {
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getEventName() {
        return eventName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket ID = " + ticketID +
                ", Event Name = " + eventName +
                ", Ticket Price = " + ticketPrice ;
    }
}
