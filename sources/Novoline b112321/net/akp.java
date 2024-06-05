package net;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.util.ResourceLocation;

public class akp {
   public static void a(RenderWolf var0, ResourceLocation var1) {
      var0.bindTexture(var1);
   }

   public static ModelBase a(RenderWolf var0) {
      return var0.getMainModel();
   }
}
