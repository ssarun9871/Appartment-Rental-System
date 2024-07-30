// src/components/AdminDashboard.js
import React from 'react';
import { Link, Route, Routes, Navigate } from 'react-router-dom';
import './AdminDashboard.css';
import AdminSignupRequests from './AdminSignupRequests'; // Import the SignupRequests component
import AdminLandlord from './AdminLandlord'; // Assuming Landlord component is available
import AdminTenants from './AdminTenants'; // Assuming Tenants component is available
import AdminBooking from './AdminBooking'; // Assuming Bookings component is available

const AdminDashboard = () => {
  return (
    <div className="admin-dashboard-container">
      <header className="admin-dashboard-header">
        <h1>Welcome to Admin Dashboard</h1>
      </header>
      <nav className="admin-dashboard-nav">
        <ul>
          <li><Link to="signup-requests">Signup Requests</Link></li>
          <li><Link to="landlord">Landlord</Link></li>
          <li><Link to="tenants">Tenants</Link></li>
          <li><Link to="bookings">Bookings</Link></li>
        </ul>
      </nav>
      <div className="admin-dashboard-body">
        <Routes>
          <Route path="signup-requests" element={<AdminSignupRequests />} /> {/* Default view */}
          <Route path="landlord" element={<AdminLandlord />} />
          <Route path="tenants" element={<AdminTenants />} />
          <Route path="bookings" element={<AdminBooking />} />
          <Route path="*" element={<Navigate to="signup-requests" />} /> {/* Redirect to Signup Requests */}
        </Routes>
      </div>
    </div>
  );
};

export default AdminDashboard;
