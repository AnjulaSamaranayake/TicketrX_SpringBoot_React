import React from "react";

const LogDisplay = ({ logs }) => (
  <div className="panel log-display">
    <h2>Logs</h2>
    <ul>
      {logs.map((log, index) => (
        <li key={index}>{log}</li>
      ))}
    </ul>
  </div>
);

export default LogDisplay;
