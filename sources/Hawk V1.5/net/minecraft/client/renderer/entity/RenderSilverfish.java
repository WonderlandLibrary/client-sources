package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

public class RenderSilverfish extends RenderLiving {
   private static final String __OBFID = "CL_00001022";
   private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");

   protected ResourceLocation getEntityTexture(EntitySilverfish var1) {
      return silverfishTextures;
   }

   protected float func_180584_a(EntitySilverfish var1) {
      return 180.0F;
   }

   public RenderSilverfish(RenderManager var1) {
      super(var1, new ModelSilverfish(), 0.3F);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getEntityTexture((EntitySilverfish)var1);
   }

   protected float getDeathMaxRotation(EntityLivingBase var1) {
      return this.func_180584_a((EntitySilverfish)var1);
   }
}
