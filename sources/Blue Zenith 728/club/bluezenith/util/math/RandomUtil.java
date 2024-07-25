package club.bluezenith.util.math;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static <T> T select(List<T> from) {
        if(from.size() == 0) return null;
        if(from.size() == 1) return from.get(0);

        return from.get(intExclusive(0, from.size()));
    }

    public static <T> T select(T[] from) {
        if(from.length == 0) return null;
        if(from.length == 1) return from[0];

        return from[intExclusive(0, from.length)];
    }

    public static int intExclusive(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    public static int intInclusive(int from, int to) {
        return intExclusive(from, to + 1);
    }

}
