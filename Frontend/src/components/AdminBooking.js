// src/components/AdminBooking.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminBooking.css';

const AdminBooking = () => {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    // Fetch the bookings data
    axios.get('http://localhost:8001/admin/bookings')
      .then(response => {
        setBookings(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the bookings!', error);
      });
  }, []);

  const handleCancel = (id) => {
    axios.post(`http://localhost:8001/admin/cancel-booking?id=${id}`)
      .then(response => {
        // Update the state to reflect the changes
        setBookings(bookings.map(booking =>
          booking.booking_id === id ? { ...booking, booking_status: 'CANCELLED' } : booking
        ));
      })
      .catch(error => {
        console.error('There was an error canceling the booking!', error);
      });
  };

  return (
    <div className="admin-booking-container">
      <header className="admin-booking-header">
        <h1>Bookings Management</h1>
      </header>
      <div className="admin-booking-body">
        <div className="admin-booking-list">
          <h2>All Bookings</h2>
          <table className="admin-booking-table">
            <thead>
              <tr>
                <th>Booking ID</th>
                <th>Flat ID</th>
                <th>Flat Address</th>
                <th>Landlord Name</th>
                <th>Tenant Name</th>
                <th>Booking Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {bookings.map(booking => (
                <tr key={booking.booking_id}>
                  <td>{booking.booking_id}</td>
                  <td>{booking.flat.flat_id}</td>
                  <td>{booking.flat.address}</td>
                  <td>{`${booking.flat.landlord.first_name} ${booking.flat.landlord.last_name}`}</td>
                  <td>{`${booking.tenant.first_name} ${booking.tenant.last_name}`}</td>
                  <td>{booking.booking_status}</td>
                  <td>
                    <button
                      onClick={() => handleCancel(booking.booking_id)}
                      className="admin-booking-cancel-button"
                      disabled={booking.booking_status === 'CANCELLED'}
                    >
                      {booking.booking_status === 'CANCELLED' ? 'Cancelled' : 'Cancel'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default AdminBooking;
