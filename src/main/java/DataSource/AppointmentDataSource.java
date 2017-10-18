/**
 * Anuwat Angkuldee 5810401066
 */

package DataSource;

import Model.Appointment;

import java.util.ArrayList;

public interface AppointmentDataSource {

    public ArrayList<Appointment> loadData();
    public int getLastID();
    public void addData(Appointment appointment);
    public void updateData(Appointment appointment);
    public void deleteData(int id);

}