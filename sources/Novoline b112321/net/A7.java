package net;

import net.acE;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;

public class A7 {
   private static acE[] b;

   public static boolean a(LayerRenderer var0) {
      return var0.shouldCombineTextures();
   }

   public static void a(LayerRenderer var0, EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      acE[] var9 = b();
      var0.doRenderLayer(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[1]);
      }

   }
}
