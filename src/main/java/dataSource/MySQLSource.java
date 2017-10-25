/**
 * Anuwat Angkuldee 5810401066
 */

package dataSource;

import model.Appointment;

import java.util.ArrayList;

public class MySQLSource extends DataSource {
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
