/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import Model.Appointment;
import Model.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class EditApptCtrl {

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
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Stage stage;

    private int editID;
    private MainController mainController;
    private Appointment appointment;

    private Date date = new Date();
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
    }

    @FXML
    public void confirmEdit() {
        LocalDate localDate = this.datePicker.getValue();
        String name = this.name.getText();
        String description = this.description.getText();
        String hour = this.hour.getValue();
        String minute = this.minute.getValue();
        String repeat = this.repeatComboBox.getValue();

        if (localDate != null && !"".equals(name) && hour != null && minute != null && repeat != null) {
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
                     * update appointment on db
                     */
                    String query = "update Appointments " +
                            "set name='" + name +
                            "', description='" + description +
                            "', date='" + date +
                            "', repeat='" + repeat +
                            "' where id=" + this.editID;
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);

                    /**
                     * edit appointment on mc.calendar
                     */
                    this.appointment.setName(name);
                    this.appointment.setDescription(description);
                    this.date = dateFormat.parse(date);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
                    this.appointment.setDate(localDateTime);
                    this.appointment.setRepeat(repeat);

                    this.mainController.setAppointmentsDetails();

                    connection.close();
                }

                this.stage.close();
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
    public void cancelEdit() {
        this.stage.close();
    }

    public void setEditID(int id) {
        this.appointment = mainController.getCalendar().getAppointment(id);

        this.editID = id;
        this.appointmentIDLabel.setText(this.appointmentIDLabel.getText()+id);
        this.datePicker.setValue(this.appointment.getDate().toLocalDate());
        this.name.setText(this.appointment.getName());
        this.description.setText(this.appointment.getDescription());
        this.hour.setValue(this.appointment.getDate().getHour()+"");
        this.minute.setValue(this.appointment.getDate().getMinute()+"");
        this.repeatComboBox.setValue(this.appointment.getRepeat());
//        int hour = date.getHours();
//        int minute = date.getMinutes();
//        if (hour < 10)
//            this.hour.setValue("0"+hour);
//        else
//            this.hour.setValue(""+hour);
//        if (minute < 10)
//            this.minute.setValue("0"+minute);
//        else
//            this.minute.setValue(""+minute);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainController(MainController ctrlr) {
        this.mainController = ctrlr;
    }

}
