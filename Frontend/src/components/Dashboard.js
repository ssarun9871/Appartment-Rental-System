import React, { useContext } from 'react';
import { UserContext } from './UserContext';
import './Dashboard.css';

const Dashboard = () => {
  const { user } = useContext(UserContext);

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1>Welcome to the Dashboard</h1>
        {user && <span className="user-info">Logged in as: {user.username}</span>}
      </header>
      {/* Add more dashboard content here */}
    </div>
  );
};

export default Dashboard;
