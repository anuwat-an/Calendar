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
import java.time.ZoneId;
import java.util.Date;

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
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Stage stage;

    private int editID;
    private MainController mainController;
    private Appointment appointment;

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

        /** set init name, desc, time, and maybe day */
    }

    @FXML
    public void confirmEdit() {
        LocalDate localDate = this.datePicker.getValue();
        String name = this.name.getText();
        String description = this.description.getText();
        String hour = this.hour.getValue();
        String minute = this.minute.getValue();

        if (localDate != null && !"".equals(name) && hour != null && minute != null) {
            try {
                /** setup */
                Class.forName("org.sqlite.JDBC");
                String dbURL = "jdbc:sqlite:Appointments.db";
                Connection connection = DriverManager.getConnection(dbURL);

                /** query add appointments */
                if (connection != null) {
                    String dayOfMonth = localDate.getDayOfMonth()+"";
                    String monthValue = localDate.getMonthValue()+"";
                    String year = localDate.getYear()+"";
                    if (Integer.parseInt(dayOfMonth) < 10)
                        dayOfMonth = "0"+dayOfMonth;
                    if (Integer.parseInt(monthValue) < 10)
                        monthValue = "0"+monthValue;
                    String dateAndTime = dayOfMonth+"/"+monthValue+"/"+year+" "+hour+":"+minute;

                    String query = "update Appointments " +
                            "set name='" + name +
                            "', description='" + description +
                            "', date='" + dateAndTime +
                            "' where id=" + this.editID;
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);

                    /**
                     * #EDIT add and use calendar's method to edit
                     */
                    this.appointment.setName(name);
                    this.appointment.setDescription(description);
                    this.appointment.setDate(dateAndTime);

                    connection.close();
                }
                this.mainController.setAppointmentsDetails();

                this.stage.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void cancelEdit() {
        stage.close();
    }

    public void setEditID(int id) {
        this.appointment = mainController.getCalendar().getAppointment(id);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        try {
            date = dateFormat.parse(appointment.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        this.editID = id;
        this.appointmentIDLabel.setText(this.appointmentIDLabel.getText()+id);
        this.datePicker.setValue(localDate.plusYears(543).minusDays(9));
        this.name.setText(appointment.getName());
        this.description.setText(appointment.getDescription());
        int hour = date.getHours();
        int minute = date.getMinutes();
        if (hour < 10)
            this.hour.setValue("0"+hour);
        else
            this.hour.setValue(""+hour);
        if (minute < 10)
            this.minute.setValue("0"+minute);
        else
            this.minute.setValue(""+minute);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainController(MainController ctrlr) {
        this.mainController = ctrlr;
    }

}
