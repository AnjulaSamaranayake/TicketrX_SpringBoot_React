package com.Anjula.TicketingSystem.cli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load or get Configuration
        Config config;
        Configloader configloader = new Configloader();
        config = configloader.loadConfig();

        //Display menu for configuration
        System.out.println(".....Welcome to the Ticketing System Simulation.....");
        System.out.println("Choose an option:");
        System.out.println("1. Load previous configuration");
        System.out.println("2. Enter New configuration");
        System.out.println("Your option: ");
        int option = sc.nextInt();

        switch (option) {
            case 1: //Load previous configuration
                config = configloader.loadConfig();
                if (config != null) {
                    System.out.println("Your configuration has been loaded" + config);
                } else {
                    System.out.println("Your configuration has not been loaded. Please enter a valid configuration.");
                    config = configloader.getConfigFromUser(sc);
                    Configloader.saveConfig(config);
                }
                break;

            case 2: //Enter new Configuration
                System.out.println("Enter New configuration");
                config = configloader.getConfigFromUser(sc);
                Configloader.saveConfig(config);
                break;

            default:
                System.out.println("Invalid option. Exiting...");
                System.exit(0);
        }

        // Proceed with the simulation using the loaded or newly created configuration
        if (config == null) {
            System.out.println("Your configuration has been loaded" + config);
        }

        // Initialize the ticket pool with total tickets and max ticket capacity
        TicketPool ticketPool = new TicketPool(config.getMaxTicketsCapacity(), config.getTotalTickets());

        //Create and start Vendor threads
        int vendorCount = 4;
        for (int i = 1; i <= vendorCount; i++) { //Simulate 4 Vendors
            String vendorId = "Vendor-" + i; //Unique ID for vendors
            Thread vendorThread = new Thread(new Vendor(ticketPool, config), vendorId); //pass config
            vendorThread.start();
        }

        // Create and start customer threads
        int customerCount = 4;
        for (int i = 1; i <= customerCount; i++) { //Simulate 4 customers
            String customerId = "Customer-" + i; // Unique ID for customers
            Thread customerThread = new Thread(new Customer(ticketPool, config), customerId);
            customerThread.start();
        }
    }
}
