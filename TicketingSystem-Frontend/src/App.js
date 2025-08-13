import React, { useState, useEffect } from "react";
import { FiSettings, FiPlay, FiStop, FiSave, FiInfo, FiActivity } from "react-icons/fi";
import ConfigurationForm from "./components/ConfigurationForm";
import TicketDisplay from "./components/TicketDisplay";
import ControlPanel from "./components/ControlPanel";
import LogDisplay from "./components/LogDisplay";
import API from "./api";
import "./App.css";

const App = () => {
  const [config, setConfig] = useState({
    totalTickets: 100,
    ticketReleaseRate: 5,
    customerRetrieveRate: 2,
    maxTicketCapacity: 20,
  });

  const [ticketStatus, setTicketStatus] = useState({
    remainingTickets: 0,
    poolSize: 0,
  });

  const [logs, setLogs] = useState([]);
  const [isSimulationRunning, setIsSimulationRunning] = useState(false);
  const [activeTab, setActiveTab] = useState("about");

  // Logs update function
  const updateLogs = (message) => {
    setLogs((prevLogs) => [...prevLogs.slice(-99), `[${new Date().toLocaleTimeString()}] ${message}`]);
  };

  // Fetch ticket status from the backend
  const fetchTicketStatus = async () => {
    try {
      const response = await API.get("/api/simulation/tickets/status");
      const { remainingTickets, poolSize } = response.data;
      setTicketStatus({ remainingTickets, poolSize });
      updateLogs(`Ticket update: ${remainingTickets} remaining, ${poolSize} in pool`);
    } catch (error) {
      updateLogs("Error fetching ticket status: " + error.message);
    }
  };

  // Start the simulation
  const startSimulation = async () => {
    try {
      await API.post("/api/simulation/start", config);
      setIsSimulationRunning(true);
      updateLogs("Simulation started with current configuration");
      fetchTicketStatus();
    } catch (error) {
      updateLogs("Error starting simulation: " + error.message);
    }
  };

  // Stop the simulation
  const stopSimulation = async () => {
    try {
      await API.post("/api/simulation/stop");
      setIsSimulationRunning(false);
      updateLogs("Simulation stopped");
    } catch (error) {
      updateLogs("Error stopping simulation: " + error.message);
    }
  };

  // Save the configuration
  const saveConfiguration = async () => {
    try {
      await API.post("/api/simulation/save-config", config);
      updateLogs("Configuration saved successfully");
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

    return () => clearInterval(interval);
  }, [isSimulationRunning]);

  return (
    <div className="app">
      <header className="app-header">
        <div className="header-content">
          <h1>
            <span className="logo-icon">🎫</span> 
            <span className="logo-text">TicketeX</span>
            <span className="tagline">Ticket Distribution Simulation</span>
          </h1>
          <nav className="tabs">
            <button 
              className={activeTab === "about" ? "active" : ""}
              onClick={() => setActiveTab("about")}
            >
              <FiInfo className="tab-icon" /> About
            </button>
            <button 
              className={activeTab === "simulation" ? "active" : ""}
              onClick={() => setActiveTab("simulation")}
            >
              <FiActivity className="tab-icon" /> Simulation
            </button>
          </nav>
        </div>
      </header>

      <main className="main-content">
        {activeTab === "about" && (
          <div className="about-section">
            <h2>Welcome to TicketrX</h2>
            <p className="intro-text">
              TicketrX is an advanced simulation platform that models real-world ticket distribution systems. 
              Configure the parameters to observe how tickets flow through the system under different conditions.
            </p>
            
            <div className="feature-cards">
              <div className="feature-card">
                <div className="card-icon">🔢</div>
                <h3>Total Tickets</h3>
                <p>The complete inventory of tickets available for distribution</p>
              </div>
              <div className="feature-card">
                <div className="card-icon">⏱️</div>
                <h3>Release Rate</h3>
                <p>How many tickets are released into the pool per time interval</p>
              </div>
              <div className="feature-card">
                <div className="card-icon">👥</div>
                <h3>Retrieve Rate</h3>
                <p>How many tickets customers take from the pool per time interval</p>
              </div>
              <div className="feature-card">
                <div className="card-icon">📊</div>
                <h3>Pool Capacity</h3>
                <p>Maximum tickets that can be available in the pool at once</p>
              </div>
            </div>

            <div className="getting-started">
              <h3>Getting Started</h3>
              <ol>
                <li>Navigate to the Simulation tab</li>
                <li>Configure your desired parameters</li>
                <li>Save your configuration</li>
                <li>Start the simulation</li>
                <li>Observe the ticket flow in real-time</li>
              </ol>
            </div>
          </div>
        )}

        {activeTab === "simulation" && (
          <div className="simulation-container">
            <div className="config-section">
              <ConfigurationForm config={config} setConfig={setConfig} />
              <ControlPanel
                startSimulation={startSimulation}
                stopSimulation={stopSimulation}
                saveConfiguration={saveConfiguration}
                isSimulationRunning={isSimulationRunning}
              />
            </div>
            
            <div className="visualization-section">
              <TicketDisplay ticketStatus={ticketStatus} />
              
              <div className="system-diagram">
                <div className="inventory">
                  <h4>Ticket Inventory</h4>
                  <div className="ticket-count">{ticketStatus.remainingTickets}</div>
                  <div className="label">Total: {config.totalTickets}</div>
                </div>
                <div className="arrow">⇨</div>
                <div className="pool">
                  <h4>Ticket Pool</h4>
                  <div className="ticket-count">{ticketStatus.poolSize}</div>
                  <div className="label">Max: {config.maxTicketCapacity}</div>
                  <div className="rate-label">Release: {config.ticketReleaseRate}/sec</div>
                </div>
                <div className="arrow">⇨</div>
                <div className="customers">
                  <h4>Customers</h4>
                  <div className="ticket-count">-</div>
                  <div className="label">Retrieve: {config.customerRetrieveRate}/sec</div>
                </div>
              </div>
            </div>
            
            <LogDisplay logs={logs} />
          </div>
        )}
      </main>

      <footer className="app-footer">
        <div className="footer-content">
          <div className="footer-section">
            <h4>TicketeX</h4>
            <p>Advanced ticket distribution simulation platform</p>
          </div>
          <div className="footer-section">
            <h4>Links</h4>
            <ul>
              <li><a href="https://github.com/AnjulaSamaranayake/TicketrX_SpringBoot_React">GitHub Repository</a></li>

            </ul>
          </div>
          <div className="footer-section">
            <h4>Contact</h4>
            <ul>
              <li>samaranayakea@icloud.com</li>
              <li>+94 71 651 3532</li>
            </ul>
          </div>
        </div>
        <div className="footer-bottom">
          <p>© {new Date().getFullYear()} TicketrX. All rights reserved.</p>
          <p>Developed by ANJU.</p>
        </div>
      </footer>
    </div>
  );
};

export default App;