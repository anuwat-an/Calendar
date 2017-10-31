package common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

/**
 * Anuwat Angkuldee 5810401066
 */

public interface CalendarService {

    Vector<Integer> getAppointmentIDs(LocalDate date);
    String getAppointmentDetails(LocalDate date);

    LocalDateTime getAppointmentDate(int id);
    String getAppointmentName(int id);
    String getAppointmentDescription(int id);
    String getAppointmentRepeat(int id);

    void addAppointment(String name, String description, String repeat, String date);
    void updateAppointment(int id, String name, String description, String repeat, String date);
    void deleteAppointment(int id);

}
