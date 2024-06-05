package net;

import cc.novoline.gui.button.HydraButton;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;

public class apq {
   private static acE[] b;

   public static void a(HydraButton var0, Minecraft var1, int var2, int var3) {
      var0.drawButton(var1, var2, var3);
   }

   public static boolean a(HydraButton var0, int var1, int var2) {
      return var0.hovered(var1, var2);
   }

   public static void a(HydraButton var0, int var1) {
      var0.b(var1);
   }

   public static void b(HydraButton var0, int var1) {
      var0.c(var1);
   }

   public static void a(HydraButton var0, SoundHandler var1) {
      var0.playPressSound(var1);
   }

   public static void a(HydraButton var0, float var1, float var2) {
      var0.updateCoordinates(var1, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[2]);
      }

   }
}
