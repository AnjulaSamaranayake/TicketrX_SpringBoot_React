package com.Anjula.TicketingSystem.cli;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;


public class LoggerSetup {
    public static final Logger LOGGER = Logger.getLogger(LoggerSetup.class.getName());

    static {
        try {
            // FileHandler logs to a file
            FileHandler fileHandler = new FileHandler("ticketing_system.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            // ConsoleHandler logs to the console
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(consoleHandler);

            // Set log level to INFO
            LOGGER.setLevel(Level.INFO);
        } catch (Exception e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
}

