package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

public class RenderOcelot extends RenderLiving {
   private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
   private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
   private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
   private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");

   public RenderOcelot(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation getEntityTexture(EntityOcelot var1) {
      switch(var1.getTameSkin()) {
      case 0:
      default:
         return ocelotTextures;
      case 1:
         return blackOcelotTextures;
      case 2:
         return redOcelotTextures;
      case 3:
         return siameseOcelotTextures;
      }
   }

   protected void preRenderCallback(EntityOcelot var1, float var2) {
      super.preRenderCallback(var1, var2);
      if(var1.isTamed()) {
         GlStateManager.scale(0.8F, 0.8F, 0.8F);
      }

   }
}
