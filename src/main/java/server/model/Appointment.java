package server.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Anuwat Angkuldee 5810401066
 */

public class Appointment {

    private int id;
    private String name;
    private String description;
    private LocalDateTime date;
    private RepeatType repeatType;

//    private String repeat;

//    public Appointment(int id, String name, String description, LocalDateTime date, String repeat) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.date = date;
//        this.repeat = repeat;
//    }

    public Appointment(int id, String name, String description, LocalDateTime date, RepeatType repeatType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.repeatType = repeatType;
    }

    public boolean compareDate(LocalDate localDate) {
        return this.repeatType.compareDate(date.toLocalDate(), localDate);
    }

    public String getDateTimeToString() {
        return getDateToString() + " " + getTimeToString();
    }

    public String getDateToString() {
        return this.date.getDayOfWeek() + " " +
                this.date.getDayOfMonth()+"/"+this.date.getMonthValue()+"/"+this.date.getYear();
    }

    public String getTimeToString() {
        NumberFormat numberFormat = new DecimalFormat("00");
        String hour = numberFormat.format(this.date.getHour());
        String minute = numberFormat.format(this.date.getMinute());
        return hour+":"+minute;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() { return date; }

//    public String getRepeat() {
//        return repeat;
//    }

    public RepeatType getRepeatType() {
        return repeatType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

//    public void setRepeat(String repeat) {
//        this.repeat = repeat;
//    }

    public void setRepeatType(RepeatType repeatType) {
        this.repeatType = repeatType;
    }

    @Override
    public String toString() {
        return this.repeatType.toString(this);
    }

}
