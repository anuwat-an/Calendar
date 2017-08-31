/**
 * Anuwat Angkuldee 5810401066
 */

package Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Appointment {

    private Date date;

    private HashMap<String, String> startTime = new HashMap<String, String>();
//    private HashMap<String, String> endTime = new HashMap<String, String>();
    private String description;
    private String name;

    public Appointment(String sDate, String sMonth, String sYear,
                       String sHour, String sMinute,
                       String description, String name) throws ParseException {
        this.name = name;
        this.description = description;

//        /*
//        i will refactor with this later
//         */
//        DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        date = dateTimeFormat.parse(sDate+"/"+sMonth+"/"+sYear+" "+sHour+":"+sMinute);

        this.startTime.put("Date", sDate);
        this.startTime.put("Month", sMonth);
        this.startTime.put("Year", sYear);

        this.startTime.put("Hour", sHour);
        this.startTime.put("Minute", sMinute);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public HashMap<String, String> getStartTime() { return this.startTime; }

    @Override
    public String toString() {
        return this.startTime.get("Date")+"/"+this.startTime.get("Month")+"/"+this.startTime.get("Year")+
                " "+this.startTime.get("Hour")+":"+this.startTime.get("Minute")+"\n"+
                name+"\n"+
                description+"\n\n";
    }

}
