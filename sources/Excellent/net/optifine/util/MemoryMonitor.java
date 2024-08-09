package net.optifine.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MemoryMonitor {
    @Getter
    private static long startTimeMs = System.currentTimeMillis();
    private static long startMemory = getMemoryUsed();
    private static long lastTimeMs = startTimeMs;
    private static long lastMemory = startMemory;
    @Getter
    private static boolean gcEvent = false;
    private static long memBytesSec = 0L;
    private static long memBytesSecAvg = 0L;
    private static final List<Long> listMemBytesSec = new ArrayList<>();
    private static long gcBytesSec = 0L;
    private static final long MB = 1048576L;

    public static void update() {
        long i = System.currentTimeMillis();
        long j = getMemoryUsed();
        gcEvent = j < lastMemory;

        if (gcEvent) {
            gcBytesSec = memBytesSec;
            startTimeMs = i;
            startMemory = j;
        }

        long k = i - startTimeMs;
        long l = j - startMemory;
        double d0 = (double) k / 1000.0D;

        if (l >= 0L && d0 > 0.0D) {
            memBytesSec = (long) ((double) l / d0);
            listMemBytesSec.add(memBytesSec);

            if (i / 1000L != lastTimeMs / 1000L) {
                long i1 = 0L;

                for (Long olong : listMemBytesSec) {
                    i1 += olong;
                }

                memBytesSecAvg = i1 / (long) listMemBytesSec.size();
                listMemBytesSec.clear();
            }
        }

        lastTimeMs = i;
        lastMemory = j;
    }

    private static long getMemoryUsed() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static long getStartMemoryMb() {
        return startMemory / MB;
    }

    public static long getAllocationRateMb() {
        return memBytesSec / MB;
    }

    public static long getAllocationRateAvgMb() {
        return memBytesSecAvg / MB;
    }

    public static long getGcRateMb() {
        return gcBytesSec / MB;
    }
}
