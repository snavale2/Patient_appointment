import React from 'react';
import { Box, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

function HomePage() {
  const navigate = useNavigate();

  return (
    <Box my={2}>
      <Button variant="contained" color="primary" onClick={() => navigate('/new-patient')}>
        New Patient
      </Button>
      <Button variant="contained" color="secondary" onClick={() => navigate('/new-appointment')}>
        Existing Patient
      </Button>
    </Box>
  );
}

export default HomePage;
