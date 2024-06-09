package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import java.util.HashMap;
import java.util.Map;

public class GLUtil
{
    private static Map<Integer, Boolean> HorizonCode_Horizon_È;
    
    static {
        GLUtil.HorizonCode_Horizon_È = new HashMap<Integer, Boolean>();
    }
    
    public static void HorizonCode_Horizon_È(final int cap, final boolean flag) {
        GLUtil.HorizonCode_Horizon_È.put(cap, GL11.glGetBoolean(cap));
        if (flag) {
            GL11.glEnable(cap);
        }
        else {
            GL11.glDisable(cap);
        }
    }
    
    public static void HorizonCode_Horizon_È(final int cap) {
        final Boolean origCap = GLUtil.HorizonCode_Horizon_È.get(cap);
        if (origCap != null) {
            if (origCap) {
                GL11.glEnable(cap);
            }
            else {
                GL11.glDisable(cap);
            }
        }
    }
    
    public static void Â(final int cap) {
        HorizonCode_Horizon_È(cap, true);
    }
    
    public static void Ý(final int cap) {
        HorizonCode_Horizon_È(cap, false);
    }
    
    public static void HorizonCode_Horizon_È() {
        for (final int cap : GLUtil.HorizonCode_Horizon_È.keySet()) {
            HorizonCode_Horizon_È(cap);
        }
    }
}
