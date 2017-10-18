/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.time.LocalDateTime;

public abstract class RepeatType implements Comparable<RepeatType> {

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
    public abstract boolean compareDate(LocalDateTime date);

}
