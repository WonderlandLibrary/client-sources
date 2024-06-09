package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.MathHelper;










public class ModelBat
  extends ModelBase
{
  private ModelRenderer batHead;
  private ModelRenderer batBody;
  private ModelRenderer batRightWing;
  private ModelRenderer batLeftWing;
  private ModelRenderer batOuterRightWing;
  private ModelRenderer batOuterLeftWing;
  private static final String __OBFID = "CL_00000830";
  
  public ModelBat()
  {
    textureWidth = 64;
    textureHeight = 64;
    batHead = new ModelRenderer(this, 0, 0);
    batHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
    ModelRenderer var1 = new ModelRenderer(this, 24, 0);
    var1.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
    batHead.addChild(var1);
    ModelRenderer var2 = new ModelRenderer(this, 24, 0);
    mirror = true;
    var2.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
    batHead.addChild(var2);
    batBody = new ModelRenderer(this, 0, 16);
    batBody.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
    batBody.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
    batRightWing = new ModelRenderer(this, 42, 0);
    batRightWing.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
    batOuterRightWing = new ModelRenderer(this, 24, 16);
    batOuterRightWing.setRotationPoint(-12.0F, 1.0F, 1.5F);
    batOuterRightWing.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
    batLeftWing = new ModelRenderer(this, 42, 0);
    batLeftWing.mirror = true;
    batLeftWing.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
    batOuterLeftWing = new ModelRenderer(this, 24, 16);
    batOuterLeftWing.mirror = true;
    batOuterLeftWing.setRotationPoint(12.0F, 1.0F, 1.5F);
    batOuterLeftWing.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
    batBody.addChild(batRightWing);
    batBody.addChild(batLeftWing);
    batRightWing.addChild(batOuterRightWing);
    batLeftWing.addChild(batOuterLeftWing);
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    batHead.render(p_78088_7_);
    batBody.render(p_78088_7_);
  }
  







  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    if (((EntityBat)p_78087_7_).getIsBatHanging())
    {
      float var8 = 57.295776F;
      batHead.rotateAngleX = (p_78087_5_ / 57.295776F);
      batHead.rotateAngleY = (3.1415927F - p_78087_4_ / 57.295776F);
      batHead.rotateAngleZ = 3.1415927F;
      batHead.setRotationPoint(0.0F, -2.0F, 0.0F);
      batRightWing.setRotationPoint(-3.0F, 0.0F, 3.0F);
      batLeftWing.setRotationPoint(3.0F, 0.0F, 3.0F);
      batBody.rotateAngleX = 3.1415927F;
      batRightWing.rotateAngleX = -0.15707964F;
      batRightWing.rotateAngleY = -1.2566371F;
      batOuterRightWing.rotateAngleY = -1.7278761F;
      batLeftWing.rotateAngleX = batRightWing.rotateAngleX;
      batLeftWing.rotateAngleY = (-batRightWing.rotateAngleY);
      batOuterLeftWing.rotateAngleY = (-batOuterRightWing.rotateAngleY);
    }
    else
    {
      float var8 = 57.295776F;
      batHead.rotateAngleX = (p_78087_5_ / 57.295776F);
      batHead.rotateAngleY = (p_78087_4_ / 57.295776F);
      batHead.rotateAngleZ = 0.0F;
      batHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      batRightWing.setRotationPoint(0.0F, 0.0F, 0.0F);
      batLeftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
      batBody.rotateAngleX = (0.7853982F + MathHelper.cos(p_78087_3_ * 0.1F) * 0.15F);
      batBody.rotateAngleY = 0.0F;
      batRightWing.rotateAngleY = (MathHelper.cos(p_78087_3_ * 1.3F) * 3.1415927F * 0.25F);
      batLeftWing.rotateAngleY = (-batRightWing.rotateAngleY);
      batOuterRightWing.rotateAngleY = (batRightWing.rotateAngleY * 0.5F);
      batOuterLeftWing.rotateAngleY = (-batRightWing.rotateAngleY * 0.5F);
    }
  }
}
