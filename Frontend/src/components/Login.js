import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const location = useLocation();
  const navigate = useNavigate();
  const role = location.pathname.split('/')[2];

  const handleSubmit = async (event) => {
    event.preventDefault();
    const loginApiEndpoint = `http://localhost:8001/${role}/login`;

    try {
      const response = await fetch(loginApiEndpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      const res = await response.json();

      if (response.ok) {
        localStorage.setItem('username', res.data.username);

        // Store the correct ID based on the role
        if (role === 'tenant') {
          localStorage.setItem('tenant_id', res.data.tenant_id);
          navigate('/tenantdashboard');
        } else if (role === 'admin') {
          localStorage.setItem('id', res.data.id);
          navigate('/admindashboard');
        } else if (role === 'landlord') {
          localStorage.setItem('landlord_id', res.data.landlord_id);
          navigate('/landlorddashboard');
        }
      } else {
        setErrorMessage(res.message || 'An error occurred');
      }
    } catch (error) {
      setErrorMessage('An unexpected error occurred.');
    }
  };

  return (
    <div className="login-container">
      <h1>Login as {role.charAt(0).toUpperCase() + role.slice(1)}</h1>
      <form onSubmit={handleSubmit} className="login-form">
        <label htmlFor="username">Username</label>
        <input
          type="text"
          id="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          placeholder="Enter your username"
        />
        <label htmlFor="password">Password</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          placeholder="Enter your password"
        />
        <button type="submit" className="submit-button">Login</button>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
      </form>
    </div>
  );
};

export default Login;
