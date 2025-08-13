package com.Anjula.TicketingSystem.cli;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.util.Scanner;
import java.io.*;


public class Configloader {

    private static final String CONFIG_FILE_PATH = "config.json";
    private static final Gson gson = new Gson();

    // Load configuration from file
    public Config loadConfig() {
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            LoggerSetup.LOGGER.severe("Error reading config file: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            LoggerSetup.LOGGER.severe("Invalid JSON syntax in the config file: " + e.getMessage());
        }
        return null;
    }


    // Save configuration to file
    public static void saveConfig(Config config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            gson.toJson(config, writer);
            System.out.println("Configuration saved to : " + CONFIG_FILE_PATH);
        } catch (JsonIOException | IOException e) {
            System.err.println("Error saving configuration file: " + e.getMessage());
        }
    }

    // Get configuration value by key
    public static Config getConfigFromUser(Scanner sc) {
        System.out.println("Enter Total Tickets: ");
        int totalTickets = sc.nextInt();
        System.out.println("Enter Ticket Release Rate: ");
        int ticketReleaseRate = sc.nextInt();
        System.out.println("Enter Customer Ticket Retrieval Rate: ");
        int ticketRetrievalRate = sc.nextInt();
        System.out.println("Enter Maximum Ticket Capacity: ");
        int maxTicketCapacity = sc.nextInt();

        return new Config(totalTickets, ticketReleaseRate, ticketRetrievalRate, maxTicketCapacity);
    }
}
