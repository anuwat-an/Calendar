package Model;

import java.time.LocalDateTime;

public class WeeklyAppointment extends Appointment {

    public WeeklyAppointment(int id, String name, String description, LocalDateTime date) {
        super(id, name, description, date);
    }

    @Override
    public String toString() {
        return id + ": " + date.getDayOfWeek() + " " + super.toString();
    }

}
