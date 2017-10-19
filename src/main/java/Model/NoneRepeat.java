/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.time.LocalDate;

public class NoneRepeat extends RepeatType {

    public NoneRepeat(Appointment appointment) {
        super(appointment);
    }

    @Override
    public String getRepeat() {
        return "NONE";
    }

    @Override
    public String toString() {
        return appointment.getId() + ": " + appointment.getDateToString() + " " + super.toString();
    }

    @Override
    public boolean compareDate(LocalDate date) {
        return appointment.getDate().toLocalDate().compareTo(date) == 0;
    }

    /** fix bug & OCP */
    @Override
    public int compareTo(RepeatType o) {
        if (o instanceof DailyRepeat || o instanceof WeeklyRepeat || o instanceof MonthlyRepeat)
            return -1;
        return 0;//appointment.getDate().compareTo(o.getAppointment().getDate());
    }
}