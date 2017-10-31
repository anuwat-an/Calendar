package server.model;

import java.time.LocalDate;

/**
 * Anuwat Angkuldee 5810401066
 */

public class MonthlyRepeat extends RepeatType {

    @Override
    public String getRepeat() {
        return "MONTHLY";
    }

    @Override
    public boolean compareDate(LocalDate appointmentDate, LocalDate date) {
        return appointmentDate.getDayOfMonth() == date.getDayOfMonth();
    }

    @Override
    public String toString(Appointment appointment) {
        return appointment.getId() + ": MONTHLY " + appointment.getDate().getDayOfMonth() + " " + super.toString(appointment);
    }
}
