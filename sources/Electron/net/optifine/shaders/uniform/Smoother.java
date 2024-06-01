package net.optifine.shaders.uniform;

import net.minecraft.src.SmoothFloat;

import java.util.HashMap;
import java.util.Map;

public class Smoother {
    private static final Map<Integer, SmoothFloat> mapSmoothValues = new HashMap();

    public static float getSmoothValue(int id, float value, float timeFadeUpSec, float timeFadeDownSec) {
        synchronized (mapSmoothValues) {
            Integer integer = id;
            SmoothFloat smoothfloat = mapSmoothValues.get(integer);

            if (smoothfloat == null) {
                smoothfloat = new SmoothFloat(value, timeFadeUpSec, timeFadeDownSec);
                mapSmoothValues.put(integer, smoothfloat);
            }

            float f = smoothfloat.getSmoothValue(value);
            return f;
        }
    }

    public static void reset() {
        synchronized (mapSmoothValues) {
            mapSmoothValues.clear();
        }
    }
}
