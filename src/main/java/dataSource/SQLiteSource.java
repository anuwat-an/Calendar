/**
 * Anuwat Angkuldee 5810401066
 */

package dataSource;

import model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SQLiteSource extends DataSource {

    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy HH:mm", Locale.US);

    @Override
    public ArrayList<Appointment> loadData() {
        ArrayList<Appointment> appointments = new ArrayList<>();

        try {
            /** setup */
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:Appointments.db";
            Connection connection = DriverManager.getConnection(dbURL);

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
                    RepeatType repeatType = repeatTypeMap.get(repeat);

                    /**
                     * create LocalDateTime to add to appointment
                     * add appointment from db to return appointments
                     */
                    this.date = dateFormat.parse(date);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());

                    Appointment appointment = new Appointment(id, name, description, localDateTime, repeatType);
                    appointments.add(appointment);
                }

                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:Appointments.db";
            Connection connection = DriverManager.getConnection(dbURL);

            /** query add appointments */
            if (connection != null) {
                String query = "select MAX(id) from Appointments";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                lastID = resultSet.getInt(1);

                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastID;
    }

    @Override
    public void addData(Appointment appointment) {
        try {
            /** setup */
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:Appointments.db";
            Connection connection = DriverManager.getConnection(dbURL);

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData(Appointment appointment) {
        try {
            /** setup */
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:Appointments.db";
            Connection connection = DriverManager.getConnection(dbURL);

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteData(int id) {
        try {
            /** setup */
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:Appointments.db";
            Connection connection = DriverManager.getConnection(dbURL);

            /** query delete appointments */
            if (connection != null) {
                String query = "delete from Appointments where id="+id;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
