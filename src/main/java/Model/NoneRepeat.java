/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.time.LocalDateTime;

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
    public boolean compareDate(LocalDateTime date) {
        return appointment.getDate().compareTo(date) == 0;
    }

    @Override
    public int compareTo(RepeatType o) {
        if (o instanceof DailyRepeat || o instanceof WeeklyRepeat || o instanceof MonthlyRepeat)
            return -1;
        return appointment.getDate().compareTo(o.getAppointment().getDate());
    }
}