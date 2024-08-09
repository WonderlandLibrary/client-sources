package net.optifine.util;

import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;

public class FrameEvent {
    private static final Map<String, Integer> mapEventFrames = new HashMap<>();

    public static boolean isActive(String name, int frameInterval) {
        synchronized (mapEventFrames) {
            int i = Minecraft.getInstance().worldRenderer.getFrameCount();

            if (i <= 0) {
                return false;
            } else {
                Integer integer = mapEventFrames.get(name);

                if (integer == null) {
                    integer = Integer.valueOf(i);
                    mapEventFrames.put(name, integer);
                }

                int j = integer;

                if (i > j && i < j + frameInterval) {
                    return false;
                } else {
                    mapEventFrames.put(name, i);
                    return true;
                }
            }
        }
    }
}
