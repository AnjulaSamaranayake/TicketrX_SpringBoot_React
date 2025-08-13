package com.Anjula.TicketingSystem.configuration;

public class TicketStatus {

    private int remainingTickets;
    private int poolSize;

    public TicketStatus(int remainingTickets, int poolSize) {
        this.remainingTickets = remainingTickets;
        this.poolSize = poolSize;
    }

    // Getters and Setters
    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}
