package arsenic.utils.java;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class JavaUtils extends UtilityClass {

    public static <T> T[] concat(T[] a, T[] b) {
        final T[] newArray = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, newArray, a.length, b.length);

        return newArray;
    }
    public static double getRandom(double min, double max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            final double subst = min;
            min = max;
            max = subst;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
