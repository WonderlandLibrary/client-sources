package net;

import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.visual.Radar;

public class lh {
   public static boolean e(Radar var0) {
      return var0.isEnabled();
   }

   public static IntProperty b(Radar var0) {
      return var0.getX();
   }

   public static IntProperty d(Radar var0) {
      return var0.getY();
   }

   public static void a(Radar var0) {
      var0.renderRadar();
   }

   public static IntProperty c(Radar var0) {
      return var0.getSize();
   }
}
