import axios from 'axios';

const API_URL = 'http://localhost:9090/api/patients';

export const getPatients = () => {
  return axios.get(API_URL);
};

export const createPatient = (patient) => {
  return axios.post(API_URL, patient);
};

export const updatePatient = (id, patient) => {
  return axios.put(`${API_URL}/${id}`, patient);
};

export const deletePatient = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};

export const searchPatients = (query) => {
  return axios.get(`${API_URL}/search?query=${query}`);
};
