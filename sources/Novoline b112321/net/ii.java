package net;

import net.minecraft.client.gui.GuiResourcePackSelected;

public class ii {
   public static void a(GuiResourcePackSelected var0, int var1) {
      var0.setSlotXBoundsFromLeft(var1);
   }

   public static void a(GuiResourcePackSelected var0, int var1, int var2) {
      var0.registerScrollButtons(var1, var2);
   }

   public static boolean a(GuiResourcePackSelected var0, int var1, int var2, int var3) {
      return var0.mouseClicked(var1, var2, var3);
   }
}
