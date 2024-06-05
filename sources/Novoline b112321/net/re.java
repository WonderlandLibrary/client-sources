package net;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.util.ResourceLocation;

public class re {
   public static void a(RenderWither var0, ResourceLocation var1) {
      var0.bindTexture(var1);
   }

   public static ModelBase a(RenderWither var0) {
      return var0.getMainModel();
   }
}
