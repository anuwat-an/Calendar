package client.controller;

import common.CalendarService;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;

/**
 * Anuwat Angkuldee 5810401066
 */

public class EditPageControllerGUI {

    @FXML
    private Label appointmentIDLabel;
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
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Stage stage;

    private int editID;
    private CalendarService service;

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
    }

    @FXML
    public void confirmEdit() {
        LocalDate localDate = this.datePicker.getValue();
        String name = this.name.getText();
        String description = this.description.getText();
        String hour = this.hour.getValue();
        String minute = this.minute.getValue();
        String repeat = this.repeatComboBox.getValue();
        String date = localDate.getDayOfWeek() + " " +
                localDate.getDayOfMonth()+"/"+localDate.getMonthValue()+"/"+localDate.getYear()+" "+
                hour+":"+minute;

        if (!"".equals(name) && hour != null && minute != null && repeat != null) {
            this.service.updateAppointment(editID, name, description, repeat, date);
            this.stage.close();
        }
        else {
            createAlert("Unable to edit appointment", "Please fill in all required field.");
        }
    }

    @FXML
    public void cancelEdit() {
        this.stage.close();
    }

    public void setEditAppointment(int id) {
        this.editID = id;

        this.appointmentIDLabel.setText(appointmentIDLabel.getText()+id);
        this.datePicker.setValue(service.getAppointmentDate(id).toLocalDate());
        this.name.setText(service.getAppointmentName(id));
        this.description.setText(service.getAppointmentDescription(id));
        NumberFormat numberFormat = new DecimalFormat("00");
        String hour = numberFormat.format(service.getAppointmentDate(id).getHour());
        String minute = numberFormat.format(service.getAppointmentDate(id).getMinute());
        this.hour.setValue(hour);
        this.minute.setValue(minute);
        this.repeatComboBox.setValue(service.getAppointmentRepeat(id));
    }

    private void createAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setService(CalendarService service) {
        this.service = service;
    }

}
