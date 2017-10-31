package server.model;

import java.time.LocalDate;

/**
 * Anuwat Angkuldee 5810401066
 */

public class NoneRepeat extends RepeatType {

    @Override
    public String getRepeat() {
        return "NONE";
    }

    @Override
    public boolean compareDate(LocalDate appointmentDate, LocalDate date) {
        return appointmentDate.compareTo(date) == 0;
    }

    @Override
    public String toString(Appointment appointment) {
        return appointment.getId() + ": " + appointment.getDateToString() + " " + super.toString(appointment);
    }
}