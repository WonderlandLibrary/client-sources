package net;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class hq {
   public static void a(RenderSpider var0, ResourceLocation var1) {
      var0.bindTexture(var1);
   }

   public static ModelBase a(RenderSpider var0) {
      return var0.getMainModel();
   }

   public static void a(RenderSpider var0, EntityLiving var1, float var2) {
      var0.func_177105_a(var1, var2);
   }
}
