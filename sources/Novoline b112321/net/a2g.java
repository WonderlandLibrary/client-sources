package net;

import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.util.IChatComponent;

public class a2g {
   public static boolean a(ISpectatorMenuObject var0) {
      return var0.func_178662_A_();
   }

   public static void a(ISpectatorMenuObject var0, SpectatorMenu var1) {
      var0.func_178661_a(var1);
   }

   public static void a(ISpectatorMenuObject var0, float var1, int var2) {
      var0.func_178663_a(var1, var2);
   }

   public static IChatComponent b(ISpectatorMenuObject var0) {
      return var0.getSpectatorName();
   }
}
