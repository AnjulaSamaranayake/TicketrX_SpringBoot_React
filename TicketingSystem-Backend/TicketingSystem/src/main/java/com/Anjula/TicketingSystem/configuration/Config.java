package com.Anjula.TicketingSystem.configuration;

public class Config {

    private int totalTickets;
    private int ticketReleaseRate; // In seconds
    private int customerRetrieveRate; // In seconds
    private int maxTicketCapacity;

    // Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrieveRate() {
        return customerRetrieveRate;
    }

    public void setCustomerRetrieveRate(int customerRetrieveRate) {
        this.customerRetrieveRate = customerRetrieveRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public String toJson() {
        return "{"
                + "\"totalTickets\":" + totalTickets + ","
                + "\"ticketReleaseRate\":" + ticketReleaseRate + ","
                + "\"customerRetrieveRate\":" + customerRetrieveRate + ","
                + "\"maxTicketCapacity\":" + maxTicketCapacity
                + "}";
    }
}