package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderPig extends RenderLiving {
   private static final String __OBFID = "CL_00001019";
   private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180583_a((EntityPig)var1);
   }

   protected ResourceLocation func_180583_a(EntityPig var1) {
      return pigTextures;
   }

   public RenderPig(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
      this.addLayer(new LayerSaddle(this));
   }
}
