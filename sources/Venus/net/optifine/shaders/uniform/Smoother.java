/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import java.util.HashMap;
import java.util.Map;
import net.optifine.util.CounterInt;
import net.optifine.util.SmoothFloat;

public class Smoother {
    private static Map<Integer, SmoothFloat> mapSmoothValues = new HashMap<Integer, SmoothFloat>();
    private static CounterInt counterIds = new CounterInt(1);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static float getSmoothValue(int n, float f, float f2, float f3) {
        Map<Integer, SmoothFloat> map = mapSmoothValues;
        synchronized (map) {
            Integer n2 = n;
            SmoothFloat smoothFloat = mapSmoothValues.get(n2);
            if (smoothFloat == null) {
                smoothFloat = new SmoothFloat(f, f2, f3);
                mapSmoothValues.put(n2, smoothFloat);
            }
            return smoothFloat.getSmoothValue(f, f2, f3);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getNextId() {
        CounterInt counterInt = counterIds;
        synchronized (counterInt) {
            return counterIds.nextValue();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetValues() {
        Map<Integer, SmoothFloat> map = mapSmoothValues;
        synchronized (map) {
            mapSmoothValues.clear();
        }
    }
}

