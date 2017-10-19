/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.time.LocalDate;

public class MonthlyRepeat extends RepeatType {

    public MonthlyRepeat(Appointment appointment) {
        super(appointment);
    }

    @Override
    public String getRepeat() {
        return "MONTHLY";
    }

    @Override
    public String toString() {
        return appointment.getId() + ": MONTHLY " + appointment.getDate().getDayOfMonth() + " " + super.toString();
    }

    @Override
    public boolean compareDate(LocalDate date) {
        return appointment.getDate().getDayOfMonth() == date.getDayOfMonth();
    }

    /** fix bug & OCP */
    @Override
    public int compareTo(RepeatType o) {
        if (o instanceof NoneRepeat || o instanceof DailyRepeat || o instanceof WeeklyRepeat)
            return 1;
        return 0;//appointment.getDate().toLocalTime().compareTo(o.getAppointment().getDate().toLocalTime());
    }
}
