package net;

import cc.novoline.gui.group.GuiGroupAltLogin;
import net.minecraft.client.Minecraft;

public class p3 {
   private static String[] b;

   public static void a(GuiGroupAltLogin var0, String var1) {
      var0.updateStatus(var1);
   }

   public static void a(GuiGroupAltLogin var0, Minecraft var1, int var2, int var3) {
      var0.drawGroup(var1, var2, var3);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[4]);
      }

   }
}
