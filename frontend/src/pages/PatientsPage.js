import React, { useState, useEffect } from 'react';
import { TextField, Box, List, ListItem, ListItemText, Button, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import { getPatients, deletePatient } from '../services/patientService';

function PatientsPage() {
  const [patients, setPatients] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedPatient, setSelectedPatient] = useState(null);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const loadPatients = async () => {
      const response = await getPatients();
      setPatients(response.data);
    };
    loadPatients();
  }, []);

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure?')) {
      await deletePatient(id);
      setPatients(patients.filter((patient) => patient.id !== id));
      setOpen(false);
    }
  };

  const filteredPatients = patients.filter(
    (patient) =>
      patient.firstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      patient.lastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      patient.email.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <Box my={2}>
      <TextField
        label="Search Patients"
        value={searchTerm}
        onChange={handleSearchChange}
        fullWidth
        margin="normal"
      />
      <List>
        {filteredPatients.map((patient) => (
          <ListItem button onClick={() => { setSelectedPatient(patient); setOpen(true); }} key={patient.id}>
            <ListItemText primary={`${patient.firstName} ${patient.lastName}`} secondary={patient.email} />
          </ListItem>
        ))}
      </List>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Patient Details</DialogTitle>
        <DialogContent>
          {selectedPatient && (
            <Box>
              <p>First Name: {selectedPatient.firstName}</p>
              <p>Last Name: {selectedPatient.lastName}</p>
              <p>Email: {selectedPatient.email}</p>
              <Button
                variant="contained"
                color="primary"
                onClick={() => { /* Add edit logic */ }}
              >
                Edit
              </Button>
              <Button
                variant="contained"
                color="secondary"
                onClick={() => handleDelete(selectedPatient.id)}
              >
                Delete
              </Button>
            </Box>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Close</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}

export default PatientsPage;
