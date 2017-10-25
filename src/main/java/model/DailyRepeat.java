/**
 * Anuwat Angkuldee 5810401066
 */

package model;

import java.time.LocalDate;

public class DailyRepeat extends RepeatType {

    @Override
    public String getRepeat() {
        return "DAILY";
    }

    @Override
    public boolean compareDate(LocalDate appointmentDate, LocalDate date) {
        return true;
    }

    @Override
    public String toString(Appointment appointment) {
        return appointment.getId() + ": DAILY " + super.toString(appointment);
    }
}
