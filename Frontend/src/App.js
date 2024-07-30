import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login';
import LandlordDashboard from './components/LandlordDashboard';
import AdminDashboard from './components/AdminDashboard';
import TenantDashboard from './components/TenantDashboard';
import Signup from './components/Signup';
import './App.css';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login/:role" element={<Login />} />
        <Route path="/signup/:role" element={<Signup />} />
        <Route path="/landlorddashboard" element={<LandlordDashboard />} />
        <Route path="/admindashboard/*" element={<AdminDashboard />} />
        <Route path="/tenantdashboard" element={<TenantDashboard />} />
      </Routes>
    </Router>
  );
};

export default App;
