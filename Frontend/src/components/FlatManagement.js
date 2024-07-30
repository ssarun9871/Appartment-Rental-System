// src/components/FlatManagement.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import "./FlatManagement.css";

const FlatManagement = ({ landlordId: propLandlordId }) => {
  const [flats, setFlats] = useState([]);
  const [newFlat, setNewFlat] = useState({
    address: "",
    rent: "",
  });
  const [landlordId, setLandlordId] = useState(propLandlordId || "");

  useEffect(() => {
    // If landlordId is not provided as a prop, try to get it from localStorage
    if (!propLandlordId) {
      const storedLandlordId = localStorage.getItem("landlord_id");
      if (storedLandlordId) {
        setLandlordId(storedLandlordId);
      } else {
        console.error("No landlord_id found in localStorage");
      }
    }

    fetchFlats();
  }, [propLandlordId]);

  const fetchFlats = async () => {
    try {
      const endpoint = landlordId
        ? `http://localhost:8001/landlord/flats?landlordId=${landlordId}`
        : "http://localhost:8001/admin/flats";
      const response = await axios.get(endpoint);

      const flatsData = response.data.data || [];
      
      setFlats(flatsData);
    } catch (error) {
      console.error("Error fetching flats:", error);
    }
  };

  const handleAddFlat = async (e) => {
    e.preventDefault();
    if (!landlordId) {
      console.error("Landlord ID is not available");
      return;
    }

    try {
      await axios.post(
        `http://localhost:8001/landlord/flat?landlord_id=${landlordId}`,
        newFlat
      );
      fetchFlats(); // Refresh the flat list
      setNewFlat({
        address: "",
        rent: "",
      });
    } catch (error) {
      console.error("Error adding flat:", error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewFlat((prevFlat) => ({
      ...prevFlat,
      [name]: value,
    }));
  };

  return (
    <div className="flat-management-container">
      <form className="add-flat-form" onSubmit={handleAddFlat}>
        <h2>Add New Flat</h2>
        <label>
          Address:
          <input
            type="text"
            name="address"
            value={newFlat.address}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Rent:
          <input
            type="number"
            name="rent"
            value={newFlat.rent}
            onChange={handleChange}
            required
          />
        </label>
        <button type="submit">Add Flat</button>
      </form>

      <section className="flats-table-section">
        <h2>Available Flats</h2>
        <table className="flats-table">
          <thead>
            <tr>
              <th>Flat ID</th>
              <th>Address</th>
              <th>Availability</th>
              <th>Rent</th>
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
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4">No flats available</td>
              </tr>
            )}
          </tbody>
        </table>
      </section>
    </div>
  );
};

export default FlatManagement;
