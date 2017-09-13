/**
 * Anuwat Angkuldee 5810401066
 */

import Model.Calendar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

public class CalendarTest {

    private Calendar calendar;

    @BeforeAll
    public void setUp() {
        calendar = new Calendar();

        LocalDateTime lc = LocalDateTime.of(2017, 9, 13, 20, 48);
        calendar.addAppointment(20, "JUnit", "test #1", lc, "NONE");
        calendar.addAppointment(21, "JUnit", "test #2", lc, "DAILY");
        calendar.addAppointment(22, "JUnit", "test #3", lc, "WEEKLY");
        calendar.addAppointment(23, "JUnit", "test #4", lc, "MONTHLY");
    }

    @Test
    public void testGetAppointments() {
        assertEquals(4, calendar.getAppointments().size());
        assertEquals("20: WEDNESDAY 13/09/2017 20:48\nJUnit\ntest #1\n", calendar.getAppointment(20));
        assertEquals("21: DAILY 20:48\nJUnit\ntest #2\n", calendar.getAppointment(21));
        assertEquals("22: WEDNESDAY 20:48\nJUnit\ntest #3\n", calendar.getAppointment(22));
        assertEquals("23: MONTHLY 13 20:48\nJUnit\ntest #4\n", calendar.getAppointment(23));
    }

}