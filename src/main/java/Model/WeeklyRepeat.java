/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.time.LocalDateTime;

public class WeeklyRepeat extends RepeatType {

    public WeeklyRepeat(Appointment appointment) {
        super(appointment);
    }

    @Override
    public String getRepeat() {
        return "WEEKLY";
    }

    @Override
    public String toString() {
        return appointment.getId() + ": " + appointment.getDate().getDayOfWeek() + " " + super.toString();
    }

    @Override
    public boolean compareDate(LocalDateTime date) {
        return appointment.getDate().getDayOfWeek().getValue() == date.getDayOfWeek().getValue();
    }

    @Override
    public int compareTo(RepeatType o) {
        if (o instanceof NoneRepeat || o instanceof DailyRepeat)
            return 1;
        else if (o instanceof MonthlyRepeat)
            return -1;
        return appointment.getDate().getMonthValue() - o.getAppointment().getDate().getMonthValue();
    }
}