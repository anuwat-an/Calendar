package client.controller;

import common.CalendarService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Anuwat Angkuldee 5810401066
 */

public class MainControllerGUI {

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
        this.datePicker.setValue(LocalDate.now());

    }

    public void setAppointmentsDetails() {
        this.appointmentID.getItems().clear();
        this.appointmentDetails.clear();

        this.appointmentID.getItems().addAll(service.getAppointmentIDs(datePicker.getValue()));
        this.appointmentDetails.setText(service.getAppointmentDetails(datePicker.getValue()));

        if (this.appointmentID.getItems().size() == 0)
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

        if (!"".equals(name) && hour != null && minute != null && repeat != null) {
            service.addAppointment(name, description, repeat, date);
            setAppointmentsDetails();
            clearFields();
        }
        else {
            createAlert("Unable to add appointment", "Please fill in all required field.");
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

                EditPageControllerGUI controller = loader.getController();
                controller.setStage(stage);
                controller.setService(service);
                controller.setEditAppointment(appointmentID.getValue());

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeleteAppointment.fxml"));

        try {
            if (appointmentID.getValue() != null) {
                stage.initOwner(deleteButton.getScene().getWindow());
                stage.setScene(new Scene((Parent) loader.load()));
                stage.setTitle("Delete Appointment");
                stage.setResizable(false);

                DeletePageControllerGUI controller = loader.getController();
                controller.setStage(stage);
                controller.setService(service);
                controller.setDeleteID(appointmentID.getValue());

                stage.showAndWait();

                setAppointmentsDetails();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void setService(CalendarService service) {
        this.service = service;
    }

}
