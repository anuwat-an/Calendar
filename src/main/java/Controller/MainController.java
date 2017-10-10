/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import DataSource.AppointmentDataSource;
import Model.*;
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
    private DateFormat dateFormat = new SimpleDateFormat("u dd/MM/yyyy HH:mm", Locale.US);

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

        this.loadCalendar();

        this.datePicker.setValue(LocalDate.now());
        this.setAppointmentsDetails();

    }

    @FXML
    private void loadCalendar() {
        this.calendar = new Calendar();
        this.calendar.setAppointments(dataSource.loadData());

        this.lastID = dataSource.getLastID();
    }

    @FXML
    public void setAppointmentsDetails() {
        this.appointmentID.getItems().clear();
        this.appointmentDetails.clear();

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
            this.appointmentDetails.appendText(apt.toString() + "\n");

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
        String date = localDate.getDayOfWeek().getValue() + " " +
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
                Appointment appointment;
                if ("DAILY".equalsIgnoreCase(repeat))
                    appointment = new DailyAppointment(lastID, name, description, localDateTime);
                else if ("WEEKLY".equalsIgnoreCase(repeat))
                    appointment = new WeeklyAppointment(lastID, name, description, localDateTime);
                else if ("MONTHLY".equalsIgnoreCase(repeat))
                    appointment = new MonthlyAppointment(lastID, name, description, localDateTime);
                else
                    appointment = new NormalAppointment(lastID, name, description, localDateTime);
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
//        this.hour.setPromptText("Hr");
//        this.minute.setPromptText("Min");
//        this.repeatComboBox.setPromptText("Repeat Option");
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

                EditPageController controller = loader.getController();
                controller.setStage(stage);
                controller.setEditAppointment(calendar.getAppointment(appointmentID.getValue()));

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

                DeletePageController controller = loader.getController();
                controller.setStage(stage);
                controller.setMainController(this);
                controller.setDeleteID(appointmentID.getValue());

                stage.showAndWait();

                setAppointmentsDetails();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
