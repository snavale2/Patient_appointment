import React, { useState, useEffect } from 'react';
import { TextField, Box, List, ListItem, ListItemText, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { getAppointments, deleteAppointment, updateAppointment } from '../services/appointmentService';

function AppointmentsPage() {
  const [appointments, setAppointments] = useState([]);
  const [filter, setFilter] = useState({ date: '', time: '' });
  const [editOpen, setEditOpen] = useState(false);
  const [currentAppointment, setCurrentAppointment] = useState(null);
  const [editTime, setEditTime] = useState('');

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

  const handleEditOpen = (appointment) => {
    setCurrentAppointment(appointment);
    setEditTime(appointment.appointmentTime.split('.')[0]);
    setEditOpen(true);
  };

  const handleEditClose = () => {
    setEditOpen(false);
    setCurrentAppointment(null);
    setEditTime('');
  };

  const handleEditSubmit = async () => {
    if (currentAppointment) {
      const updatedAppointment = await updateAppointment(currentAppointment.id, editTime);
      setAppointments(appointments.map((appointment) =>
        appointment.id === currentAppointment.id ? updatedAppointment.data : appointment
      ));
      handleEditClose();
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
            <Button variant="contained" color="primary" onClick={() => handleEditOpen(appointment)}>Edit</Button>
            <Button variant="contained" color="secondary" onClick={() => handleDelete(appointment.id)}>Delete</Button>
          </ListItem>
        ))}
      </List>

      <Dialog open={editOpen} onClose={handleEditClose}>
        <DialogTitle>Edit Appointment</DialogTitle>
        <DialogContent>
          <TextField
            label="Appointment Time"
            type="datetime-local"
            fullWidth
            value={editTime}
            onChange={(e) => setEditTime(e.target.value)}
            InputLabelProps={{ shrink: true }}
            margin="normal"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleEditClose} color="primary">Cancel</Button>
          <Button onClick={handleEditSubmit} color="primary">Save</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}

export default AppointmentsPage;
