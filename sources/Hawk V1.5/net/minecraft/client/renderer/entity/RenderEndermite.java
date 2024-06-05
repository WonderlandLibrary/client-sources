package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.util.ResourceLocation;

public class RenderEndermite extends RenderLiving {
   private static final String __OBFID = "CL_00002445";
   private static final ResourceLocation field_177108_a = new ResourceLocation("textures/entity/endermite.png");

   protected float func_177107_a(EntityEndermite var1) {
      return 180.0F;
   }

   protected float getDeathMaxRotation(EntityLivingBase var1) {
      return this.func_177107_a((EntityEndermite)var1);
   }

   public RenderEndermite(RenderManager var1) {
      super(var1, new ModelEnderMite(), 0.3F);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_177106_b((EntityEndermite)var1);
   }

   protected ResourceLocation func_177106_b(EntityEndermite var1) {
      return field_177108_a;
   }
}
