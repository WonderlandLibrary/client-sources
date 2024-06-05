package net;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class alk {
   private static int[] b;

   public static Tessellator a() {
      return Tessellator.getInstance();
   }

   public static WorldRenderer a(Tessellator var0) {
      return var0.getWorldRenderer();
   }

   public static void b(Tessellator var0) {
      var0.draw();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[4]);
      }

   }
}
