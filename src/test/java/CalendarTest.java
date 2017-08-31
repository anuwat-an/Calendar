/**
 * Anuwat Angkuldee 5810401066
 */

import Controller.Controller;
import Model.Appointment;
import Model.Calendar;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CalendarTest {

    private Calendar calendar;

    @Before
    public void setUp() throws ParseException {
        calendar = new Calendar();

        calendar.addAppointment("30", "8", "2560",
                "21", "49",
                "None", "testCalendar1");

        calendar.addAppointment("30", "8", "2560",
                "21", "30",
                "None", "testCalendar2");

        calendar.addAppointment("28", "8", "2560",
                "21", "49",
                "None", "testCalendar3");
    }

    @Test
    public void testFindingString() {
//        this.startTime.get("Date")+"/"+this.startTime.get("Month")+"/"+this.startTime.get("Year")+
//                " "+this.startTime.get("Hour")+":"+this.startTime.get("Minute")+"\n"+
//                name+"\n"+
//                description+"\n\n"
        assertEquals(calendar.findAppointments("28", "8", "2560").get(0).toString(),
                "28/8/2560 21:49\ntestCalendar3\nNone\n\n");
    }

    @Test
    public void testFindingAmount() {
        assertEquals(calendar.findAppointments("30", "8", "2560").size(), 2);
    }

}