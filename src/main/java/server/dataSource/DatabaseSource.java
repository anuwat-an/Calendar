package server.dataSource;

import server.library.DataLibrary;
import server.model.Appointment;
import server.model.RepeatType;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

/**
 * Anuwat Angkuldee 5810401066
 */

public abstract class DatabaseSource implements DataSource {

    public abstract Connection connect();

    @Override
    public Vector<Appointment> loadData() {
        Vector<Appointment> appointments = new Vector<>();

        try {
            /** setup */
            Connection connection = connect();

            /** query get appointments */
            if (connection != null) {
                String query = "select * from Appointments";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String date = resultSet.getString("date");
                    String repeat = resultSet.getString("repeat");
                    RepeatType repeatType = DataLibrary.repeatTypeMap.get(repeat);

                    /**
                     * create LocalDateTime to add to appointment
                     * add appointment from db to return appointments
                     */
                    Date d = DataLibrary.dateFormat.parse(date);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());

                    Appointment appointment = new Appointment(id, name, description, localDateTime, repeatType);
                    appointments.add(appointment);
                }

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public int getLastID() {
        int lastID = 0;

        try {
            /** setup */
            Connection connection = connect();

            /** query add appointments */
            if (connection != null) {
                String query = "select MAX(id) from Appointments";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                lastID = resultSet.getInt(1);

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastID;
    }

    @Override
    public void addData(Appointment appointment) {
        try {
            /** setup */
            Connection connection = connect();

            /** query add appointments */
            if (connection != null) {
                /**
                 * add appointment to db
                 */
                String query = "insert into Appointments values ("+
                        appointment.getId()+", '"+
                        appointment.getName()+"' , '"+
                        appointment.getDescription()+"' , '"+
                        appointment.getDateTimeToString()+"' , '"+
                        appointment.getRepeatType().getRepeat()+"')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData(Appointment appointment) {
        try {
            /** setup */
            Connection connection = connect();

            /** query edit appointments */
            if (connection != null) {
                /**
                 * update appointment on db
                 */
                String query = "update Appointments " +
                        "set name='" + appointment.getName() +
                        "', description='" + appointment.getDescription() +
                        "', date='" + appointment.getDateTimeToString() +
                        "', repeat='" + appointment.getRepeatType().getRepeat() +
                        "' where id=" + appointment.getId();
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(int id) {
        try {
            /** setup */
            Connection connection = connect();

            /** query delete appointments */
            if (connection != null) {
                String query = "delete from Appointments where id="+id;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
