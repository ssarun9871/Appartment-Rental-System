// src/components/BookingManagement.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './BookingManagement.css';

const BookingManagement = ({ landlordId: propLandlordId }) => {
  const [bookings, setBookings] = useState([]);
  const [landlordId, setLandlordId] = useState(propLandlordId || '');

  useEffect(() => {
    const fetchLandlordId = async () => {
      if (!propLandlordId) {
        const storedLandlordId = localStorage.getItem('landlord_id');
        if (storedLandlordId) {
          setLandlordId(storedLandlordId);
          fetchBookings(storedLandlordId);
        } else {
          console.error('No landlord_id found in localStorage');
        }
      } else {
        setLandlordId(propLandlordId);
        fetchBookings(propLandlordId);
      }
    };

    fetchLandlordId();
  }, [propLandlordId]);

  useEffect(() => {
    if (landlordId) {
      fetchBookings(landlordId);
    }
  }, [landlordId]);

  const fetchBookings = async (landlordId) => {
    try {
      const endpoint = landlordId
        ? `http://localhost:8001/landlord/bookings?landlordId=${landlordId}`
        : 'http://localhost:8001/admin/bookings';
      const response = await axios.get(endpoint);

      const bookingsData = response.data.data || []; // Extracting data from response

      setBookings(bookingsData);
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  };

  const handleAction = async (bookingId, action) => {
    try {
      const endpoint = action === 'CONFIRMED'
        ? `http://localhost:8001/landlord/booking/confirm?bookingId=${bookingId}`
        : `http://localhost:8001/landlord/booking/reject?bookingId=${bookingId}`;
      await axios.post(endpoint);
      fetchBookings(landlordId); // Refresh the bookings list
    } catch (error) {
      console.error(`Error ${action === 'CONFIRMED' ? 'confirming' : 'rejecting'} booking:`, error.response || error.message);
    }
  };

  return (
    <div className="booking-management-container">
      <h2>Booking Management</h2>
      {bookings.length > 0 ? (
        <table className="bookings-table">
          <thead>
            <tr>
              <th>Tenant</th>
              <th>Flat Address</th>
              <th>From Date</th>
              <th>To Date</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking) => (
              <tr key={booking.booking_id}>
                <td>{booking.tenant.first_name} {booking.tenant.last_name}</td>
                <td>{booking.flat.address}</td>
                <td>{new Date(booking.from_date).toLocaleDateString()}</td>
                <td>{new Date(booking.to_date).toLocaleDateString()}</td>
                <td>{booking.booking_status}</td>
                <td>
                  {booking.booking_status === 'PENDING' ? (
                    <>
                      <button
                        onClick={() => handleAction(booking.booking_id, 'CONFIRMED')}
                        className="confirm-button"
                      >
                        Confirm
                      </button>
                      <button
                        onClick={() => handleAction(booking.booking_id, 'REJECTED')}
                        className="reject-button"
                      >
                        Reject
                      </button>
                    </>
                  ) : (
                    <span>{booking.booking_status === 'CONFIRMED' ? 'Confirmed' : 'Rejected'}</span>
                  )}
                </td>
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

export default BookingManagement;
