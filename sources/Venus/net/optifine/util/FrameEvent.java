/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class FrameEvent {
    private static Map<String, Integer> mapEventFrames = new HashMap<String, Integer>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isActive(String string, int n) {
        Map<String, Integer> map = mapEventFrames;
        synchronized (map) {
            int n2;
            int n3 = Minecraft.getInstance().worldRenderer.getFrameCount();
            if (n3 <= 0) {
                return false;
            }
            Integer n4 = mapEventFrames.get(string);
            if (n4 == null) {
                n4 = new Integer(n3);
                mapEventFrames.put(string, n4);
            }
            if (n3 > (n2 = n4.intValue()) && n3 < n2 + n) {
                return false;
            }
            mapEventFrames.put(string, new Integer(n3));
            return true;
        }
    }
}

