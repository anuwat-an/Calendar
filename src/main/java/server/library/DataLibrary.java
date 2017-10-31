package server.library;

import server.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Locale;

/**
 * Anuwat Angkuldee 5810401066
 */

public class DataLibrary {

    public static final Hashtable<String, RepeatType> repeatTypeMap;
    public static final DateFormat dateFormat;
    static {
        repeatTypeMap = new Hashtable<>();
        repeatTypeMap.put("NONE", new NoneRepeat());
        repeatTypeMap.put("DAILY", new DailyRepeat());
        repeatTypeMap.put("WEEKLY", new WeeklyRepeat());
        repeatTypeMap.put("MONTHLY", new MonthlyRepeat());

        dateFormat = new SimpleDateFormat("E dd/MM/yyyy HH:mm", Locale.US);
    }

}
