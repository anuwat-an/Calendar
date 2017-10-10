package DataSource;

import Model.Appointment;

import java.util.ArrayList;

public class FileSystemDataSource implements AppointmentDataSource {
    @Override
    public ArrayList<Appointment> loadData() {
        return null;
    }

    @Override
    public int getLastID() {
        return 0;
    }

    @Override
    public void addData(Appointment appointment) {

    }

    @Override
    public void updateData(Appointment appointment) {

    }

    @Override
    public void deleteData(int id) {

    }
}
