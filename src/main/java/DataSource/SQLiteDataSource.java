package DataSource;

import Model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SQLiteDataSource implements AppointmentDataSource {

    private java.util.Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("u dd/MM/yyyy HH:mm", Locale.US);

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

                    /**
                     * create LocalDateTime to add to appointment
                     * add appointment from db to return appointments
                     */
                    this.date = dateFormat.parse(date);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
                    Appointment appointment;
                    if ("DAILY".equalsIgnoreCase(repeat))
                        appointment = new DailyAppointment(id, name, description, localDateTime);
                    else if ("WEEKLY".equalsIgnoreCase(repeat))
                        appointment = new WeeklyAppointment(id, name, description, localDateTime);
                    else if ("MONTHLY".equalsIgnoreCase(repeat))
                        appointment = new MonthlyAppointment(id, name, description, localDateTime);
                    else
                        appointment = new NormalAppointment(id, name, description, localDateTime);
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

                lastID = resultSet.getInt(0);

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
                String repeat = "NONE";
                if (appointment instanceof DailyAppointment)
                    repeat = "DAILY";
                else if (appointment instanceof WeeklyAppointment)
                    repeat = "WEEKLY";
                else if (appointment instanceof MonthlyAppointment)
                    repeat = "MONTHLY";

                String query = "insert into Appointments values ("+
                        appointment.getId()+", '"+
                        appointment.getName()+"' , '"+
                        appointment.getDescription()+"' , '"+
                        appointment.getDateString()+"' , '"+repeat+"')";
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
                String repeat = "NONE";
                if (appointment instanceof DailyAppointment)
                    repeat = "DAILY";
                else if (appointment instanceof WeeklyAppointment)
                    repeat = "WEEKLY";
                else if (appointment instanceof MonthlyAppointment)
                    repeat = "MONTHLY";

                String query = "update Appointments " +
                        "set name='" + appointment.getName() +
                        "', description='" + appointment.getDescription() +
                        "', date='" + appointment.getDateString() +
                        "', repeat='" + repeat +
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
