package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.MathHelper;















public class ModelWolf
  extends ModelBase
{
  public ModelRenderer wolfHeadMain;
  public ModelRenderer wolfBody;
  public ModelRenderer wolfLeg1;
  public ModelRenderer wolfLeg2;
  public ModelRenderer wolfLeg3;
  public ModelRenderer wolfLeg4;
  ModelRenderer wolfTail;
  ModelRenderer wolfMane;
  private static final String __OBFID = "CL_00000868";
  
  public ModelWolf()
  {
    float var1 = 0.0F;
    float var2 = 13.5F;
    wolfHeadMain = new ModelRenderer(this, 0, 0);
    wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, var1);
    wolfHeadMain.setRotationPoint(-1.0F, var2, -7.0F);
    wolfBody = new ModelRenderer(this, 18, 14);
    wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, var1);
    wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
    wolfMane = new ModelRenderer(this, 21, 0);
    wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, var1);
    wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
    wolfLeg1 = new ModelRenderer(this, 0, 18);
    wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
    wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
    wolfLeg2 = new ModelRenderer(this, 0, 18);
    wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
    wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
    wolfLeg3 = new ModelRenderer(this, 0, 18);
    wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
    wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
    wolfLeg4 = new ModelRenderer(this, 0, 18);
    wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
    wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
    wolfTail = new ModelRenderer(this, 9, 18);
    wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
    wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
    wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, var1);
    wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, var1);
    wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, var1);
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    
    if (isChild)
    {
      float var8 = 2.0F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 5.0F * p_78088_7_, 2.0F * p_78088_7_);
      wolfHeadMain.renderWithRotation(p_78088_7_);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
      GlStateManager.translate(0.0F, 24.0F * p_78088_7_, 0.0F);
      wolfBody.render(p_78088_7_);
      wolfLeg1.render(p_78088_7_);
      wolfLeg2.render(p_78088_7_);
      wolfLeg3.render(p_78088_7_);
      wolfLeg4.render(p_78088_7_);
      wolfTail.renderWithRotation(p_78088_7_);
      wolfMane.render(p_78088_7_);
      GlStateManager.popMatrix();
    }
    else
    {
      wolfHeadMain.renderWithRotation(p_78088_7_);
      wolfBody.render(p_78088_7_);
      wolfLeg1.render(p_78088_7_);
      wolfLeg2.render(p_78088_7_);
      wolfLeg3.render(p_78088_7_);
      wolfLeg4.render(p_78088_7_);
      wolfTail.renderWithRotation(p_78088_7_);
      wolfMane.render(p_78088_7_);
    }
  }
  




  public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
  {
    EntityWolf var5 = (EntityWolf)p_78086_1_;
    
    if (var5.isAngry())
    {
      wolfTail.rotateAngleY = 0.0F;
    }
    else
    {
      wolfTail.rotateAngleY = (MathHelper.cos(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_);
    }
    
    if (var5.isSitting())
    {
      wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
      wolfMane.rotateAngleX = 1.2566371F;
      wolfMane.rotateAngleY = 0.0F;
      wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
      wolfBody.rotateAngleX = 0.7853982F;
      wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
      wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
      wolfLeg1.rotateAngleX = 4.712389F;
      wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
      wolfLeg2.rotateAngleX = 4.712389F;
      wolfLeg3.rotateAngleX = 5.811947F;
      wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
      wolfLeg4.rotateAngleX = 5.811947F;
      wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
    }
    else
    {
      wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
      wolfBody.rotateAngleX = 1.5707964F;
      wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
      wolfMane.rotateAngleX = wolfBody.rotateAngleX;
      wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
      wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
      wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
      wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
      wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
      wolfLeg1.rotateAngleX = (MathHelper.cos(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_);
      wolfLeg2.rotateAngleX = (MathHelper.cos(p_78086_2_ * 0.6662F + 3.1415927F) * 1.4F * p_78086_3_);
      wolfLeg3.rotateAngleX = (MathHelper.cos(p_78086_2_ * 0.6662F + 3.1415927F) * 1.4F * p_78086_3_);
      wolfLeg4.rotateAngleX = (MathHelper.cos(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_);
    }
    
    wolfHeadMain.rotateAngleZ = (var5.getInterestedAngle(p_78086_4_) + var5.getShakeAngle(p_78086_4_, 0.0F));
    wolfMane.rotateAngleZ = var5.getShakeAngle(p_78086_4_, -0.08F);
    wolfBody.rotateAngleZ = var5.getShakeAngle(p_78086_4_, -0.16F);
    wolfTail.rotateAngleZ = var5.getShakeAngle(p_78086_4_, -0.2F);
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    wolfHeadMain.rotateAngleX = (p_78087_5_ / 57.295776F);
    wolfHeadMain.rotateAngleY = (p_78087_4_ / 57.295776F);
    wolfTail.rotateAngleX = p_78087_3_;
  }
}
