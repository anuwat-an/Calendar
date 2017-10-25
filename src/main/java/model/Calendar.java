/**
 * Anuwat Angkuldee 5810401066
 */

package model;

import java.util.ArrayList;

public class Calendar {

    private ArrayList<Appointment> appointments = new ArrayList<>();

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

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

}
