/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;

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

    public Appointment(int id, String name, String description, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getDateToString() {
        return this.date.getDayOfWeek() + " " +
                this.date.getDayOfMonth()+"/"+this.date.getMonthValue()+"/"+this.date.getYear() + " " +
                this.date.getHour()+":"+this.date.getMinute();
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

    public void setRepeatType(String type) {
        if ("DAILY".equalsIgnoreCase(type))
            this.repeatType = new DailyRepeat();
        else if ("WEEKLY".equalsIgnoreCase(type))
            this.repeatType = new WeeklyRepeat();
        else if ("MONTHLY".equalsIgnoreCase(type))
            this.repeatType = new MonthlyRepeat();
        else if ("NONE".equalsIgnoreCase(type))
            this.repeatType = new NoneRepeat();
    }

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

}
