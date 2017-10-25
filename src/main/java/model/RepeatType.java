/**
 * Anuwat Angkuldee 5810401066
 */

package model;

import java.time.LocalDate;

public abstract class RepeatType {

    public String toString(Appointment appointment) {
        return appointment.getTimeToString() + "\n" +
                appointment.getName() + "\n" +
                appointment.getDescription() + "\n";
    }

    public abstract String getRepeat();
    public abstract boolean compareDate(LocalDate appointmentDate, LocalDate date);

}
