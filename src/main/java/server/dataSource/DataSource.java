package server.dataSource;

import server.model.Appointment;

import java.util.Vector;

/**
 * Anuwat Angkuldee 5810401066
 */

public interface DataSource {

    Vector<Appointment> loadData();
    int getLastID();
    void addData(Appointment appointment);
    void updateData(Appointment appointment);
    void deleteData(int id);

}
