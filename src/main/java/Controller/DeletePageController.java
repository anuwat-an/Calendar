/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import DataSource.AppointmentDataSource;
import Model.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeletePageController {

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
    private AppointmentDataSource dataSource;

    @FXML
    public void confirmDel() {
        this.calendar.deleteAppointment(deleteID);

        this.dataSource.deleteData(deleteID);

        this.stage.close();
    }

    @FXML
    public void cancelDel() {
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

    public void setDataSource(AppointmentDataSource dataSource) {
        this.dataSource = dataSource;
    }

}
