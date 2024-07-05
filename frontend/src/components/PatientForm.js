import React, { useState } from 'react';
import { TextField, Button, Box } from '@mui/material';

function PatientForm({ onSave, initialData }) {
  const [form, setForm] = useState(initialData || { firstName: '', lastName: '', email: '' });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(form);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} my={2}>
      <TextField
        label="First Name"
        name="firstName"
        value={form.firstName}
        onChange={handleChange}
        fullWidth
        margin="normal"
        autoComplete='off'
        required
      />
      <TextField
        label="Last Name"
        name="lastName"
        value={form.lastName}
        onChange={handleChange}
        fullWidth
        autoComplete='off'
        margin="normal"
        required
      />
      <TextField
        label="Email"
        name="email"
        value={form.email}
        onChange={handleChange}
        fullWidth
        autoComplete='off'
        margin="normal"
        required
      />
      <Button type="submit" variant="contained" color="primary">Save</Button>
    </Box>
  );
}

export default PatientForm;
