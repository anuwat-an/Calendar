/**
 * Anuwat Angkuldee 5810401066
 */

package controller;

import dataSource.DataSource;
import model.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private Calendar calendar;
    private DataSource dataSource;

    @FXML
    public void confirmDelete() {
        this.calendar.deleteAppointment(deleteID);
        this.dataSource.deleteData(deleteID);
        this.stage.close();
    }

    @FXML
    public void cancelDelete() {
        this.stage.close();
    }

    public void setDeleteID(int id) {
        this.deleteID = id;
        this.textLabel.setText("Are you sure to delete appointment ID: "+deleteID);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
