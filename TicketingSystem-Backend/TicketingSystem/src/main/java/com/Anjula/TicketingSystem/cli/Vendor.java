package com.Anjula.TicketingSystem.cli;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final Config config;

    public Vendor(TicketPool ticketPool, Config config) {
        this.ticketPool = ticketPool;
        this.config = config;
    }

    @Override
    public void run() {
        while (true) {
            Ticket ticket = new Ticket(); // Assuming Ticket has a default constructor
            ticketPool.addTicket(ticket); // Add ticket to the pool
            System.out.println(Thread.currentThread().getName() + " added " + ticket);

            try {
                Thread.sleep(1000 * config.getTicketReleaseRate());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Vendor thread interrupted: " + e.getMessage());
            }
        }
    }
}





