/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.HashMap;
import java.util.Map;

public class TimedEvent {
    private static Map<String, Long> mapEventTimes = new HashMap<String, Long>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isActive(String string, long l) {
        Map<String, Long> map = mapEventTimes;
        synchronized (map) {
            long l2;
            long l3 = System.currentTimeMillis();
            Long l4 = mapEventTimes.get(string);
            if (l4 == null) {
                l4 = new Long(l3);
                mapEventTimes.put(string, l4);
            }
            if (l3 < (l2 = l4.longValue()) + l) {
                return false;
            }
            mapEventTimes.put(string, new Long(l3));
            return true;
        }
    }
}

