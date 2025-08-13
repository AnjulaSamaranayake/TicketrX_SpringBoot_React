package com.Anjula.TicketingSystem.cli;

public class Customer implements Runnable{

    private final TicketPool ticketPool;
    private final Config config;

    public Customer(TicketPool ticketPool, Config config) {
        this.ticketPool = ticketPool;
        this.config = config;
    }

    @Override
    public void run() {
        while(true){
            Ticket ticket = ticketPool.retrieveTicket();
            System.out.println(Thread.currentThread().getName() + " bought " + ticket);

            try{
                Thread.sleep(1000 * config.getCustomerRetrievalRate());
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
                System.err.println("Customer thread interrupted: " + e.getMessage());
            }
        }
    }
}

