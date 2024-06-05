package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider extends RenderSpider {
   private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
   private static final String __OBFID = "CL_00000982";

   public RenderCaveSpider(RenderManager var1) {
      super(var1);
      this.shadowSize *= 0.7F;
   }

   protected ResourceLocation getEntityTexture(EntitySpider var1) {
      return this.func_180586_a((EntityCaveSpider)var1);
   }

   protected ResourceLocation func_180586_a(EntityCaveSpider var1) {
      return caveSpiderTextures;
   }

   protected void func_180585_a(EntityCaveSpider var1, float var2) {
      GlStateManager.scale(0.7F, 0.7F, 0.7F);
   }

   protected void preRenderCallback(EntityLivingBase var1, float var2) {
      this.func_180585_a((EntityCaveSpider)var1, var2);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180586_a((EntityCaveSpider)var1);
   }
}
