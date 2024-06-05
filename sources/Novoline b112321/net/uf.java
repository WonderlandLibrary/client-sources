package net;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class uf {
   public static int b(GuiButton var0) {
      return GuiVideoSettings.getButtonWidth(var0);
   }

   public static int a(GuiButton var0) {
      return GuiVideoSettings.getButtonHeight(var0);
   }

   public static void a(GuiScreen var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      GuiVideoSettings.drawGradientRect(var0, var1, var2, var3, var4, var5, var6);
   }
}
