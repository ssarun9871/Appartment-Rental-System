import React, { useEffect, useState } from 'react';
import FlatManagement from './FlatManagement'; // Ensure this path is correct
import BookingManagement from './BookingManagement'; // Ensure this path is correct
import './LandlordDashboard.css';

const Dashboard = () => {
  const [landlordId, setLandlordId] = useState('');

  useEffect(() => {
    const storedLandlordId = localStorage.getItem('landlord_id');
    if (storedLandlordId) {
      setLandlordId(storedLandlordId);
    } else {
      console.error('No landlord_id found in localStorage');
    }
  }, []);

  return (
    <div className="landlord-dashboard-container">
      <header className="landlord-dashboard-header">
        <h1>Welcome to Landlord Dashboard</h1>
      </header>

      <div className="landlord-dashboard-body">
        <div className="landlord-left-section">
          <BookingManagement landlordId={landlordId} />
        </div>
        <div className="landlord-right-section">
          <FlatManagement landlordId={landlordId} />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
