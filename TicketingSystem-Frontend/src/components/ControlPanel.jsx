import React from "react";

const ControlPanel = ({ startSimulation, stopSimulation, saveConfiguration, isSimulationRunning }) => {
  return (
    <div className="panel control-panel">
      <h2>Control Panel</h2>
      <button onClick={startSimulation} disabled={isSimulationRunning}>
        Start Simulation
      </button>
      <button onClick={stopSimulation} disabled={!isSimulationRunning}>
        Stop Simulation
      </button>
      <button onClick={saveConfiguration}>Save Configuration</button>
    </div>
  );
};

export default ControlPanel;
