import React, { useState, useEffect } from "react";
import ConfigurationForm from "./components/ConfigurationForm";
import TicketDisplay from "./components/TicketDisplay";
import ControlPanel from "./components/ControlPanel";
import LogDisplay from "./components/LogDisplay";
import API from "./api";
import "./App.css";

const App = () => {
  const [config, setConfig] = useState({
    totalTickets: [null],
    ticketReleaseRate: [null],
    customerRetrieveRate: [null],
    maxTicketCapacity: [null],
  });

  const [ticketStatus, setTicketStatus] = useState({
    remainingTickets: 0,
    poolSize: 0,
  });

  const [logs, setLogs] = useState([]);
  const [isSimulationRunning, setIsSimulationRunning] = useState(false);

  // Logs update function
  const updateLogs = (message) => {
    setLogs((prevLogs) => [...prevLogs, `[${new Date().toLocaleTimeString()}] ${message}`]);
  };

  // Fetch ticket status from the backend
  const fetchTicketStatus = async () => {
    try {
      const response = await API.get("/api/simulation/tickets/status");
      const { remainingTickets, poolSize } = response.data;
      setTicketStatus({ remainingTickets, poolSize });
      updateLogs(`Updated: ${remainingTickets} tickets left, pool size: ${poolSize}`);
    } catch (error) {
      updateLogs("Error fetching ticket status: " + error.message);
    }
  };

  // Start the simulation
  const startSimulation = async () => {
    try {
      await API.post("/api/simulation/start", config);
      setIsSimulationRunning(true);
      updateLogs("Simulation started!");
    } catch (error) {
      updateLogs("Error starting simulation: " + error.message);
    }
  };

  // Stop the simulation
  const stopSimulation = async () => {
    try {
      await API.post("/api/simulation/stop");
      setIsSimulationRunning(false);
      updateLogs("Simulation stopped!");
    } catch (error) {
      updateLogs("Error stopping simulation: " + error.message);
    }
  };

  // Save the configuration
  const saveConfiguration = async () => {
    try {
      await API.post("/api/simulation/save-config", config);
      updateLogs("Configuration saved successfully!");
    } catch (error) {
      updateLogs("Error saving configuration: " + error.message);
    }
  };

  // Poll for ticket status when simulation is running
  useEffect(() => {
    const interval = setInterval(() => {
      if (isSimulationRunning) {
        fetchTicketStatus();
      }
    }, 1000);

    return () => clearInterval(interval); // Cleanup on unmount
  }, [isSimulationRunning]);

  return (
    <div className="app">
      <h1>Real-Time Ticketing System</h1>
      <div className="container">
        <ConfigurationForm config={config} setConfig={setConfig} />
        <TicketDisplay ticketStatus={ticketStatus} />
        <ControlPanel
          startSimulation={startSimulation}
          stopSimulation={stopSimulation}
          saveConfiguration={saveConfiguration}
          isSimulationRunning={isSimulationRunning}
        />
      </div>
      <LogDisplay logs={logs} />
    </div>
  );
};

export default App;
