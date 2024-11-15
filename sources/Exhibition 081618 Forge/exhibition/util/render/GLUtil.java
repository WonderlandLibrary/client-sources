package exhibition.util.render;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class GLUtil {
   private static Map glCapMap = new HashMap();

   public static void revertGLCap(int cap) {
      Boolean origCap = (Boolean)glCapMap.get(cap);
      if (origCap != null) {
         if (origCap.booleanValue()) {
            GL11.glEnable(cap);
         } else {
            GL11.glDisable(cap);
         }
      }

   }

   public static void revertAllCaps() {
      Iterator var0 = glCapMap.keySet().iterator();

      while(var0.hasNext()) {
         int cap = ((Integer)var0.next()).intValue();
         revertGLCap(cap);
      }

   }

   public static void setGLCap(int cap, boolean flag) {
      glCapMap.put(cap, GL11.glGetBoolean(cap));
      if (flag) {
         GL11.glEnable(cap);
      } else {
         GL11.glDisable(cap);
      }

   }
}
