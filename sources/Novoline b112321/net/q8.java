package net;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class q8 {
   public static Rectangle2D a(FontMetrics var0, String var1, Graphics var2) {
      return var0.getStringBounds(var1, var2);
   }

   public static int a(FontMetrics var0) {
      return var0.getAscent();
   }
}
