package com.Anjula.TicketingSystem.controller;

import com.Anjula.TicketingSystem.configuration.Config;
import com.Anjula.TicketingSystem.configuration.TicketStatus;
import com.Anjula.TicketingSystem.service.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/simulation")
public class TicketingController {

    @Autowired
    private TicketingService ticketingService;

    @PostMapping("/start")
    public String startSimulation(@RequestBody Config config) {
        try {
            ticketingService.startSimulation(config);
            return "Simulation started!";
        } catch (Exception e) {
            return "Error starting simulation: " + e.getMessage();
        }
    }

    @PostMapping("/stop")
    public String stopSimulation() {
        try {
            ticketingService.stopSimulation();
            return "Simulation stopped!";
        } catch (Exception e) {
            return "Error stopping simulation: " + e.getMessage();
        }
    }

    @PostMapping("/save-config")
    public String saveConfiguration(@RequestBody Config config) throws IOException {
        ticketingService.saveConfig(config);
        return "Configuration saved successfully!";
    }

    @GetMapping("/tickets/status")
    public TicketStatus getTicketStatus() {
        return ticketingService.getTicketStatus();
    }
}
