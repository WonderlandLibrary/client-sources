package net;

import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.visual.GlintColorize;

public class awc {
   public static boolean c(GlintColorize var0) {
      return var0.isEnabled();
   }

   public static int a(GlintColorize var0) {
      return var0.glintColor();
   }

   public static ColorProperty b(GlintColorize var0) {
      return var0.getColor();
   }

   public static BooleanProperty d(GlintColorize var0) {
      return var0.getRainbow();
   }

   public static int e(GlintColorize var0) {
      return var0.getOffset();
   }
}
