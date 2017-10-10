/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;

public class Appointment {

    protected int id;
    protected String name;
    protected String description;
    protected LocalDateTime date;

    /**
     * USE STRETAGY FOR REPEAT TYPE
     */
//    private String repeat;

//    public Appointment(int id, String name, String description, LocalDateTime date) {
//        this(id, name, description, date, "NONE");
//    }

//    public Appointment(int id, String name, String description, LocalDateTime date, String repeat) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.date = date;
//        this.repeat = repeat;
//    }

    public Appointment(int id, String name, String description, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getDateString() {
        NumberFormat numberFormat = new DecimalFormat("00");
        String hour = numberFormat.format(this.date.getHour());
        String minute = numberFormat.format(this.date.getMinute());

        return date.getDayOfWeek().getValue() + " " +
                date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear()+" "+
                hour+":"+minute;
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

//    @Override
//    public String toString() {
//        NumberFormat numberFormat = new DecimalFormat("00");
//        String hour = numberFormat.format(this.date.getHour());
//        String minute = numberFormat.format(this.date.getMinute());
//
//        if ("DAILY".equalsIgnoreCase(this.repeat)) {
//            return this.id + ": " + this.repeat + " " +
//                    hour + ":" + minute + "\n" +
//                    this.name + "\n" +
//                    this.description + "\n";
//        }
//        else if ("WEEKLY".equalsIgnoreCase((this.repeat))) {
//            return this.id + ": " + this.date.getDayOfWeek() + " " +
//                    hour + ":" + minute + "\n" +
//                    this.name + "\n" +
//                    this.description + "\n";
//        }
//        else if ("MONTHLY".equalsIgnoreCase(this.repeat)) {
//            return this.id + ": " + this.repeat + " " +
//                    this.date.getDayOfMonth() + " " +
//                    hour + ":" + minute + "\n" +
//                    this.name + "\n" +
//                    this.description + "\n";
//        }
//        return this.id + ": " + this.date.getDayOfWeek() + " " +
//                this.date.getDayOfMonth()+"/"+this.date.getMonthValue()+"/"+this.date.getYear() + " " +
//                hour + ":" + minute + "\n" +
//                this.name + "\n" +
//                this.description + "\n";
//    }

    @Override
    public String toString() {
        NumberFormat numberFormat = new DecimalFormat("00");
        String hour = numberFormat.format(this.date.getHour());
        String minute = numberFormat.format(this.date.getMinute());

        return hour + ":" + minute + "\n" +
                name + "\n" +
                description + "\n";
    }

}
