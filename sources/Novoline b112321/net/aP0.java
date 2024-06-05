package net;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.util.ResourceLocation;

public class aP0 {
   public static void a(RenderCreeper var0, ResourceLocation var1) {
      var0.bindTexture(var1);
   }

   public static ModelBase a(RenderCreeper var0) {
      return var0.getMainModel();
   }
}
