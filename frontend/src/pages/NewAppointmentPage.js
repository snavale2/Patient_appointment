import React from 'react';
import { useLocation } from 'react-router-dom';
import AppointmentForm from '../components/AppointmentForm';
import { createAppointment } from '../services/appointmentService';

function NewAppointmentPage() {
  const location = useLocation();
  const initialEmail = location.state ? location.state.email : '';

  const handleSave = async (data) => {
    try {
      await createAppointment(data);
      alert('Appointment booked successfully');
    } catch (error) {
      console.error('Error booking appointment', error);
      alert('Error booking appointment');
    }
  };

  return <AppointmentForm onSave={handleSave} initialData={{ email: initialEmail }} />;
}

export default NewAppointmentPage;
