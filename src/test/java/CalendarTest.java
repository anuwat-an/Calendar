import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.controller.CalendarServiceImp;
import server.dataSource.TestSource;
import server.model.Appointment;
import server.model.Calendar;
import server.model.NoneRepeat;
import server.model.WeeklyRepeat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Anuwat Angkuldee 5810401066
 */

public class CalendarTest {

    private Calendar calendar;
    private CalendarServiceImp service;

    @BeforeEach
    public void setUp() {
        calendar = new Calendar();
        service = new CalendarServiceImp(new TestSource());
    }

    @Test
    public void initTest() {
        assertEquals(0, calendar.getAppointments().size());
        assertEquals(4, service.getAppointmentIDs(LocalDate.now()).size());
    }

    @Test
    public void updateTest() {
        calendar.addAppointment(new Appointment(1, "test1", "unittest1", LocalDateTime.now(), new NoneRepeat()));
        calendar.addAppointment(new Appointment(2, "test2", "unittest2", LocalDateTime.now(), new WeeklyRepeat()));

        service.addAppointment("test5", "test datasource", "DAILY", "THURSDAY 2/11/2017 18:27");

        assertEquals(2, calendar.getAppointments().size());
        assertEquals(5, service.getAppointmentIDs(LocalDate.now()).size());

        calendar.deleteAppointment(1);
        service.deleteAppointment(4);

        assertEquals(1, calendar.getAppointments().size());
        assertEquals(4, service.getAppointmentIDs(LocalDate.now()).size());
    }

}