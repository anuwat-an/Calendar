package Model;

public class NoneRepeat implements RepeatType, Comparable<RepeatType> {

    @Override
    public String getRepeat() {
        return "NONE";
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