// src/Signup.js
import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Signup.css'; // Import the CSS file for styling

const Signup = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [age, setAge] = useState('');
  const [mobile, setMobile] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const location = useLocation();
  const role = location.pathname.split('/')[2]; // Extract role from URL path

  const handleSubmit = async (event) => {
    event.preventDefault();
    const signupApiEndpoint = `/api/signup/${role}`; // Adjust this as per your API structure

    try {
      const response = await fetch(signupApiEndpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ firstName, lastName, age, mobile, username, password }),
      });

      if (response.ok) {
        // Handle successful signup
        const data = await response.json();
        console.log('Signup successful:', data);
        // Redirect or update UI as needed
      } else {
        // Handle signup error
        const error = await response.json();
        console.error('Signup failed:', error);
      }
    } catch (error) {
      console.error('An error occurred:', error);
    }
  };

  return (
    <div className="signup-container">
      <h1>Sign Up as {role.charAt(0).toUpperCase() + role.slice(1)}</h1>
      <form onSubmit={handleSubmit} className="signup-form">
        <div className="form-group">
          <label htmlFor="firstName">First Name</label>
          <input
            type="text"
            id="firstName"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            placeholder="John"
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="lastName">Last Name</label>
          <input
            type="text"
            id="lastName"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            placeholder="Doe"
            required
          />
        </div>
        <div className="form-group half-width">
          <label htmlFor="age">Age</label>
          <input
            type="number"
            id="age"
            value={age}
            onChange={(e) => setAge(e.target.value)}
            placeholder="30"
            required
          />
        </div>
        <div className="form-group half-width">
          <label htmlFor="mobile">Mobile</label>
          <input
            type="tel"
            id="mobile"
            value={mobile}
            onChange={(e) => setMobile(e.target.value)}
            placeholder="+1234567890"
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="johndoe"
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="********"
            required
          />
        </div>
        <button type="submit" className="submit-button">Sign Up</button>
      </form>
    </div>
  );
};

export default Signup;
