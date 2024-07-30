import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './TenantBooking.css';

const TenantBooking = ({ tenantId }) => {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    if (tenantId) {
      fetchBookings(tenantId);
    }
  }, [tenantId]);

  const fetchBookings = async (tenantId) => {
    try {
      const response = await axios.get(`http://localhost:8001/tenant/bookings/${tenantId}`);
      const bookingsData = response.data || [];
      setBookings(bookingsData);
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  };

  return (
    <div className="tenant-booking-container">
      <h2>Your Bookings</h2>
      {bookings.length > 0 ? (
        <table className="bookings-table">
          <thead>
            <tr>
              <th>Flat Address</th>
              <th>From Date</th>
              <th>To Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking) => (
              <tr key={booking.booking_id}>
                <td>{booking.flat.address}</td>
                <td>{new Date(booking.from_date).toLocaleDateString()}</td>
                <td>{new Date(booking.to_date).toLocaleDateString()}</td>
                <td>{booking.booking_status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No bookings available.</p>
      )}
    </div>
  );
};

export default TenantBooking;
