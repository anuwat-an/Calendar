/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import DataSource.AppointmentDataSource;
import Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class EditPageController {

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

    private Appointment appointment;
    private AppointmentDataSource dataSource;

    private Date date = new Date();
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

        if (localDate != null && !"".equals(name) && hour != null && minute != null && repeat != null) {
            try {
                /**
                 * edit appointment
                 */
                this.appointment.setName(name);
                this.appointment.setDescription(description);
                this.date = dateFormat.parse(date);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
                this.appointment.setDate(localDateTime);
                this.appointment.setRepeatType(repeat);

                this.dataSource.updateData(appointment);

                this.stage.close();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void cancelEdit() {
        this.stage.close();
    }

    public void setEditAppointment(Appointment appointment) {
        this.appointment = appointment;
        this.datePicker.setValue(appointment.getDate().toLocalDate());
        this.name.setText(appointment.getName());
        this.description.setText(appointment.getDescription());
        this.hour.setValue(appointment.getDate().getHour()+"");
        this.minute.setValue(appointment.getDate().getMinute()+"");
        this.repeatComboBox.setValue(appointment.getRepeatType().getRepeat());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDataSource(AppointmentDataSource dataSource) {
        this.dataSource = dataSource;
    }

}
