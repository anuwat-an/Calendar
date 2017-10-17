package Model;

public class MonthlyRepeat implements RepeatType, Comparable<RepeatType> {

    @Override
    public String getRepeat() {
        return "MONTHLY";
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
