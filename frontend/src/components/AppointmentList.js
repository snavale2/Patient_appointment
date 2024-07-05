import React from 'react';
import { List, ListItem, ListItemText, ListItemSecondaryAction, IconButton, Typography } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

function AppointmentList({ appointments }) {
  return (
    <>
      {appointments.length ? (
        <List>
          {appointments.map((appointment) => (
            <ListItem key={appointment.id}>
              <ListItemText
                primary={`${appointment.patient.firstName} ${appointment.patient.lastName}`}
                secondary={new Date(appointment.appointmentTime).toLocaleString()}
              />
              <ListItemSecondaryAction>
                <IconButton edge="end">
                  <EditIcon />
                </IconButton>
                <IconButton edge="end">
                  <DeleteIcon />
                </IconButton>
              </ListItemSecondaryAction>
            </ListItem>
          ))}
        </List>
      ) : (
        <Typography variant="body1">No appointments available</Typography>
      )}
    </>
  );
}

export default AppointmentList;
