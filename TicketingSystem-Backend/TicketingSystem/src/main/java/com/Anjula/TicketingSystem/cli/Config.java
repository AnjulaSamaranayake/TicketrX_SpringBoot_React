package com.Anjula.TicketingSystem.cli;

public class Config {

    private int totalTickets;
    private int maxTicketsCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public Config(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketsCapacity) {
        this.totalTickets = totalTickets;
        this.maxTicketsCapacity = maxTicketsCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketsCapacity() {
        return maxTicketsCapacity;
    }

    @Override
    public String toString() {
        return "Config{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketsCapacity=" + maxTicketsCapacity +
                '}';
    }
}


