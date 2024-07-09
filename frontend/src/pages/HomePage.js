import React from 'react';
import { Box, Button, Stack } from '@mui/material';
import { useNavigate } from 'react-router-dom';

function HomePage() {
  const navigate = useNavigate();

  const buttonStyles = {
    width: '200px',
    height: '50px',
  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      height="50vh"
    >
      <Stack spacing={2} alignItems="center">
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate('/new-patient')}
          sx={buttonStyles}
        >
          New Patient
        </Button>
        <Button
          variant="contained"
          color="secondary"
          onClick={() => navigate('/new-appointment')}
          sx={buttonStyles}
        >
          Existing Patient
        </Button>
      </Stack>
    </Box>
  );
}

export default HomePage;
