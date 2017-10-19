/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.time.LocalDate;

public class DailyRepeat extends RepeatType {

    public DailyRepeat(Appointment appointment) {
        super(appointment);
    }

    @Override
    public String getRepeat() {
        return "DAILY";
    }

    @Override
    public String toString() {
        return appointment.getId() + ": DAILY " + super.toString();
    }

    @Override
    public boolean compareDate(LocalDate date) {
        return true;
    }

    /** fix bug & OCP */
    @Override
    public int compareTo(RepeatType o) {
        if (o instanceof NoneRepeat)
            return 1;
        else if (o instanceof WeeklyRepeat || o instanceof MonthlyRepeat)
            return -1;
        return 0;//appointment.getDate().toLocalTime().compareTo(o.getAppointment().getDate().toLocalTime());
    }
}
