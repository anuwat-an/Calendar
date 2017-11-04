package server.dataSource;

import server.model.Appointment;

import java.io.*;
import java.util.Vector;

/**
 * Anuwat Angkuldee 5810401066
 */

public class FileSystemSource implements DataSource {

    private ObjectInputStream createInputStream() {
        try {
            return new ObjectInputStream(new FileInputStream("Appointment"));
        } catch (IOException e) {
            return null;
        }
    }

    private ObjectOutputStream createOutputStream() {
        try {
            return new ObjectOutputStream(new FileOutputStream("Appointment", false));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Vector<Appointment> loadData() {
        Vector<Appointment> appointments = new Vector<>();
        ObjectInputStream in = createInputStream();

        if (in != null) {
            try {
                while (true) {
                    try {
                        Appointment appointment = (Appointment) in.readObject();
                        appointments.add(appointment);
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return appointments;
    }

    @Override
    public int getLastID() {
        Vector<Appointment> appointments = loadData();
        int lastID = 0;
        for (Appointment appointment : appointments) {
            lastID = Math.max(lastID, appointment.getId());
        }
        return lastID;
    }

    @Override
    public void addData(Appointment appointment) {
        Vector<Appointment> appointments = loadData();
        ObjectOutputStream out = createOutputStream();

        if (out != null) {
            try {
                appointments.add(appointment);
                for (Appointment appt : appointments) {
                    out.writeObject(appt);
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateData(Appointment appointment) {
        Vector<Appointment> appointments = loadData();
        ObjectOutputStream out = createOutputStream();

        if (out != null) {
            try {
                for (Appointment appt : appointments) {
                    if (appointment.getId() == appt.getId())
                        out.writeObject(appointment);
                    else
                        out.writeObject(appt);
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteData(int id) {
        Vector<Appointment> appointments = loadData();
        ObjectOutputStream out = createOutputStream();

        if (out != null) {
            try {
                for (Appointment appt : appointments) {
                    if (appt.getId() != id)
                        out.writeObject(appt);
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
