import React, { useState, useEffect } from 'react';
import { TextField, Button, Box, MenuItem } from '@mui/material';
import { getPatients } from '../services/patientService';

function AppointmentForm({ onSave, initialData }) {
  const [form, setForm] = useState(initialData || { email: '', appointmentTime: '' });
  const [patients, setPatients] = useState([]);

  useEffect(() => {
    const loadPatients = async () => {
      const response = await getPatients();
      setPatients(response.data);
    };
    loadPatients();

    if (initialData) {
      setForm(initialData);
    }
  }, [initialData]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const formattedTime = new Date(form.appointmentTime).toISOString().split('.')[0];
    onSave({ ...form, appointmentTime: formattedTime });
  };

  return (
    <Box
    display="flex"
    justifyContent="center"
    alignItems="center"
    height="50vh"
    >
    <Box component="form" onSubmit={handleSubmit} my={2}>
      <TextField
        select
        label="Select Patient"
        name="email"
        value={form.email}
        onChange={handleChange}
        fullWidth
        margin="normal"
        required
      >
        {patients.map((patient) => (
          <MenuItem key={patient.id} value={patient.email}>
            {patient.firstName} {patient.lastName} ({patient.email})
          </MenuItem>
        ))}
      </TextField>
      <TextField
        label="Appointment Time"
        name="appointmentTime"
        value={form.appointmentTime}
        onChange={handleChange}
        type="datetime-local"
        fullWidth
        margin="normal"
        required
        InputLabelProps={{ shrink: true }}
      />
      <Box
        display="flex"
        justifyContent="center"
        width="100%"
        mt={2}
      >
        <Button type="submit" variant="contained" color="primary">
          Submit
        </Button>
      </Box>
    </Box>
    </Box>
  );
}

export default AppointmentForm;
