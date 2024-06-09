/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.lwjgl.opengl.GL11;

public class GLUtil {
    private static Map<Integer, Boolean> glCapMap = new HashMap<Integer, Boolean>();

    public static void setGLCap(int cap, boolean flag) {
        glCapMap.put(cap, GL11.glGetBoolean((int)cap));
        if (flag) {
            GL11.glEnable((int)cap);
        } else {
            GL11.glDisable((int)cap);
        }
    }

    public static void revertGLCap(int cap) {
        Boolean origCap = glCapMap.get(cap);
        if (origCap != null) {
            if (origCap.booleanValue()) {
                GL11.glEnable((int)cap);
            } else {
                GL11.glDisable((int)cap);
            }
        }
    }

    public static void glEnable(int cap) {
        GLUtil.setGLCap(cap, true);
    }

    public static void glDisable(int cap) {
        GLUtil.setGLCap(cap, false);
    }

    public static void revertAllCaps() {
        Iterator<Integer> localIterator = glCapMap.keySet().iterator();
        while (localIterator.hasNext()) {
            int cap = localIterator.next();
            GLUtil.revertGLCap(cap);
        }
    }
}

