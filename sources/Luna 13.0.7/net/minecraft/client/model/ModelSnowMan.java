package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSnowMan
  extends ModelBase
{
  public ModelRenderer body;
  public ModelRenderer bottomBody;
  public ModelRenderer head;
  public ModelRenderer rightHand;
  public ModelRenderer leftHand;
  private static final String __OBFID = "CL_00000859";
  
  public ModelSnowMan()
  {
    float var1 = 4.0F;
    float var2 = 0.0F;
    this.head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
    this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var2 - 0.5F);
    this.head.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
    this.rightHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
    this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, var2 - 0.5F);
    this.rightHand.setRotationPoint(0.0F, 0.0F + var1 + 9.0F - 7.0F, 0.0F);
    this.leftHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64);
    this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, var2 - 0.5F);
    this.leftHand.setRotationPoint(0.0F, 0.0F + var1 + 9.0F - 7.0F, 0.0F);
    this.body = new ModelRenderer(this, 0, 16).setTextureSize(64, 64);
    this.body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, var2 - 0.5F);
    this.body.setRotationPoint(0.0F, 0.0F + var1 + 9.0F, 0.0F);
    this.bottomBody = new ModelRenderer(this, 0, 36).setTextureSize(64, 64);
    this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, var2 - 0.5F);
    this.bottomBody.setRotationPoint(0.0F, 0.0F + var1 + 20.0F, 0.0F);
  }
  
  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    this.head.rotateAngleY = (p_78087_4_ / 57.295776F);
    this.head.rotateAngleX = (p_78087_5_ / 57.295776F);
    this.body.rotateAngleY = (p_78087_4_ / 57.295776F * 0.25F);
    float var8 = MathHelper.sin(this.body.rotateAngleY);
    float var9 = MathHelper.cos(this.body.rotateAngleY);
    this.rightHand.rotateAngleZ = 1.0F;
    this.leftHand.rotateAngleZ = -1.0F;
    this.rightHand.rotateAngleY = (0.0F + this.body.rotateAngleY);
    this.leftHand.rotateAngleY = (3.1415927F + this.body.rotateAngleY);
    this.rightHand.rotationPointX = (var9 * 5.0F);
    this.rightHand.rotationPointZ = (-var8 * 5.0F);
    this.leftHand.rotationPointX = (-var9 * 5.0F);
    this.leftHand.rotationPointZ = (var8 * 5.0F);
  }
  
  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    this.body.render(p_78088_7_);
    this.bottomBody.render(p_78088_7_);
    this.head.render(p_78088_7_);
    this.rightHand.render(p_78088_7_);
    this.leftHand.render(p_78088_7_);
  }
}
