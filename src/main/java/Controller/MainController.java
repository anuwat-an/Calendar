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
import java.util.ArrayList;
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
    private ComboBox<String> repeatComboBox;
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

        this.repeatComboBox.getItems().add("NONE");
        this.repeatComboBox.getItems().add("DAILY");
        this.repeatComboBox.getItems().add("WEEKLY");
        this.repeatComboBox.getItems().add("MONTHLY");

        loadCalendar();

    }

    @FXML
    private void loadCalendar() {
        this.calendar = new Calendar();
//        ResultSet resultSet = DatabaseHandler.loadCalendar();

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
                     * add appointment from db to this.calendar
                     */
                    this.date = dateFormat.parse(date);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
                    Appointment appointment = new Appointment(id, name, description, localDateTime, repeat);
                    this.calendar.addAppointment(appointment);
                    this.lastID = id + 1;
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
    }

    @FXML
    public void setAppointmentsDetails() {
        this.appointmentID.getItems().clear();
        this.appointmentsDetails.clear();

        ArrayList<Appointment> selectedAppts = new ArrayList<>();
        for (Appointment appt : this.calendar.getAppointments()) {
            if (appt.getDate().toLocalDate().equals(this.datePicker.getValue()) || "DAILY".equalsIgnoreCase(appt.getRepeat())) {
                selectedAppts.add(appt);
            }
            else if ("WEEKLY".equalsIgnoreCase(appt.getRepeat()) &&
                    this.datePicker.getValue().getDayOfWeek().getValue() == appt.getDate().getDayOfWeek().getValue()) {
                selectedAppts.add(appt);
            }
            else if ("MONTHLY".equalsIgnoreCase(appt.getRepeat()) &&
                    this.datePicker.getValue().getDayOfMonth() == appt.getDate().getDayOfMonth()) {
                selectedAppts.add(appt);
            }
        }
        selectedAppts.sort(new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                if ("MONTHLY".equalsIgnoreCase(o1.getRepeat()) && "MONTHLY".equalsIgnoreCase(o2.getRepeat()))
                    return o1.getDate().compareTo(o2.getDate());
                else if ("MONTHLY".equalsIgnoreCase(o1.getRepeat()))
                    return 1;
                else if ("MONTHLY".equalsIgnoreCase(o2.getRepeat()))
                    return -1;
                else if ("WEEKLY".equalsIgnoreCase(o1.getRepeat()) && "WEEKLY".equalsIgnoreCase(o2.getRepeat()))
                    return o1.getDate().compareTo(o2.getDate());
                else if ("WEEKLY".equalsIgnoreCase(o1.getRepeat()))
                    return 1;
                else if ("WEEKLY".equalsIgnoreCase(o2.getRepeat()))
                    return -1;
                else if ("DAILY".equalsIgnoreCase(o1.getRepeat()) && "DAILY".equalsIgnoreCase(o2.getRepeat()))
                    return o1.getDate().compareTo(o2.getDate());
                else if ("DAILY".equalsIgnoreCase(o1.getRepeat()))
                    return 1;
                else if ("DAILY".equalsIgnoreCase(o2.getRepeat()))
                    return -1;
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        for (Appointment apt : selectedAppts) {
            int aptID = apt.getId();

            this.appointmentID.getItems().add(aptID);
            this.appointmentsDetails.appendText(apt.toString() + "\n");

        }

        int length = this.appointmentsDetails.getText().length();
        if (length > 0)
            this.appointmentsDetails.deleteText(length-1, length);
        else
            this.appointmentsDetails.appendText("There is no appointment on this day.");
    }

    @FXML
    public void addAppointment() {
        LocalDate localDate = this.datePicker.getValue();
        String name = this.apptName.getText();
        String description = this.apptDescription.getText();
        String hour = this.apptHour.getValue();
        String minute = this.apptMinute.getValue();
        String repeat = this.repeatComboBox.getValue();

        if (localDate != null && !"".equals(name) && hour != null && minute != null && repeat != null) {
            /**
             * date format string
             */
            String date = localDate.getDayOfWeek().getValue() + " " +
                    localDate.getDayOfMonth()+"/"+localDate.getMonthValue()+"/"+localDate.getYear()+" "+
                    hour+":"+minute;

            DatabaseHandler.addAppointment(date, name, description, repeat, lastID);

            try {
                /**
                 * create LocalDateTime to add to appointment
                 * add appointment from db to this.calendar
                 */
                this.date = dateFormat.parse(date);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
                Appointment appointment = new Appointment(lastID, name, description, localDateTime, repeat);
                this.calendar.addAppointment(appointment);

                setAppointmentsDetails();

                lastID += 1;
                clearFields();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clearFields() {
        this.apptName.clear();
        this.apptDescription.clear();
        this.apptHour.setValue(null);
        this.apptHour.setPromptText("Hr");
        this.apptMinute.setValue(null);
        this.apptMinute.setPromptText("Min");
        this.repeatComboBox.setValue(null);
        this.repeatComboBox.setPromptText("Repeat Option");
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
                controller.setStage(stage);
                controller.setMainController(this);
                controller.setDeleteID(this.appointmentID.getValue());

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
                controller.setStage(stage);
                controller.setMainController(this);
                controller.setEditID(this.appointmentID.getValue());

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
