package DataSource;

import Model.Appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestDataSource implements AppointmentDataSource {

    ArrayList<Appointment> appointments = new ArrayList<>();

    @Override
    public ArrayList<Appointment> loadData() {
        appointments.add(new Appointment(0, "test0", "test datasource", LocalDateTime.now()));
        appointments.add(new Appointment(1, "test1", "test datasource", LocalDateTime.now()));
        appointments.add(new Appointment(2, "test2", "test datasource", LocalDateTime.now()));
        appointments.add(new Appointment(3, "test3", "test datasource", LocalDateTime.now()));
        return appointments;
    }

    @Override
    public int getLastID() {
        return 3;
    }

    @Override
    public void addData(Appointment appointment) {
        appointments.add(appointment);
    }

    @Override
    public void updateData(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId() == appointment.getId()) {
                appointments.remove(i);
                break;
            }
        }
        appointments.add(appointment);
    }

    @Override
    public void deleteData(int id) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId() == id) {
                appointments.remove(i);
                break;
            }
        }
    }
}
