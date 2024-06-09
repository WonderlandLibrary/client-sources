package io.github.liticane.clients.util.misc;

public class Random {
    public static double nextDouble(final double startInclusive, final double endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0D)
            return startInclusive;

        return startInclusive + ((endInclusive - startInclusive) * Math.random());
    }
}
