package Model;

import java.time.LocalDateTime;

public class DailyAppointment extends Appointment {

    public DailyAppointment(int id, String name, String description, LocalDateTime date) {
        super(id, name, description, date);
    }

    @Override
    public String toString() {
        return id + ": DAILY " + super.toString();
    }

}
