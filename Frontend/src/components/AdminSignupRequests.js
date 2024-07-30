// src/components/AdminSignupRequests.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminSignupRequests.css'; // Import the CSS file for styling

const AdminSignupRequests = () => {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    // Fetch the signup requests data
    axios.get('http://localhost:8001/admin/signup-requests')
      .then(response => {
        setRequests(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the signup requests!', error);
      });
  }, []);

  const handleApprove = (id, role) => {
    axios.post(`http://localhost:8001/admin/approve-signup?role=${role}&id=${id}`)
      .then(() => {
        alert(`Approved ${role} with ID: ${id}`);
        // Refresh the data after approval
        refreshRequests();
      })
      .catch(error => {
        console.error('There was an error approving the signup request!', error);
      });
  };

  const handleReject = (id, role) => {
    axios.post(`http://localhost:8001/admin/reject-signup?role=${role}&id=${id}`)
      .then(() => {
        alert(`Rejected ${role} with ID: ${id}`);
        // Refresh the data after rejection
        refreshRequests();
      })
      .catch(error => {
        console.error('There was an error rejecting the signup request!', error);
      });
  };

  const refreshRequests = () => {
    axios.get('http://localhost:8001/admin/signup-requests')
      .then(response => {
        setRequests(response.data);
      })
      .catch(error => {
        console.error('There was an error refreshing the signup requests!', error);
      });
  };

  return (
    <div className="signup-requests-container">
      <h1>Signup Requests</h1>
      <table className="signup-requests-table">
        <thead>
          <tr>
            <th>Role</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
            <th>Mobile</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {requests.map(request => {
            const role = request.tenant_id ? 'Tenant' : 'Landlord';
            const id = request.tenant_id || request.landlord_id;
            return (
              <tr key={id}>
                <td>{role}</td>
                <td>{request.first_name}</td>
                <td>{request.last_name}</td>
                <td>{request.username}</td>
                <td>{request.mobile}</td>
                <td>
                  <button onClick={() => handleApprove(id, role)} className="approve-button">Approve</button>
                  <button onClick={() => handleReject(id, role)} className="reject-button">Reject</button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default AdminSignupRequests;
