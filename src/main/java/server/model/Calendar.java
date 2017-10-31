package server.model;

import java.util.Vector;

/**
 * Anuwat Angkuldee 5810401066
 */

public class Calendar {

    private Vector<Appointment> appointments = new Vector<>();

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public Appointment getAppointment(int id) {
        for (Appointment apt : appointments) {
            if (apt.getId() == id)
                return apt;
        }
        return null;
    }

    public void deleteAppointment(int id) {
        this.appointments.remove(getAppointment(id));
    }

    public Vector<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Vector<Appointment> appointments) {
        this.appointments = appointments;
    }

}
