package net;

import net.asJ;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class aEC {
   public static ModelPlayer a(RenderPlayer var0) {
      return var0.getMainModel();
   }

   public static boolean a(RenderPlayer var0, LayerRenderer var1) {
      return var0.addLayer(var1);
   }

   public static void a(RenderPlayer var0, ResourceLocation var1) {
      var0.bindTexture(var1);
   }

   public static void a(RenderPlayer var0, asJ var1) {
      var0.a(var1);
   }

   public static void b(RenderPlayer var0, asJ var1) {
      var0.c(var1);
   }
}
