package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.Rotations;

public class ModelArmorStand extends ModelArmorStandArmor
{
  public ModelRenderer standRightSide;
  public ModelRenderer standLeftSide;
  public ModelRenderer standWaist;
  public ModelRenderer standBase;
  private static final String __OBFID = "CL_00002631";
  
  public ModelArmorStand()
  {
    this(0.0F);
  }
  
  public ModelArmorStand(float p_i46306_1_)
  {
    super(p_i46306_1_, 64, 64);
    bipedHead = new ModelRenderer(this, 0, 0);
    bipedHead.addBox(-1.0F, -7.0F, -1.0F, 2, 7, 2, p_i46306_1_);
    bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
    bipedBody = new ModelRenderer(this, 0, 26);
    bipedBody.addBox(-6.0F, 0.0F, -1.5F, 12, 3, 3, p_i46306_1_);
    bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
    bipedRightArm = new ModelRenderer(this, 24, 0);
    bipedRightArm.addBox(-2.0F, -2.0F, -1.0F, 2, 12, 2, p_i46306_1_);
    bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
    bipedLeftArm = new ModelRenderer(this, 32, 16);
    bipedLeftArm.mirror = true;
    bipedLeftArm.addBox(0.0F, -2.0F, -1.0F, 2, 12, 2, p_i46306_1_);
    bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
    bipedRightLeg = new ModelRenderer(this, 8, 0);
    bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, p_i46306_1_);
    bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
    bipedLeftLeg = new ModelRenderer(this, 40, 16);
    bipedLeftLeg.mirror = true;
    bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, p_i46306_1_);
    bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
    standRightSide = new ModelRenderer(this, 16, 0);
    standRightSide.addBox(-3.0F, 3.0F, -1.0F, 2, 7, 2, p_i46306_1_);
    standRightSide.setRotationPoint(0.0F, 0.0F, 0.0F);
    standRightSide.showModel = true;
    standLeftSide = new ModelRenderer(this, 48, 16);
    standLeftSide.addBox(1.0F, 3.0F, -1.0F, 2, 7, 2, p_i46306_1_);
    standLeftSide.setRotationPoint(0.0F, 0.0F, 0.0F);
    standWaist = new ModelRenderer(this, 0, 48);
    standWaist.addBox(-4.0F, 10.0F, -1.0F, 8, 2, 2, p_i46306_1_);
    standWaist.setRotationPoint(0.0F, 0.0F, 0.0F);
    standBase = new ModelRenderer(this, 0, 32);
    standBase.addBox(-6.0F, 11.0F, -6.0F, 12, 1, 12, p_i46306_1_);
    standBase.setRotationPoint(0.0F, 12.0F, 0.0F);
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    
    if ((p_78087_7_ instanceof EntityArmorStand))
    {
      EntityArmorStand var8 = (EntityArmorStand)p_78087_7_;
      bipedLeftArm.showModel = var8.getShowArms();
      bipedRightArm.showModel = var8.getShowArms();
      standBase.showModel = (!var8.hasNoBasePlate());
      bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
      bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
      standRightSide.rotateAngleX = (0.017453292F * var8.getBodyRotation().func_179415_b());
      standRightSide.rotateAngleY = (0.017453292F * var8.getBodyRotation().func_179416_c());
      standRightSide.rotateAngleZ = (0.017453292F * var8.getBodyRotation().func_179413_d());
      standLeftSide.rotateAngleX = (0.017453292F * var8.getBodyRotation().func_179415_b());
      standLeftSide.rotateAngleY = (0.017453292F * var8.getBodyRotation().func_179416_c());
      standLeftSide.rotateAngleZ = (0.017453292F * var8.getBodyRotation().func_179413_d());
      standWaist.rotateAngleX = (0.017453292F * var8.getBodyRotation().func_179415_b());
      standWaist.rotateAngleY = (0.017453292F * var8.getBodyRotation().func_179416_c());
      standWaist.rotateAngleZ = (0.017453292F * var8.getBodyRotation().func_179413_d());
      float var9 = (var8.getLeftLegRotation().func_179415_b() + var8.getRightLegRotation().func_179415_b()) / 2.0F;
      float var10 = (var8.getLeftLegRotation().func_179416_c() + var8.getRightLegRotation().func_179416_c()) / 2.0F;
      float var11 = (var8.getLeftLegRotation().func_179413_d() + var8.getRightLegRotation().func_179413_d()) / 2.0F;
      standBase.rotateAngleX = 0.0F;
      standBase.rotateAngleY = (0.017453292F * -rotationYaw);
      standBase.rotateAngleZ = 0.0F;
    }
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
    net.minecraft.client.renderer.GlStateManager.pushMatrix();
    
    if (isChild)
    {
      float var8 = 2.0F;
      net.minecraft.client.renderer.GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
      net.minecraft.client.renderer.GlStateManager.translate(0.0F, 24.0F * p_78088_7_, 0.0F);
      standRightSide.render(p_78088_7_);
      standLeftSide.render(p_78088_7_);
      standWaist.render(p_78088_7_);
      standBase.render(p_78088_7_);
    }
    else
    {
      if (p_78088_1_.isSneaking())
      {
        net.minecraft.client.renderer.GlStateManager.translate(0.0F, 0.2F, 0.0F);
      }
      
      standRightSide.render(p_78088_7_);
      standLeftSide.render(p_78088_7_);
      standWaist.render(p_78088_7_);
      standBase.render(p_78088_7_);
    }
    
    net.minecraft.client.renderer.GlStateManager.popMatrix();
  }
  
  public void postRenderHiddenArm(float p_178718_1_)
  {
    boolean var2 = bipedRightArm.showModel;
    bipedRightArm.showModel = true;
    super.postRenderHiddenArm(p_178718_1_);
    bipedRightArm.showModel = var2;
  }
}
