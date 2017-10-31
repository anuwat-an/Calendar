package server.dataSource;

import server.model.Appointment;

import java.util.Vector;

/**
 * Anuwat Angkuldee 5810401066
 */

public class FileSystemSource implements DataSource {
    @Override
    public Vector<Appointment> loadData() {
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
