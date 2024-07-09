import axios from 'axios';

const API_URL = 'http://localhost:8080/api/appointments';

export const getAppointments = () => {
  return axios.get(API_URL);
};

export const createAppointment = (appointment) => {
  return axios.post(API_URL, null, { params: appointment });
};

export const updateAppointment = (id, appointment) => {
  return axios.put(`${API_URL}/${id}`, appointment);
};

export const deleteAppointment = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};

export const filterAppointments = (date, time) => {
  const params = new URLSearchParams();
  if (date) params.append('date', date);
  if (time) params.append('appointmentTime', time);
  return axios.get(`${API_URL}/filter?${params.toString()}`);
};
