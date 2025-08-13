package com.Anjula.TicketingSystem.service;

import com.Anjula.TicketingSystem.configuration.Config;
import com.Anjula.TicketingSystem.configuration.TicketStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class TicketingService {

    private boolean isSimulationRunning = false;
    private Config config;
    private TicketStatus ticketStatus;
    private Thread vendorThread;
    private Thread customerThread;

    // Start the simulation
    public void startSimulation(Config config) {
        if (isSimulationRunning) {
            throw new IllegalStateException("Simulation already running.");
        }
        this.config = config;
        this.isSimulationRunning = true;
        this.ticketStatus = new TicketStatus(config.getTotalTickets(), 0);

        // Start vendor and customer threads
        vendorThread = new Thread(this::vendorSimulation);
        customerThread = new Thread(this::customerSimulation);

        vendorThread.start();
        customerThread.start();
    }

    // Vendor Thread: Releases tickets
    private void vendorSimulation() {
        while (isSimulationRunning && ticketStatus.getRemainingTickets() > 0) {
            try {
                // Release tickets based on the vendor rate
                Thread.sleep(config.getTicketReleaseRate() * 1000);
                synchronized (ticketStatus) {
                    if (ticketStatus.getRemainingTickets() > 0) {
                        ticketStatus.setRemainingTickets(ticketStatus.getRemainingTickets() - 1);
                        ticketStatus.setPoolSize(ticketStatus.getPoolSize() + 1);
                        System.out.println("Ticket Released, Remaining Tickets: " + ticketStatus.getRemainingTickets());
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Customer Thread: Retrieves tickets
    private void customerSimulation() {
        while (isSimulationRunning) {
            try {
                // Simulate customer retrieval at the retrieval rate
                Thread.sleep(config.getCustomerRetrieveRate() * 1000);
                synchronized (ticketStatus) {
                    if (ticketStatus.getPoolSize() > 0) {
                        ticketStatus.setPoolSize(ticketStatus.getPoolSize() - 1);
                        System.out.println("Ticket Retrieved, Pool Size: " + ticketStatus.getPoolSize());
                    } else {
                        // Wait for tickets to become available
                        System.out.println("No tickets available, waiting...");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Stop the simulation
    public void stopSimulation() {
        isSimulationRunning = false;
        if (vendorThread != null && vendorThread.isAlive()) {
            vendorThread.interrupt();
        }
        if (customerThread != null && customerThread.isAlive()) {
            customerThread.interrupt();
        }
    }

    // Save the configuration to a file
    public void saveConfig(Config config) throws IOException {
        File file = new File("config.json");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(config.toJson());
        }
    }

    // Get the current ticket status
    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }
}
