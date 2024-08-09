/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.ArrayList;
import java.util.List;

public class MemoryMonitor {
    private static long startTimeMs = System.currentTimeMillis();
    private static long startMemory = MemoryMonitor.getMemoryUsed();
    private static long lastTimeMs = startTimeMs;
    private static long lastMemory = startMemory;
    private static boolean gcEvent = false;
    private static long memBytesSec = 0L;
    private static long memBytesSecAvg = 0L;
    private static List<Long> listMemBytesSec = new ArrayList<Long>();
    private static long gcBytesSec = 0L;
    private static long MB = 0x100000L;

    public static void update() {
        long l = System.currentTimeMillis();
        long l2 = MemoryMonitor.getMemoryUsed();
        boolean bl = gcEvent = l2 < lastMemory;
        if (gcEvent) {
            gcBytesSec = memBytesSec;
            startTimeMs = l;
            startMemory = l2;
        }
        long l3 = l - startTimeMs;
        long l4 = l2 - startMemory;
        double d = (double)l3 / 1000.0;
        if (l4 >= 0L && d > 0.0) {
            memBytesSec = (long)((double)l4 / d);
            listMemBytesSec.add(memBytesSec);
            if (l / 1000L != lastTimeMs / 1000L) {
                long l5 = 0L;
                for (Long l6 : listMemBytesSec) {
                    l5 += l6.longValue();
                }
                memBytesSecAvg = l5 / (long)listMemBytesSec.size();
                listMemBytesSec.clear();
            }
        }
        lastTimeMs = l;
        lastMemory = l2;
    }

    private static long getMemoryUsed() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static long getStartTimeMs() {
        return startTimeMs;
    }

    public static long getStartMemoryMb() {
        return startMemory / MB;
    }

    public static boolean isGcEvent() {
        return gcEvent;
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

