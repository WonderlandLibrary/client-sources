package net;

import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.visual.Chams;
import net.minecraft.entity.EntityLivingBase;

public class a8A {
   public static boolean h(Chams var0) {
      return var0.isEnabled();
   }

   public static BooleanProperty b(Chams var0) {
      return var0.isColored();
   }

   public static BooleanProperty a(Chams var0) {
      return var0.getHand();
   }

   public static ColorProperty g(Chams var0) {
      return var0.getHandColor();
   }

   public static BooleanProperty d(Chams var0) {
      return var0.isMaterial();
   }

   public static float c(Chams var0) {
      return var0.getVisibleAlpha();
   }

   public static boolean a(Chams var0, EntityLivingBase var1) {
      return var0.isValid(var1);
   }

   public static BooleanProperty i(Chams var0) {
      return var0.getRainbow();
   }

   public static ColorProperty f(Chams var0) {
      return var0.getHidden();
   }

   public static ColorProperty e(Chams var0) {
      return var0.getVisible();
   }
}
