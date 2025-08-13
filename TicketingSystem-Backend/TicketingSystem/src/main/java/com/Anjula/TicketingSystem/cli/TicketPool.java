package com.Anjula.TicketingSystem.cli;

import java.util.concurrent.ConcurrentLinkedQueue;


public class TicketPool {
    private final ConcurrentLinkedQueue<Ticket> tickets;
    private int maximumCapacity;
    int totalTicketsAvailable;


    public TicketPool(int maximumCapacity, int totalTickets) {
        this.maximumCapacity = maximumCapacity;
        this.tickets = new ConcurrentLinkedQueue<>();
        this.totalTicketsAvailable = totalTickets;
    }


    //Buy ticket method is used by Customer when buying Tickets
    public synchronized Ticket retrieveTicket() {
        while (this.tickets.isEmpty()) {
            try{
                System.out.println(Thread.currentThread().getName() + " waiting for retrieve tickets..");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted while retrieving tickets. "+e.getMessage());
            }
        }

        Ticket ticket = tickets.poll(); //Remove ticket from the queue (front)
        // Print the message to show the thread name who bought the ticket and current size of the pool
        System.out.println(Thread.currentThread().getName() + " has bought a ticket from the pool. Current size is " + this.tickets.size());
        notifyAll();

        return ticket;
    }
    //Add Ticket method which is used by Vendors to add tickets
    public void addTicket(Ticket ticket) {
        try {
            // Wait if pool is at full
            while (tickets.size() >= maximumCapacity) {
                System.out.println(Thread.currentThread().getName() + " waiting to release tickets..");
                notifyAll();
            }

            // Check if all tickets are already released
            if (totalTicketsAvailable <= 0) {
                notifyAll(); //Notify all waiting Threads
                return;
            }

            //Add ticket to the pool
            tickets.add(ticket);
            totalTicketsAvailable--;

            System.out.println(Thread.currentThread().getName() + " added ticket. " + ticket+ " Tickets left. Current pool size: " + tickets.size() );
            notifyAll();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted while releasing tickets. " +e.getMessage());
        }

    }
}
