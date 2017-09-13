/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import java.sql.*;

public class DatabaseHandler {

//    public static ResultSet loadCalendar() {
//        ResultSet resultSet = null;
//        try {
//            /** setup */
//            Class.forName("org.sqlite.JDBC");
//            String dbURL = "jdbc:sqlite:Appointments.db";
//            Connection connection = DriverManager.getConnection(dbURL);
//
//            /** query get appointments */
//            if (connection != null) {
//                String query = "select * from Appointments";
//                Statement statement = connection.createStatement();
//                resultSet = statement.executeQuery(query);
//
//                connection.close();
//
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return resultSet;
//    }

    public static void addAppointment(String date, String name, String description, String repeat, int lastID) {
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
                        lastID+", '"+name+"' , '"+description+"' , '"+date+"' , '"+repeat+"')";
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

    public static void deleteAppointment(int deleteID) {
        try {
            /** setup */
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:Appointments.db";
            Connection connection = DriverManager.getConnection(dbURL);

            /** query delete appointments */
            if (connection != null) {
                String query = "delete from Appointments where id="+deleteID;
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

    public static  void editAppointment(String date, String name, String description, String repeat, int editID) {
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
                        "set name='" + name +
                        "', description='" + description +
                        "', date='" + date +
                        "', repeat='" + repeat +
                        "' where id=" + editID;
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
