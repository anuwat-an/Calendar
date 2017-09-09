/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import Model.Appointment;
import Model.Calendar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MainController {

    /** Column 1 */
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField apptName;
    @FXML
    private TextArea apptDescription;
    @FXML
    private ComboBox<String> apptHour;
    @FXML
    private ComboBox<String> apptMinute;
    @FXML
    private Button addButton;
    @FXML
    private Button clrButton;

    /** Column 2 */
    @FXML
    private TextArea appointmentsDetails;
    @FXML
    private ComboBox<Integer> appointmentID;
    @FXML
    private Button editButton;
    @FXML
    private Button deleButton;
    @FXML
    private Button restButton;

    private Calendar calendar;

    private int lastID;

    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("u dd/MM/yyyy HH:mm", Locale.US);

    @FXML
    public void initialize() {

        for (int i=0; i<=23; i++) {
            String hour = "";
            if (i < 10)
                hour = "0";
            this.apptHour.getItems().add(hour + i);
        }
        for (int i=0; i<=59; i++) {
            String min = "";
            if (i < 10)
                min = "0";
            this.apptMinute.getItems().add(min + i);
        }

        loadCalendar();

    }

    @FXML
    private void loadCalendar() {
        this.calendar = new Calendar();
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

                    /**
                     * add appointments from db to this.calendar
                     */
                    this.date = dateFormat.parse(date);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
                    Appointment appointment = new Appointment(id, name, description, localDateTime);
                    this.calendar.addAppointment(appointment);
//                    this.lastID = id + 1;
                }
                connection.close();

            }

//            setAppointmentsDetails();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setAppointmentsDetails() {
        this.appointmentID.getItems().clear();
        this.appointmentsDetails.clear();
        this.calendar.getAppointments().sort(new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        int aptID = 0;
        for (Appointment apt : this.calendar.getAppointments()) {
            if (apt.getDate().equals(this.datePicker.getValue())) {
                aptID = apt.getId();

                this.appointmentID.getItems().add(aptID);
                this.appointmentsDetails.appendText(apt.toString() + "\n");
            }

        }
        if(aptID >= lastID)
            lastID = aptID + 1;

        int length = this.appointmentsDetails.getText().length();
        if (length > 0)
            this.appointmentsDetails.deleteText(length-1, length);
    }

    @FXML
    public void addAppointment() {
        LocalDate localDate = this.datePicker.getValue();
        String name = this.apptName.getText();
        String description = this.apptDescription.getText();
        String hour = this.apptHour.getValue();
        String minute = this.apptMinute.getValue();

        if (localDate != null && !"".equals(name) && hour != null && minute != null) {
            try {
                /** setup */
                Class.forName("org.sqlite.JDBC");
                String dbURL = "jdbc:sqlite:Appointments.db";
                Connection connection = DriverManager.getConnection(dbURL);

                /** query add appointments */
                if (connection != null) {
                    /**
                     * date format string
                     */
                    String date = localDate.getDayOfWeek().getValue() + " " +
                                    localDate.getDayOfMonth()+"/"+localDate.getMonthValue()+"/"+localDate.getYear()+" "+
                                    hour+":"+minute;

                    /**
                     * add appointment to db
                     */
                    String query = "insert into Appointments values ("+
                                    lastID+", '"+name+"' , '"+description+"' , '"+date+"')";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);

                    /**
                     * add appointment to this.calendar
                     */
                    this.date = dateFormat.parse(date);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
                    Appointment appointment = new Appointment(lastID, name, description, localDateTime);
                    this.calendar.addAppointment(appointment);

                    setAppointmentsDetails();

                    lastID += 1;
                    clearFields();

                    connection.close();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clearFields() {
        this.apptName.clear();
        this.apptDescription.clear();
        this.apptHour.setPromptText("Hr");
        this.apptHour.setValue(null);
        this.apptMinute.setPromptText("Min");
        this.apptMinute.setValue(null);
    }

    @FXML
    public void deleteAppointment() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmDelete.fxml"));

        try {
            if (this.appointmentID.getValue() != null) {
                stage.initOwner(deleButton.getScene().getWindow());
                stage.setScene(new Scene((Parent) loader.load()));
                stage.setTitle("Delete Appointment");

                ConfirmDeleteCtrl controller = loader.getController();
                controller.setDeleteID(this.appointmentID.getValue());
                controller.setStage(stage);
                controller.setMainController(this);

                stage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editAppointment() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditAppointment.fxml"));

        try {
            if (this.appointmentID.getValue() != null) {
                stage.initOwner(editButton.getScene().getWindow());
                stage.setScene(new Scene((Parent) loader.load()));
                stage.setTitle("Edit Appointment");

                EditApptCtrl controller = loader.getController();
                controller.setEditID(this.appointmentID.getValue());
                controller.setStage(stage);
                controller.setMainController(this);

                stage.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Calendar getCalendar() {
        return this.calendar;
    }
}
