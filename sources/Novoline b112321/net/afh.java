package net;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class afh {
   public static void a(GuiButton var0, SoundHandler var1) {
      var0.playPressSound(var1);
   }

   public static void b(GuiButton var0, Minecraft var1, int var2, int var3) {
      var0.drawButton(var1, var2, var3);
   }

   public static boolean a(GuiButton var0, Minecraft var1, int var2, int var3) {
      return var0.mousePressed(var1, var2, var3);
   }

   public static void a(GuiButton var0, int var1, int var2) {
      var0.mouseReleased(var1, var2);
   }

   public static int a(GuiButton var0) {
      return var0.getButtonWidth();
   }

   public static void a(GuiButton var0, int var1) {
      var0.setWidth(var1);
   }

   public static boolean b(GuiButton var0) {
      return var0.isMouseOver();
   }

   public static void b(GuiButton var0, int var1, int var2) {
      var0.drawButtonForegroundLayer(var1, var2);
   }

   public static void a(GuiButton var0, String var1) {
      var0.setDisplayString(var1);
   }
}
