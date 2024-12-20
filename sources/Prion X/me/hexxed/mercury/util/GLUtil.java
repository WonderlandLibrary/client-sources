package me.hexxed.mercury.util;

import org.lwjgl.opengl.GL11;

public class GLUtil
{
  public GLUtil() {}
  
  private static java.util.Map<Integer, Boolean> glCapMap = new java.util.HashMap();
  
  public static void setGLCap(int cap, boolean flag) { glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
    if (flag) {
      GL11.glEnable(cap);
    } else
      GL11.glDisable(cap);
  }
  
  public static void revertGLCap(int cap) {
    Boolean origCap = (Boolean)glCapMap.get(Integer.valueOf(cap));
    if (origCap != null) {
      if (origCap.booleanValue()) {
        GL11.glEnable(cap);
      } else
        GL11.glDisable(cap);
    }
  }
  
  public static void revertAllCaps() {
    for (java.util.Iterator localIterator = glCapMap.keySet().iterator(); localIterator.hasNext();) { int cap = ((Integer)localIterator.next()).intValue();
      revertGLCap(cap);
    }
  }
}
