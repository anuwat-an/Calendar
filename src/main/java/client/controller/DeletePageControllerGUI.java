package client.controller;

import common.CalendarService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Anuwat Angkuldee 5810401066
 */

public class DeletePageControllerGUI {

    @FXML
    private Label textLabel;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Stage stage;

    private int deleteID;
    private CalendarService service;

    @FXML
    public void confirmDelete() {
        this.service.deleteAppointment(deleteID);
        this.stage.close();
    }

    @FXML
    public void cancelDelete() {
        this.stage.close();
    }

    public void setDeleteAppointment(int id) {
        this.deleteID = id;
        this.textLabel.setText("Are you sure to delete appointment ID: "+deleteID);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setService(CalendarService service) {
        this.service = service;
    }

}
