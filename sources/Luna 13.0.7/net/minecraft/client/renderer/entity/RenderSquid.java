package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class RenderSquid
  extends RenderLiving
{
  private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");
  private static final String __OBFID = "CL_00001028";
  
  public RenderSquid(RenderManager p_i46138_1_, ModelBase p_i46138_2_, float p_i46138_3_)
  {
    super(p_i46138_1_, p_i46138_2_, p_i46138_3_);
  }
  
  protected ResourceLocation getEntityTexture(EntitySquid p_110775_1_)
  {
    return squidTextures;
  }
  
  protected void rotateCorpse(EntitySquid p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
  {
    float var5 = p_77043_1_.prevSquidPitch + (p_77043_1_.squidPitch - p_77043_1_.prevSquidPitch) * p_77043_4_;
    float var6 = p_77043_1_.prevSquidYaw + (p_77043_1_.squidYaw - p_77043_1_.prevSquidYaw) * p_77043_4_;
    GlStateManager.translate(0.0F, 0.5F, 0.0F);
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(var5, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(var6, 0.0F, 1.0F, 0.0F);
    GlStateManager.translate(0.0F, -1.2F, 0.0F);
  }
  
  protected float handleRotationFloat(EntitySquid p_77044_1_, float p_77044_2_)
  {
    return p_77044_1_.lastTentacleAngle + (p_77044_1_.tentacleAngle - p_77044_1_.lastTentacleAngle) * p_77044_2_;
  }
  
  protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_)
  {
    return handleRotationFloat((EntitySquid)p_77044_1_, p_77044_2_);
  }
  
  protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
  {
    rotateCorpse((EntitySquid)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_)
  {
    return getEntityTexture((EntitySquid)p_110775_1_);
  }
}
