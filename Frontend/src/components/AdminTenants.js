// src/components/AdminTenants.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AdminTenant.css';

const AdminTenants = () => {
  const [tenants, setTenants] = useState([]);
  const [newTenant, setNewTenant] = useState({
    username: '',
    password: '',
    first_name: '',
    last_name: '',
    mobile: '',
    age: '',
  });

  useEffect(() => {
    // Fetch the tenants data
    axios.get('http://localhost:8001/admin/tenants')
      .then(response => {
        setTenants(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the tenants!', error);
      });
  }, []);

  const handleBlock = (id) => {
    axios.post(`http://localhost:8001/admin/block-tenant?id=${id}`)
      .then(response => {
        setTenants(tenants.map(tenant => 
          tenant.tenant_id === id ? { ...tenant, blocked: true } : tenant
        ));
      })
      .catch(error => {
        console.error('There was an error blocking the tenant!', error);
      });
  };

  const handleDelete = (id) => {
    axios.delete(`http://localhost:8001/admin/delete-tenant?id=${id}`)
      .then(response => {
        // Update the state to reflect the changes
        setTenants(tenants.filter(tenant => tenant.tenant_id !== id));
      })
      .catch(error => {
        console.error('There was an error deleting the tenant!', error);
      });
  };

  const handleAdd = () => {
    axios.post('http://localhost:8001/admin/tenants', newTenant)
      .then(response => {
        setTenants([...tenants, response.data]);
        setNewTenant({
          username: '',
          password: '',
          first_name: '',
          last_name: '',
          mobile: '',
          age: '',
        });
      })
      .catch(error => {
        console.error('There was an error adding the tenant!', error);
      });
  };

  return (
    <div className="admin-tenant-container">
      <header className="admin-tenant-header">
        <h1 color='black'>Tenants Management</h1>
      </header>

      <div className="admin-tenant-body">
    

        <div className="admin-tenant-list">
          <h2>Tenants</h2>
          <table className="admin-tenant-table">
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
              {tenants.map(tenant => (
                <tr key={tenant.tenant_id}>
                  <td>{tenant.tenant_id}</td>
                  <td>{tenant.username}</td>
                  <td>{tenant.first_name}</td>
                  <td>{tenant.last_name}</td>
                  <td>{tenant.mobile}</td>
                  <td>{tenant.age}</td>
                  <td>{tenant.status}</td>
                  <td>{tenant.blocked ? 'Yes' : 'No'}</td>
                  <td>
                    <button 
                      onClick={() => handleBlock(tenant.tenant_id)} 
                      className="admin-tenant-block-button"
                      disabled={tenant.blocked}
                    >
                      Block
                    </button>
                    <button 
                      onClick={() => handleDelete(tenant.tenant_id)} 
                      className="admin-tenant-delete-button"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="admin-tenant-add">
          <h2>Add Tenant</h2>
          <div className="admin-tenant-add-form">
            <input 
              type="text" 
              placeholder="Username" 
              value={newTenant.username} 
              onChange={(e) => setNewTenant({ ...newTenant, username: e.target.value })} 
            />
            <input 
              type="password" 
              placeholder="Password" 
              value={newTenant.password} 
              onChange={(e) => setNewTenant({ ...newTenant, password: e.target.value })} 
            />
            <input 
              type="text" 
              placeholder="First Name" 
              value={newTenant.first_name} 
              onChange={(e) => setNewTenant({ ...newTenant, first_name: e.target.value })} 
            />
            <input 
              type="text" 
              placeholder="Last Name" 
              value={newTenant.last_name} 
              onChange={(e) => setNewTenant({ ...newTenant, last_name: e.target.value })} 
            />
            <input 
              type="text" 
              placeholder="Mobile" 
              value={newTenant.mobile} 
              onChange={(e) => setNewTenant({ ...newTenant, mobile: e.target.value })} 
            />
            <input 
              type="number" 
              placeholder="Age" 
              value={newTenant.age} 
              onChange={(e) => setNewTenant({ ...newTenant, age: e.target.value })} 
            />
            <button 
              onClick={handleAdd} 
              className="admin-tenant-add-button"
            >
              Add Tenant
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminTenants;
