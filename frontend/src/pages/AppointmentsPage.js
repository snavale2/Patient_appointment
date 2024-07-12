import React, { useState, useEffect } from 'react';
import { TextField, Box, List, ListItem, ListItemText, Button } from '@mui/material';
import { getAppointments, deleteAppointment } from '../services/appointmentService';

function AppointmentsPage() {
  const [appointments, setAppointments] = useState([]);
  const [filter, setFilter] = useState({ date: '', time: '' });

  useEffect(() => {
    const loadAppointments = async () => {
      const response = await getAppointments();
      setAppointments(response.data);
    };
    loadAppointments();
  }, []);

  const handleFilterChange = (e) => {
    setFilter({ ...filter, [e.target.name]: e.target.value });
  };

  const handleFilter = async () => {
    const params = {};
    if (filter.date) {
      params.date = filter.date;
    }
    if (filter.time) {
      params.time = filter.time;
    }

    const queryString = new URLSearchParams(params).toString();
    const response = await getAppointments(queryString);
    setAppointments(response.data);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure?')) {
      await deleteAppointment(id);
      setAppointments(appointments.filter((appointment) => appointment.id !== id));
    }
  };

  return (
    <Box my={2}>
      <TextField
        label="Date"
        type="date"
        name="date"
        value={filter.date}
        onChange={handleFilterChange}
        fullWidth
        margin="normal"
        InputLabelProps={{ shrink: true }}
      />
      <TextField
        label="Time"
        type="time"
        name="time"
        value={filter.time}
        onChange={handleFilterChange}
        fullWidth
        margin="normal"
        InputLabelProps={{ shrink: true }}
      />
      <Button variant="contained" color="primary" onClick={handleFilter}>Filter</Button>
      <List>
        {appointments.map((appointment) => (
          <ListItem key={appointment.id}>
            <ListItemText
              primary={`Appointment with ${appointment.patient.firstName} ${appointment.patient.lastName}`}
              secondary={`${new Date(appointment.appointmentTime).toLocaleString()}`}
            />
            <Button variant="contained" color="secondary" onClick={() => handleDelete(appointment.id)}>Delete</Button>
          </ListItem>
        ))}
      </List>
    </Box>
  );
}

export default AppointmentsPage;
