package Model;

public class WeeklyRepeat implements RepeatType, Comparable<RepeatType> {

    @Override
    public String getRepeat() {
        return "WEEKLY";
    }

    @Override
    public int compareTo(RepeatType o) {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}