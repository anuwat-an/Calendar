/**
 * Anuwat Angkuldee 5810401066
 */

import Model.Appointment;
import Model.Calendar;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ControllerTest {

    Calendar calendar;
    ArrayList<Appointment> testAppointments;

    @Before
    public void setUp() {
        calendar = new Calendar();
        testAppointments = new ArrayList<>();

        calendar.addAppointment("30","8","2560",
                "19","17",
                "testing with Junit", "JUnit");

        testAppointments.add(new Appointment("30", "8","2560",
                                            "19","17",
                                            "testing with Junit","JUnit"));
    }

    @Test
    public void test30082560() {
        ArrayList<Appointment> appointments = calendar.findAppointments("30","8","2560");
        for (int i=0; i<appointments.size(); i++) {
            assertEquals(testAppointments.get(i).toString(), appointments.get(i).toString());
        }
    }

}