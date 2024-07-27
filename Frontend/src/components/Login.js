import React, { useState, useContext } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { UserContext } from './UserContext';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const location = useLocation();
  const navigate = useNavigate(); // useNavigate instead of useHistory
  const { setUser } = useContext(UserContext);
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

      const data = await response.json();

      if (response.ok) {
        setUser({ username: data.username });
        navigate('/dashboard'); // Navigate to dashboard
      } else {
        setErrorMessage(data.message || 'An error occurred');
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
