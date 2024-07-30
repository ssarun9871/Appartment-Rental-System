import React, { useState, useEffect } from "react";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Modal from "react-modal";
import "./FlatBooking.css";

Modal.setAppElement("#root");

const FlatBooking = () => {
  const [flats, setFlats] = useState([]);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedFlatId, setSelectedFlatId] = useState(null);
  const [fromDate, setFromDate] = useState(new Date());
  const [toDate, setToDate] = useState(new Date());
  const tenantId = localStorage.getItem('tenant_id');

  useEffect(() => {
    fetchFlats();
  }, []);

  const fetchFlats = async () => {
    try {
      const response = await axios.get("http://localhost:8001/tenant/available-flats");
      const flatsData = response.data || [];
      setFlats(flatsData);
    } catch (error) {
      console.error("Error fetching flats:", error);
    }
  };

  const handleBookFlat = async () => {
    try {
      const response = await axios.post("http://localhost:8001/tenant/book-flat", {
        tenant_id: tenantId,
        flat_id: selectedFlatId,
        from_date: fromDate.toISOString().split("T")[0],
        to_date: toDate.toISOString().split("T")[0]
      });

      if (response.status === 200) {
        alert("Booking request sent for confirmation!");
        fetchFlats();
      } else {
        alert("Failed to book the flat.");
      }
      closeModal();
    } catch (error) {
      console.error("Error booking flat:", error);
      alert("An error occurred while booking the flat.");
    }
  };

  const openModal = (flatId) => {
    setSelectedFlatId(flatId);
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
    setSelectedFlatId(null);
    setFromDate(new Date());
    setToDate(new Date());
  };

  return (
    <div className="flat-booking-container">
      <section className="flats-table-section">
        <h2>Available Flats</h2>
        <table className="flats-table">
          <thead>
            <tr>
              <th>Flat ID</th>
              <th>Address</th>
              <th>Availability</th>
              <th>Rent</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {flats.length > 0 ? (
              flats.map((flat) => (
                <tr key={flat.flat_id}>
                  <td>{flat.flat_id}</td>
                  <td>{flat.address}</td>
                  <td>{flat.availability ? "Available" : "Not Available"}</td>
                  <td>Rs {flat.rent}</td>
                  <td>
                    {flat.availability && (
                      <button className="book-button" onClick={() => openModal(flat.flat_id)}>Book</button>
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5">No flats available</td>
              </tr>
            )}
          </tbody>
        </table>
      </section>

      <Modal
        isOpen={modalIsOpen}
        onRequestClose={closeModal}
        contentLabel="Book Flat"
        className="modal"
        overlayClassName="modal-overlay"
      >
        <h2 className="modal-title">Book Flat</h2>
        <div className="date-picker-container">
          <label>From Date:</label>
          <DatePicker 
            selected={fromDate} 
            onChange={(date) => setFromDate(date)} 
            dateFormat="yyyy-MM-dd"
            className="date-picker-input"
          />
        </div>
        <div className="date-picker-container">
          <label>To Date:</label>
          <DatePicker 
            selected={toDate} 
            onChange={(date) => setToDate(date)} 
            dateFormat="yyyy-MM-dd"
            className="date-picker-input"
          />
        </div>
        <div className="modal-buttons">
          <button onClick={handleBookFlat} className="book-button">Confirm Booking</button>
          <button onClick={closeModal} className="cancel-button">Cancel</button>
        </div>
      </Modal>
    </div>
  );
};

export default FlatBooking;
