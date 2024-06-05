package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderChicken extends RenderLiving {
   private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");

   public RenderChicken(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation getEntityTexture(EntityChicken var1) {
      return chickenTextures;
   }

   protected float handleRotationFloat(EntityChicken var1, float var2) {
      float var3 = var1.field_70888_h + (var1.wingRotation - var1.field_70888_h) * var2;
      float var4 = var1.field_70884_g + (var1.destPos - var1.field_70884_g) * var2;
      return (MathHelper.sin(var3) + 1.0F) * var4;
   }
}
