// src/Home.js
import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css'; // Import the CSS file for styling

const Home = () => {
  return (
    <div className="home-container">
      <h1>Welcome to Apartment Rental</h1>
      <div className="options-container">
        <div className="option-box">
          <h2>Login</h2>
          <Link to="/login/admin" className="link-button">Login as Admin</Link>
          <Link to="/login/landlord" className="link-button">Login as Landlord</Link>
          <Link to="/login/tenant" className="link-button">Login as Tenant</Link>
        </div>
        <div className="option-box">
          <h2>Sign Up</h2>
          <Link to="/signup/landlord" className="link-button">Register as Landlord</Link>
          <Link to="/signup/tenant" className="link-button">Register as Tenant</Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
