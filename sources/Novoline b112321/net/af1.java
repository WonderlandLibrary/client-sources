package net;

import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;

public class af1 {
   private static boolean b;

   public static void e(SpectatorMenu var0) {
      var0.func_178641_d();
   }

   public static void a(SpectatorMenu var0, ISpectatorMenuView var1) {
      var0.func_178647_a(var1);
   }

   public static void a(SpectatorMenu var0, int var1) {
      var0.func_178644_b(var1);
   }

   public static SpectatorDetails a(SpectatorMenu var0) {
      return var0.func_178646_f();
   }

   public static ISpectatorMenuObject b(SpectatorMenu var0) {
      return var0.func_178645_b();
   }

   public static ISpectatorMenuView c(SpectatorMenu var0) {
      return var0.func_178650_c();
   }

   public static int d(SpectatorMenu var0) {
      return var0.func_178648_e();
   }

   public static ISpectatorMenuObject b(SpectatorMenu var0, int var1) {
      return var0.func_178643_a(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(a()) {
         b(true);
      }

   }
}
