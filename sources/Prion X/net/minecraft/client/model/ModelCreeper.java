package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCreeper extends ModelBase
{
  public ModelRenderer head;
  public ModelRenderer creeperArmor;
  public ModelRenderer body;
  public ModelRenderer leg1;
  public ModelRenderer leg2;
  public ModelRenderer leg3;
  public ModelRenderer leg4;
  private static final String __OBFID = "CL_00000837";
  
  public ModelCreeper()
  {
    this(0.0F);
  }
  
  public ModelCreeper(float p_i46366_1_)
  {
    byte var2 = 6;
    head = new ModelRenderer(this, 0, 0);
    head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46366_1_);
    head.setRotationPoint(0.0F, var2, 0.0F);
    creeperArmor = new ModelRenderer(this, 32, 0);
    creeperArmor.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46366_1_ + 0.5F);
    creeperArmor.setRotationPoint(0.0F, var2, 0.0F);
    body = new ModelRenderer(this, 16, 16);
    body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46366_1_);
    body.setRotationPoint(0.0F, var2, 0.0F);
    leg1 = new ModelRenderer(this, 0, 16);
    leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    leg1.setRotationPoint(-2.0F, 12 + var2, 4.0F);
    leg2 = new ModelRenderer(this, 0, 16);
    leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    leg2.setRotationPoint(2.0F, 12 + var2, 4.0F);
    leg3 = new ModelRenderer(this, 0, 16);
    leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    leg3.setRotationPoint(-2.0F, 12 + var2, -4.0F);
    leg4 = new ModelRenderer(this, 0, 16);
    leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    leg4.setRotationPoint(2.0F, 12 + var2, -4.0F);
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    head.render(p_78088_7_);
    body.render(p_78088_7_);
    leg1.render(p_78088_7_);
    leg2.render(p_78088_7_);
    leg3.render(p_78088_7_);
    leg4.render(p_78088_7_);
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    head.rotateAngleY = (p_78087_4_ / 57.295776F);
    head.rotateAngleX = (p_78087_5_ / 57.295776F);
    leg1.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
    leg2.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
    leg3.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
    leg4.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
  }
}
