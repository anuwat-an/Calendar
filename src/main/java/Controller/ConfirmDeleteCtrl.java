/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfirmDeleteCtrl {

    @FXML
    private Label textLabel;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Stage stage;

    private int deleteID;
    private MainController mainController;

//    public ConfirmDeleteCtrl(int deleteID) {
//        this.deleteID = deleteID;
//    }

//    @FXML
//    public void initialize() {
//
//    }

    @FXML
    public void confirmDel() {
        DatabaseHandler.deleteAppointment(deleteID);

        /**
         * #EDIT add and use calendar's method to remove
         */
        this.mainController.getCalendar().deleteAppointment(deleteID);
        this.mainController.setAppointmentsDetails();

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

    public void setMainController(MainController ctrlr) {
        this.mainController = ctrlr;
    }

}
