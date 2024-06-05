package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class RenderCow extends RenderLiving {
   private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
   private static final String __OBFID = "CL_00000984";

   protected ResourceLocation func_180572_a(EntityCow var1) {
      return cowTextures;
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180572_a((EntityCow)var1);
   }

   public RenderCow(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }
}
