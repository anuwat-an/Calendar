package Model;

public class DailyRepeat implements RepeatType, Comparable<RepeatType> {

    @Override
    public String getRepeat() {
        return "DAILY";
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
