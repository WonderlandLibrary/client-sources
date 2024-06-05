package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class RenderSquid extends RenderLiving {
   private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");

   public RenderSquid(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation getEntityTexture(EntitySquid var1) {
      return squidTextures;
   }

   protected void rotateCorpse(EntitySquid var1, float var2, float var3, float var4) {
      float var5 = var1.prevSquidPitch + (var1.squidPitch - var1.prevSquidPitch) * var4;
      float var6 = var1.prevSquidYaw + (var1.squidYaw - var1.prevSquidYaw) * var4;
      GlStateManager.translate(0.0F, 0.5F, 0.0F);
      GlStateManager.rotate(180.0F - var3, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var5, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var6, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, -1.2F, 0.0F);
   }

   protected float handleRotationFloat(EntitySquid var1, float var2) {
      return var1.lastTentacleAngle + (var1.tentacleAngle - var1.lastTentacleAngle) * var2;
   }
}
