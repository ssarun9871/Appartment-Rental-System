// src/components/AdminLandlords.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminLandlord.css'; // Import the CSS file for styling

const AdminLandlords = () => {
  const [landlords, setLandlords] = useState([]);

  useEffect(() => {
    // Fetch the landlords data
    axios.get('http://localhost:8001/admin/landlords')
      .then(response => {
        setLandlords(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the landlords!', error);
      });
  }, []);

  const handleBlock = (id) => {
    axios.post(`http://localhost:8001/admin/block-landlord?id=${id}`)
      .then(response => {
        setLandlords(landlords.map(landlord => 
          landlord.landlord_id === id ? { ...landlord, blocked: true } : landlord
        ));
      })
      .catch(error => {
        console.error('There was an error blocking the landlord!', error);
      });
  };

  const handleDelete = (id) => {
    axios.delete(`http://localhost:8001/admin/delete-landlord?id=${id}`)
      .then(response => {
        // Update the state to reflect the changes
        setLandlords(landlords.filter(landlord => landlord.landlord_id !== id));
      })
      .catch(error => {
        console.error('There was an error deleting the landlord!', error);
      });
  };

  return (
    <div className="landlords-container">
      <h1>Landlords</h1>
      <table className="landlords-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Mobile</th>
            <th>Age</th>
            <th>Status</th>
            <th>Blocked</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {landlords.map(landlord => (
            <tr key={landlord.landlord_id}>
              <td>{landlord.landlord_id}</td>
              <td>{landlord.username}</td>
              <td>{landlord.first_name}</td>
              <td>{landlord.last_name}</td>
              <td>{landlord.mobile}</td>
              <td>{landlord.age}</td>
              <td>{landlord.status}</td>
              <td>{landlord.blocked ? 'Yes' : 'No'}</td>
              <td>
                <button 
                  onClick={() => handleBlock(landlord.landlord_id)} 
                  className="block-button"
                  disabled={landlord.blocked}
                >
                  Block
                </button>
                <button 
                  onClick={() => handleDelete(landlord.landlord_id)} 
                  className="delete-button"
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminLandlords;
