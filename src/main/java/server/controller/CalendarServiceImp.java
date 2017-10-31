package server.controller;

import common.CalendarService;
import server.dataSource.DataSource;
import server.library.DataLibrary;
import server.model.Appointment;
import server.model.Calendar;
import server.model.RepeatType;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Vector;
import java.util.Comparator;
import java.util.Date;

/**
 * Anuwat Angkuldee 5810401066
 */

public class CalendarServiceImp implements CalendarService {

    private int lastID;
    private Calendar calendar;
    private DataSource dataSource;

    public CalendarServiceImp(DataSource dataSource) {
        this.dataSource = dataSource;

        this.calendar = new Calendar();
        this.calendar.setAppointments(dataSource.loadData());

        this.lastID = dataSource.getLastID()+1;
    }

    @Override
    public Vector<Integer> getAppointmentIDs(LocalDate date) {
        Vector<Appointment> selectedAppts = getAppointments(date);

        Vector<Integer> ids = new Vector<>();
        for (Appointment appointment : selectedAppts) {
            ids.add(appointment.getId());
        }

        return ids;
    }

    @Override
    public String getAppointmentDetails(LocalDate date) {
        Vector<Appointment> selectedAppts = getAppointments(date);

        String details = "";
        for (Appointment appointment : selectedAppts) {
            details += appointment.toString() + "\n";
        }

        return details;
    }

    private Vector<Appointment> getAppointments(LocalDate date) {
        Vector<Appointment> selectedAppts = new Vector<>();
        for (Appointment appointment : calendar.getAppointments()) {
            if (appointment.compareDate(date))
                selectedAppts.add(appointment);
        }

        selectedAppts.sort(new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getDate().toLocalTime().compareTo(o2.getDate().toLocalTime());
            }
        });

        return selectedAppts;
    }

    @Override
    public LocalDateTime getAppointmentDate(int id) {
        return this.calendar.getAppointment(id).getDate();
    }

    @Override
    public String getAppointmentName(int id) {
        return this.calendar.getAppointment(id).getName();
    }

    @Override
    public String getAppointmentDescription(int id) {
        return this.calendar.getAppointment(id).getDescription();
    }

    @Override
    public String getAppointmentRepeat(int id) {
        return this.calendar.getAppointment(id).getRepeatType().getRepeat();
    }

    @Override
    public void addAppointment(String name, String description, String repeat, String date) {

        try {
            /**
             * create LocalDateTime to add to appointment
             * add appointment from db to this.calendar
             */
            Date d = DataLibrary.dateFormat.parse(date);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());

            RepeatType repeatType = DataLibrary.repeatTypeMap.get(repeat);
            Appointment appointment = new Appointment(lastID, name, description, localDateTime, repeatType);
            this.calendar.addAppointment(appointment);

            this.dataSource.addData(appointment);

            this.lastID += 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAppointment(int id, String name, String description, String repeat, String date) {
        try {
            RepeatType repeatType = DataLibrary.repeatTypeMap.get(repeat);

            /**
             * edit appointment
             */
            Appointment appointment = calendar.getAppointment(id);
            appointment.setName(name);
            appointment.setDescription(description);
            Date d = DataLibrary.dateFormat.parse(date);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
            appointment.setDate(localDateTime);
            appointment.setRepeatType(repeatType);

            this.dataSource.updateData(appointment);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAppointment(int id) {
        this.calendar.deleteAppointment(id);
        this.dataSource.deleteData(id);
    }
}
