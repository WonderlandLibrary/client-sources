package net;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class sl {
   public static void a(RenderEnderman var0, ResourceLocation var1) {
      var0.bindTexture(var1);
   }

   public static ModelBase a(RenderEnderman var0) {
      return var0.getMainModel();
   }

   public static void a(RenderEnderman var0, EntityLiving var1, float var2) {
      var0.func_177105_a(var1, var2);
   }
}
