import React from "react";

const TicketDisplay = ({ ticketStatus }) => (
  <div className="ticket-display">
    <h2>Ticket Status</h2>
    <p>Remaining Tickets: {ticketStatus.remainingTickets}</p>
    <p>Current Pool Size: {ticketStatus.poolSize}</p>
  </div>
);

export default TicketDisplay;
