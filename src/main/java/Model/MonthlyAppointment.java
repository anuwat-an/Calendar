package Model;

import java.time.LocalDateTime;

public class MonthlyAppointment extends Appointment {

    public MonthlyAppointment(int id, String name, String description, LocalDateTime date) {
        super(id, name, description, date);
    }

    @Override
    public String toString() {
        return id + ": MONTHLY(" + date.getDayOfMonth() + ") " + super.toString();
    }

}
