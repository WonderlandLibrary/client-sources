package net;

import cc.novoline.viaversion.gui.GuiProtocolSelector;
import net.minecraft.client.gui.FontRenderer;

public class mQ {
   private static int b;

   public static void a(GuiProtocolSelector var0) {
      var0.drawDefaultBackground();
   }

   public static void a(GuiProtocolSelector var0, FontRenderer var1, String var2, int var3, int var4, int var5) {
      var0.drawCenteredString(var1, var2, var3, var4, var5);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 122;
   }

   static {
      if(b() != 0) {
         b(7);
      }

   }
}
