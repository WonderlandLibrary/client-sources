package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider extends RenderSpider {
   private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");

   public RenderCaveSpider(RenderManager var1) {
      super(var1);
      this.shadowSize *= 0.7F;
   }

   protected void preRenderCallback(EntityCaveSpider var1, float var2) {
      GlStateManager.scale(0.7F, 0.7F, 0.7F);
   }

   protected ResourceLocation getEntityTexture(EntityCaveSpider var1) {
      return caveSpiderTextures;
   }
}
