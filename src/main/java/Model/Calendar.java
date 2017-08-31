/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.text.ParseException;
import java.util.ArrayList;

public class Calendar {

    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    public void addAppointment(String sDate, String sMonth, String sYear,
                               String sHour, String sMinute,
                               String description, String name) throws ParseException {
        appointments.add(new Appointment(sDate, sMonth, sYear,
                                         sHour, sMinute,
                                         description, name));

    }

    public ArrayList<Appointment> findAppointments(String date, String month, String year) {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        for (Appointment appt : this.appointments) {

            if (date.equals(appt.getStartTime().get("Date")) &&
                month.equals(appt.getStartTime().get("Month")) &&
                year.equals(appt.getStartTime().get("Year"))) {
                appointments.add(appt);
            }

        }
        return appointments;
    }
}
