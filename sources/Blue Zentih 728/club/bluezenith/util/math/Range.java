package club.bluezenith.util.math;

public class Range<T extends Number> {
    private final T min, max;

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public static <T extends Number> Range<T> of(T min, T max) {
        return new Range<>(min, max);
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public boolean checkRangeInclusive(T number) {
        return number.doubleValue() >= min.doubleValue() && number.doubleValue() <= max.doubleValue();
    }

    public boolean checkRangeExclusive(T number) {
        return number.doubleValue() > min.doubleValue() && number.doubleValue() < max.doubleValue();
    }

    public T clamp(T number) {
        if(min.doubleValue() > number.doubleValue())
            return min;
        else if(max.doubleValue() < number.doubleValue())
            return max;
        return number;
    }

}