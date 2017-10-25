/**
 * Anuwat Angkuldee 5810401066
 */

package dataSource;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestSource extends DataSource {

    private ArrayList<Appointment> appointments;

    public TestSource() {
        appointments = new ArrayList<>();
        appointments.add(new Appointment(1, "test1", "test datasource", LocalDateTime.now(), new NoneRepeat()));
        appointments.add(new Appointment(2, "test2", "test datasource", LocalDateTime.now(), new DailyRepeat()));
        appointments.add(new Appointment(3, "test3", "test datasource", LocalDateTime.now(), new WeeklyRepeat()));
        appointments.add(new Appointment(4, "test4", "test datasource", LocalDateTime.now(), new MonthlyRepeat()));
    }

    @Override
    public ArrayList<Appointment> loadData() {
        return appointments;
    }

    @Override
    public int getLastID() {
        return appointments.size();
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
                appointments.add(i, appointment);
                break;
            }
        }
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
