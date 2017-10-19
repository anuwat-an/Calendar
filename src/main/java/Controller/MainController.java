/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import DataSource.AppointmentDataSource;
import DataSource.SQLiteDataSource;
import Model.*;
import Model.Calendar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class MainController {

    /** Column 1 */
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private ComboBox<String> hour;
    @FXML
    private ComboBox<String> minute;
    @FXML
    private ComboBox<String> repeatComboBox;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;

    /** Column 2 */
    @FXML
    private TextArea appointmentDetails;
    @FXML
    private ComboBox<Integer> appointmentID;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button resetButton;

    private Calendar calendar;
    private AppointmentDataSource dataSource;

    private int lastID;

    private java.util.Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy HH:mm", Locale.US);

    @FXML
    public void initialize() {

        for (int i=0; i<=23; i++) {
            String hour = "";
            if (i < 10)
                hour = "0";
            this.hour.getItems().add(hour + i);
        }
        for (int i=0; i<=59; i++) {
            String min = "";
            if (i < 10)
                min = "0";
            this.minute.getItems().add(min + i);
        }

        this.repeatComboBox.getItems().add("NONE");
        this.repeatComboBox.getItems().add("DAILY");
        this.repeatComboBox.getItems().add("WEEKLY");
        this.repeatComboBox.getItems().add("MONTHLY");
        this.datePicker.setValue(LocalDate.now());

        this.dataSource = new SQLiteDataSource();

        this.loadCalendar();
        this.setAppointmentsDetails();

    }

    @FXML
    private void loadCalendar() {
        this.calendar = new Calendar();
        this.calendar.setAppointments(dataSource.loadData());

        this.lastID = dataSource.getLastID()+1;
    }

    @FXML
    public void setAppointmentsDetails() {
        this.appointmentID.getItems().clear();
        this.appointmentDetails.clear();

        PriorityQueue<Appointment> selectedAppts = new PriorityQueue<>();
        for (Appointment appointment : calendar.getAppointments()) {
            if (appointment.getRepeatType().compareDate(datePicker.getValue()))
                selectedAppts.add(appointment);
        }

        for (Appointment appointment : selectedAppts) {
            int appointmentID = appointment.getId();

            this.appointmentID.getItems().add(appointmentID);
            this.appointmentDetails.appendText(appointment.toString() + "\n");

        }

        int length = this.appointmentDetails.getText().length();
        if (length > 0)
            this.appointmentDetails.deleteText(length-1, length);
        else
            this.appointmentDetails.appendText("There is no appointment on this day.");
    }

    @FXML
    public void addAppointment() {
        LocalDate localDate = this.datePicker.getValue();
        String name = this.name.getText();
        String description = this.description.getText();
        String hour = this.hour.getValue();
        String minute = this.minute.getValue();
        String repeat = this.repeatComboBox.getValue();
        String date = localDate.getDayOfWeek() + " " +
                localDate.getDayOfMonth()+"/"+localDate.getMonthValue()+"/"+localDate.getYear()+" "+
                hour+":"+minute;

        if (localDate != null && !"".equals(name) && hour != null && minute != null && repeat != null) {

            try {
                /**
                 * create LocalDateTime to add to appointment
                 * add appointment from db to this.calendar
                 */
                this.date = dateFormat.parse(date);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());

                Appointment appointment = new Appointment(lastID, name, description, localDateTime);
                appointment.setRepeatType(repeat);
                this.calendar.addAppointment(appointment);

                this.dataSource.addData(appointment);

                this.lastID += 1;
                setAppointmentsDetails();
                clearFields();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void clearFields() {
        this.name.clear();
        this.description.clear();
        this.hour.setValue(null);
        this.minute.setValue(null);
        this.repeatComboBox.setValue(null);
    }

    @FXML
    public void editAppointment() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditAppointment.fxml"));

        try {
            if (appointmentID.getValue() != null) {
                stage.initOwner(editButton.getScene().getWindow());
                stage.setScene(new Scene((Parent) loader.load()));
                stage.setTitle("Edit Appointment");
                stage.setResizable(false);

                EditPageController controller = loader.getController();
                controller.setStage(stage);
                controller.setEditAppointment(calendar.getAppointment(appointmentID.getValue()));
                controller.setDataSource(dataSource);

                stage.showAndWait();

                setAppointmentsDetails();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteAppointment() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmDelete.fxml"));

        try {
            if (appointmentID.getValue() != null) {
                stage.initOwner(deleteButton.getScene().getWindow());
                stage.setScene(new Scene((Parent) loader.load()));
                stage.setTitle("Delete Appointment");
                stage.setResizable(false);

                DeletePageController controller = loader.getController();
                controller.setStage(stage);
                controller.setDeleteID(appointmentID.getValue());
                controller.setCalendar(calendar);
                controller.setDataSource(dataSource);

                stage.showAndWait();

                setAppointmentsDetails();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(AppointmentDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
