package net;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class aul {
   public static void b(Render var0, Entity var1, double var2, double var4, double var6, float var8, float var9) {
      var0.doRender(var1, var2, var4, var6, var8, var9);
   }

   public static void a(Render var0, Entity var1, double var2, double var4, double var6, float var8, float var9) {
      var0.doRenderShadowAndFire(var1, var2, var4, var6, var8, var9);
   }
}
