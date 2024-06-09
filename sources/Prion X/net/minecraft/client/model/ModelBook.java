package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBook
  extends ModelBase
{
  public ModelRenderer coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6.0F, -5.0F, 0.0F, 6, 10, 0);
  

  public ModelRenderer coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0.0F, -5.0F, 0.0F, 6, 10, 0);
  

  public ModelRenderer pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0.0F, -4.0F, -0.99F, 5, 8, 1);
  

  public ModelRenderer pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0.0F, -4.0F, -0.01F, 5, 8, 1);
  

  public ModelRenderer flippingPageRight = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
  

  public ModelRenderer flippingPageLeft = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
  

  public ModelRenderer bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2, 10, 0);
  private static final String __OBFID = "CL_00000833";
  
  public ModelBook()
  {
    coverRight.setRotationPoint(0.0F, 0.0F, -1.0F);
    coverLeft.setRotationPoint(0.0F, 0.0F, 1.0F);
    bookSpine.rotateAngleY = 1.5707964F;
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    coverRight.render(p_78088_7_);
    coverLeft.render(p_78088_7_);
    bookSpine.render(p_78088_7_);
    pagesRight.render(p_78088_7_);
    pagesLeft.render(p_78088_7_);
    flippingPageRight.render(p_78088_7_);
    flippingPageLeft.render(p_78088_7_);
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    float var8 = (MathHelper.sin(p_78087_1_ * 0.02F) * 0.1F + 1.25F) * p_78087_4_;
    coverRight.rotateAngleY = (3.1415927F + var8);
    coverLeft.rotateAngleY = (-var8);
    pagesRight.rotateAngleY = var8;
    pagesLeft.rotateAngleY = (-var8);
    flippingPageRight.rotateAngleY = (var8 - var8 * 2.0F * p_78087_2_);
    flippingPageLeft.rotateAngleY = (var8 - var8 * 2.0F * p_78087_3_);
    pagesRight.rotationPointX = MathHelper.sin(var8);
    pagesLeft.rotationPointX = MathHelper.sin(var8);
    flippingPageRight.rotationPointX = MathHelper.sin(var8);
    flippingPageLeft.rotationPointX = MathHelper.sin(var8);
  }
}
