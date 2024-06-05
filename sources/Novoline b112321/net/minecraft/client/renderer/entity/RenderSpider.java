package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderSpider extends RenderLiving {
   private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");

   public RenderSpider(RenderManager var1) {
      super(var1, new ModelSpider(), 1.0F);
      this.addLayer(new LayerSpiderEyes(this));
   }

   protected float getDeathMaxRotation(EntitySpider var1) {
      return 180.0F;
   }

   protected ResourceLocation getEntityTexture(EntitySpider var1) {
      return spiderTextures;
   }
}
