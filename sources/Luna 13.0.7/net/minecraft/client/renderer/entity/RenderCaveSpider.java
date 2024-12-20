package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider
  extends RenderSpider
{
  private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
  private static final String __OBFID = "CL_00000982";
  
  public RenderCaveSpider(RenderManager p_i46189_1_)
  {
    super(p_i46189_1_);
    this.shadowSize *= 0.7F;
  }
  
  protected void func_180585_a(EntityCaveSpider p_180585_1_, float p_180585_2_)
  {
    GlStateManager.scale(0.7F, 0.7F, 0.7F);
  }
  
  protected ResourceLocation func_180586_a(EntityCaveSpider p_180586_1_)
  {
    return caveSpiderTextures;
  }
  
  protected ResourceLocation getEntityTexture(EntitySpider p_110775_1_)
  {
    return func_180586_a((EntityCaveSpider)p_110775_1_);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180585_a((EntityCaveSpider)p_77041_1_, p_77041_2_);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_)
  {
    return func_180586_a((EntityCaveSpider)p_110775_1_);
  }
}
