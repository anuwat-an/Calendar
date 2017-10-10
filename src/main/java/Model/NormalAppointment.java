package Model;

import java.time.LocalDateTime;

public class NormalAppointment extends Appointment {

    public NormalAppointment(int id, String name, String description, LocalDateTime date) {
        super(id, name, description, date);
    }

    @Override
    public String toString() {
        return id + ": " + date.getDayOfWeek() + " " +
                date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear() + " " + super.toString();
    }

}
