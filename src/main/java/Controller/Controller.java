/**
 * Anuwat Angkuldee 5810401066
 */

package Controller;

import Model.Appointment;
import Model.Calendar;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.soap.Text;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controller {

    @FXML
    private DatePicker date;
    @FXML
    private TextField apptName;
    @FXML
    private TextArea apptDescription;
    @FXML
    private TextField apptHour;
    @FXML
    private TextField apptMinute;
    @FXML
    private Button addButton;
    @FXML
    private Button clrButton;
    @FXML
    private TextArea appointments;
//    @FXML
//    private TableView<Appointment> apptTable;
//    @FXML
//    private TableColumn<Appointment, String> nameColumn;
//    @FXML
//    private TableColumn<Appointment, String> timeColumn;
//    @FXML
//    private TableColumn<Appointment, String> descColumn;
//
//    private final ObservableList<Appointment> data = FXCollections.observableArrayList();

    private Calendar calendar;

    public Controller() {
        this.calendar = new Calendar();
//        this.date.setValue(LocalDate.now());
//        loadCalendar();

//        nameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("name"));
//        timeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTime"));
//        descColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
//
//        apptTable.setItems(data);
    }

    @FXML
    public void addAppointment() throws ParseException {
        LocalDate date = this.date.getValue();
        String name = apptName.getText();
        String description = apptDescription.getText();
        String hour = apptHour.getText();
        String minute = apptMinute.getText();
        if (date != null && !name.equals("") && !hour.equals("") && !minute.equals("")) {
            String[] dates = (date + "").split("-");

            calendar.addAppointment(dates[2], dates[1], dates[0],
                                    hour, minute,
                                    description, name);
            clearFields();
            findAppointments();
        }
    }

    @FXML
    public void clearFields() {
        apptName.clear();
        apptDescription.clear();
        apptHour.clear();
        apptMinute.clear();
    }

    @FXML
    public void findAppointments() {
        appointments.clear();
        String[] date = (this.date.getValue() + "").split("-");
        ArrayList<Appointment> appts = calendar.findAppointments(date[2], date[1], date[0]);
//        appts.addAll(calendar.findAppointments(date[2]+1, date[1], date[0]));
        appts.sort(new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                int timeO1 = 0;
                int timeO2 = 1;
                try {
                    timeO1 = Integer.parseInt(o1.getStartTime().get("Hour"))*3600
                            + Integer.parseInt(o1.getStartTime().get("Minute"))*60;
                    timeO2 = Integer.parseInt(o2.getStartTime().get("Hour"))*3600
                            + Integer.parseInt(o2.getStartTime().get("Minute"))*60;
                }
                catch (Exception e) {
                    return -1;
                }
                return timeO1-timeO2;
            }
        });
        if (appts.size() != 0) {
            for (Appointment appt : appts) {
                appointments.appendText(appt.toString());
            }
        }
        else {
            appointments.setText("No appointment on this day:\n" + date[2]+"/"+date[1]+"/"+date[0]);
        }
    }
}
