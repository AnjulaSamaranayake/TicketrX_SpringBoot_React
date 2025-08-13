import React from "react";

const ConfigurationForm = ({ config, setConfig }) => {
  const handleChange = (e) => {
    const { name, value } = e.target;
    const numValue = Number(value);
    if (numValue < 0) return; // Prevent negative input
    setConfig({ ...config, [name]: numValue });
  };

  return (
    <div className="panel configuration-form">
      <h2>Configuration Settings</h2>
      <form>
        <label>
          <input
            type="number"
            name="totalTickets"
            value={config.totalTickets}
            onChange={handleChange}
            placeholder="Enter total tickets"
          />
        </label>
        <label>
          <input
            type="number"
            name="ticketReleaseRate"
            value={config.ticketReleaseRate}
            onChange={handleChange}
            placeholder="Enter release rate"
          />
        </label>
        <label>
          <input
            type="number"
            name="customerRetrieveRate"
            value={config.customerRetrieveRate}
            onChange={handleChange}
            placeholder="Enter retrieval rate"
          />
        </label>
        <label>
          <input
            type="number"
            name="maxTicketCapacity"
            value={config.maxTicketCapacity}
            onChange={handleChange}
            placeholder="Enter max capacity"
          />
        </label>
      </form>
    </div>
  );
};

export default ConfigurationForm;
