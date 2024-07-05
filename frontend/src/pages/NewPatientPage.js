import React from 'react';
import { useNavigate } from 'react-router-dom';
import PatientForm from '../components/PatientForm';
import { createPatient } from '../services/patientService';

function NewPatientPage() {
  const navigate = useNavigate();

  const handleSave = async (data) => {
    try {
      const response = await createPatient(data);
      alert('Patient added successfully');
      navigate('/new-appointment', { state: { email: response.data.email } });
    } catch (error) {
      console.error('Error adding patient', error);
      alert('Error adding patient');
    }
  };

  return <PatientForm onSave={handleSave} />;
}

export default NewPatientPage;
