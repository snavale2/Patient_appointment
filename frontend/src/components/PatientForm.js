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
    <Box
    display="flex"
    justifyContent="center"
    alignItems="center"
    height="50vh"
    >
    <Box component="form"
        onSubmit={handleSubmit}
        my={2}
        display="flex"
        flexDirection="column"
        alignItems="center"
        width="50%"
    >
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

export default PatientForm;
