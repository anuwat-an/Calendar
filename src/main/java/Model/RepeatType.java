/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.time.LocalDate;

public abstract class RepeatType {

    protected Appointment appointment;

    public RepeatType(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    @Override
    public String toString() {
        return appointment.getTimeToString() + "\n" +
                appointment.getName() + "\n" +
                appointment.getDescription() + "\n";
    }

    public abstract String getRepeat();
    public abstract boolean compareDate(LocalDate date);
    public abstract int compareTo(RepeatType o);

}
