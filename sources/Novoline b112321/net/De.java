package net;

import cc.novoline.utils.OpenGLUtil;
import java.awt.Color;

public class De {
   public static void a(int var0, int var1, int var2, int var3) {
      OpenGLUtil.a(var0, var1, var2, var3);
   }

   public static int a(Color var0, Color var1, float var2) {
      return OpenGLUtil.interpolateColor(var0, var1, var2);
   }
}
