package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class TimedEvent {
    private static final Map<String, Long> mapEventTimes = new HashMap();

    public static boolean isActive(String p_isActive_0_, long p_isActive_1_) {
        synchronized (mapEventTimes) {
            long i = System.currentTimeMillis();
            Long olong = mapEventTimes.get(p_isActive_0_);

            if (olong == null) {
                olong = i;
                mapEventTimes.put(p_isActive_0_, olong);
            }

            long j = olong.longValue();

            if (i < j + p_isActive_1_) {
                return false;
            } else {
                mapEventTimes.put(p_isActive_0_, i);
                return true;
            }
        }
    }
}
