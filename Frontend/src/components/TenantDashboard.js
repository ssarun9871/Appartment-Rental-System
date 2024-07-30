import React, { useEffect, useState } from "react";
import TenantBooking from "./TenantBooking"; // Ensure this path is correct
import FlatBooking from "./FlatBooking"; // Ensure this path is correct
import "./TenantDashboard.css";

const TenantDashboard = () => {
  const [tenantId, setTenantId] = useState("");

  useEffect(() => {
    const storedTenantId = localStorage.getItem("tenant_id");
    if (storedTenantId) {
      setTenantId(storedTenantId);
    } else {
      console.error("No tenant_id found in localStorage");
    }
  }, []);

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1>Welcome to Tenant Dashboard</h1>
      </header>
      <div className="dashboard-body">
        <div className="left-section">
          <TenantBooking tenantId={tenantId} />
        </div>
        <div className="right-section">
          <FlatBooking tenantId={tenantId} />
        </div>
      </div>
    </div>
  );
};

export default TenantDashboard;
