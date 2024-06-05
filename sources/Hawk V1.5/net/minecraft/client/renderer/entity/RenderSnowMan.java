package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.layers.LayerSnowmanHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ResourceLocation;

public class RenderSnowMan extends RenderLiving {
   private static final String __OBFID = "CL_00001025";
   private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");

   public ModelBase getMainModel() {
      return this.func_177123_g();
   }

   public RenderSnowMan(RenderManager var1) {
      super(var1, new ModelSnowMan(), 0.5F);
      this.addLayer(new LayerSnowmanHead(this));
   }

   public ModelSnowMan func_177123_g() {
      return (ModelSnowMan)super.getMainModel();
   }

   protected ResourceLocation func_180587_a(EntitySnowman var1) {
      return snowManTextures;
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180587_a((EntitySnowman)var1);
   }
}
