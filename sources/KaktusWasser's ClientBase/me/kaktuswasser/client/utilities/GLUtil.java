// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import java.util.HashMap;
import java.util.Map;

public class GLUtil
{
    private static Map<Integer, Boolean> glCapMap;
    
    static {
        GLUtil.glCapMap = new HashMap<Integer, Boolean>();
    }
    
    public static void setGLCap(final int cap, final boolean flag) {
        GLUtil.glCapMap.put(cap, GL11.glGetBoolean(cap));
        if (flag) {
            GL11.glEnable(cap);
        }
        else {
            GL11.glDisable(cap);
        }
    }
    
    public static void revertGLCap(final int cap) {
        final Boolean origCap = GLUtil.glCapMap.get(cap);
        if (origCap != null) {
            if (origCap) {
                GL11.glEnable(cap);
            }
            else {
                GL11.glDisable(cap);
            }
        }
    }
    
    public static void glEnable(final int cap) {
        setGLCap(cap, true);
    }
    
    public static void glDisable(final int cap) {
        setGLCap(cap, false);
    }
    
    public static void revertAllCaps() {
        for (final int cap : GLUtil.glCapMap.keySet()) {
            revertGLCap(cap);
        }
    }
}
