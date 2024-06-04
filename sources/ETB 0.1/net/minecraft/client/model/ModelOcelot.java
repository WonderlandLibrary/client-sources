package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.MathHelper;















public class ModelOcelot
  extends ModelBase
{
  ModelRenderer ocelotBackLeftLeg;
  ModelRenderer ocelotBackRightLeg;
  ModelRenderer ocelotFrontLeftLeg;
  ModelRenderer ocelotFrontRightLeg;
  ModelRenderer ocelotTail;
  ModelRenderer ocelotTail2;
  ModelRenderer ocelotHead;
  ModelRenderer ocelotBody;
  int field_78163_i = 1;
  private static final String __OBFID = "CL_00000848";
  
  public ModelOcelot()
  {
    setTextureOffset("head.main", 0, 0);
    setTextureOffset("head.nose", 0, 24);
    setTextureOffset("head.ear1", 0, 10);
    setTextureOffset("head.ear2", 6, 10);
    ocelotHead = new ModelRenderer(this, "head");
    ocelotHead.addBox("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
    ocelotHead.addBox("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
    ocelotHead.addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
    ocelotHead.addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
    ocelotHead.setRotationPoint(0.0F, 15.0F, -9.0F);
    ocelotBody = new ModelRenderer(this, 20, 0);
    ocelotBody.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
    ocelotBody.setRotationPoint(0.0F, 12.0F, -10.0F);
    ocelotTail = new ModelRenderer(this, 0, 15);
    ocelotTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
    ocelotTail.rotateAngleX = 0.9F;
    ocelotTail.setRotationPoint(0.0F, 15.0F, 8.0F);
    ocelotTail2 = new ModelRenderer(this, 4, 15);
    ocelotTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
    ocelotTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
    ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
    ocelotBackLeftLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
    ocelotBackLeftLeg.setRotationPoint(1.1F, 18.0F, 5.0F);
    ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
    ocelotBackRightLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
    ocelotBackRightLeg.setRotationPoint(-1.1F, 18.0F, 5.0F);
    ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
    ocelotFrontLeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
    ocelotFrontLeftLeg.setRotationPoint(1.2F, 13.8F, -5.0F);
    ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
    ocelotFrontRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
    ocelotFrontRightLeg.setRotationPoint(-1.2F, 13.8F, -5.0F);
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    
    if (isChild)
    {
      float var8 = 2.0F;
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.5F / var8, 1.5F / var8, 1.5F / var8);
      GlStateManager.translate(0.0F, 10.0F * p_78088_7_, 4.0F * p_78088_7_);
      ocelotHead.render(p_78088_7_);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
      GlStateManager.translate(0.0F, 24.0F * p_78088_7_, 0.0F);
      ocelotBody.render(p_78088_7_);
      ocelotBackLeftLeg.render(p_78088_7_);
      ocelotBackRightLeg.render(p_78088_7_);
      ocelotFrontLeftLeg.render(p_78088_7_);
      ocelotFrontRightLeg.render(p_78088_7_);
      ocelotTail.render(p_78088_7_);
      ocelotTail2.render(p_78088_7_);
      GlStateManager.popMatrix();
    }
    else
    {
      ocelotHead.render(p_78088_7_);
      ocelotBody.render(p_78088_7_);
      ocelotTail.render(p_78088_7_);
      ocelotTail2.render(p_78088_7_);
      ocelotBackLeftLeg.render(p_78088_7_);
      ocelotBackRightLeg.render(p_78088_7_);
      ocelotFrontLeftLeg.render(p_78088_7_);
      ocelotFrontRightLeg.render(p_78088_7_);
    }
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    ocelotHead.rotateAngleX = (p_78087_5_ / 57.295776F);
    ocelotHead.rotateAngleY = (p_78087_4_ / 57.295776F);
    
    if (field_78163_i != 3)
    {
      ocelotBody.rotateAngleX = 1.5707964F;
      
      if (field_78163_i == 2)
      {
        ocelotBackLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_);
        ocelotBackRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 0.3F) * 1.0F * p_78087_2_);
        ocelotFrontLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F + 0.3F) * 1.0F * p_78087_2_);
        ocelotFrontRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_);
        ocelotTail2.rotateAngleX = (1.7278761F + 0.31415927F * MathHelper.cos(p_78087_1_) * p_78087_2_);
      }
      else
      {
        ocelotBackLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_);
        ocelotBackRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_);
        ocelotFrontLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_);
        ocelotFrontRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_);
        
        if (field_78163_i == 1)
        {
          ocelotTail2.rotateAngleX = (1.7278761F + 0.7853982F * MathHelper.cos(p_78087_1_) * p_78087_2_);
        }
        else
        {
          ocelotTail2.rotateAngleX = (1.7278761F + 0.47123894F * MathHelper.cos(p_78087_1_) * p_78087_2_);
        }
      }
    }
  }
  




  public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
  {
    EntityOcelot var5 = (EntityOcelot)p_78086_1_;
    ocelotBody.rotationPointY = 12.0F;
    ocelotBody.rotationPointZ = -10.0F;
    ocelotHead.rotationPointY = 15.0F;
    ocelotHead.rotationPointZ = -9.0F;
    ocelotTail.rotationPointY = 15.0F;
    ocelotTail.rotationPointZ = 8.0F;
    ocelotTail2.rotationPointY = 20.0F;
    ocelotTail2.rotationPointZ = 14.0F;
    ocelotFrontLeftLeg.rotationPointY = (ocelotFrontRightLeg.rotationPointY = 13.8F);
    ocelotFrontLeftLeg.rotationPointZ = (ocelotFrontRightLeg.rotationPointZ = -5.0F);
    ocelotBackLeftLeg.rotationPointY = (ocelotBackRightLeg.rotationPointY = 18.0F);
    ocelotBackLeftLeg.rotationPointZ = (ocelotBackRightLeg.rotationPointZ = 5.0F);
    ocelotTail.rotateAngleX = 0.9F;
    
    if (var5.isSneaking())
    {
      ocelotBody.rotationPointY += 1.0F;
      ocelotHead.rotationPointY += 2.0F;
      ocelotTail.rotationPointY += 1.0F;
      ocelotTail2.rotationPointY += -4.0F;
      ocelotTail2.rotationPointZ += 2.0F;
      ocelotTail.rotateAngleX = 1.5707964F;
      ocelotTail2.rotateAngleX = 1.5707964F;
      field_78163_i = 0;
    }
    else if (var5.isSprinting())
    {
      ocelotTail2.rotationPointY = ocelotTail.rotationPointY;
      ocelotTail2.rotationPointZ += 2.0F;
      ocelotTail.rotateAngleX = 1.5707964F;
      ocelotTail2.rotateAngleX = 1.5707964F;
      field_78163_i = 2;
    }
    else if (var5.isSitting())
    {
      ocelotBody.rotateAngleX = 0.7853982F;
      ocelotBody.rotationPointY += -4.0F;
      ocelotBody.rotationPointZ += 5.0F;
      ocelotHead.rotationPointY += -3.3F;
      ocelotHead.rotationPointZ += 1.0F;
      ocelotTail.rotationPointY += 8.0F;
      ocelotTail.rotationPointZ += -2.0F;
      ocelotTail2.rotationPointY += 2.0F;
      ocelotTail2.rotationPointZ += -0.8F;
      ocelotTail.rotateAngleX = 1.7278761F;
      ocelotTail2.rotateAngleX = 2.670354F;
      ocelotFrontLeftLeg.rotateAngleX = (ocelotFrontRightLeg.rotateAngleX = -0.15707964F);
      ocelotFrontLeftLeg.rotationPointY = (ocelotFrontRightLeg.rotationPointY = 15.8F);
      ocelotFrontLeftLeg.rotationPointZ = (ocelotFrontRightLeg.rotationPointZ = -7.0F);
      ocelotBackLeftLeg.rotateAngleX = (ocelotBackRightLeg.rotateAngleX = -1.5707964F);
      ocelotBackLeftLeg.rotationPointY = (ocelotBackRightLeg.rotationPointY = 21.0F);
      ocelotBackLeftLeg.rotationPointZ = (ocelotBackRightLeg.rotationPointZ = 1.0F);
      field_78163_i = 3;
    }
    else
    {
      field_78163_i = 1;
    }
  }
}
