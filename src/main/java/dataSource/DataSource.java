/**
 * Anuwat Angkuldee 5810401066
 */

package dataSource;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class DataSource {

    protected Map<String, RepeatType> repeatTypeMap;

    public DataSource() {
        repeatTypeMap = new HashMap<>();
        repeatTypeMap.put("NONE", new NoneRepeat());
        repeatTypeMap.put("DAILY", new DailyRepeat());
        repeatTypeMap.put("WEEKLY", new WeeklyRepeat());
        repeatTypeMap.put("MONTHLY", new MonthlyRepeat());
    }

    public abstract ArrayList<Appointment> loadData();
    public abstract int getLastID();
    public abstract void addData(Appointment appointment);
    public abstract void updateData(Appointment appointment);
    public abstract void deleteData(int id);

}
