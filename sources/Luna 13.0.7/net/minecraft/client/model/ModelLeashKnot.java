package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelLeashKnot
  extends ModelBase
{
  public ModelRenderer field_110723_a;
  private static final String __OBFID = "CL_00000843";
  
  public ModelLeashKnot()
  {
    this(0, 0, 32, 32);
  }
  
  public ModelLeashKnot(int p_i46365_1_, int p_i46365_2_, int p_i46365_3_, int p_i46365_4_)
  {
    this.textureWidth = p_i46365_3_;
    this.textureHeight = p_i46365_4_;
    this.field_110723_a = new ModelRenderer(this, p_i46365_1_, p_i46365_2_);
    this.field_110723_a.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
    this.field_110723_a.setRotationPoint(0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    this.field_110723_a.render(p_78088_7_);
  }
  
  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    this.field_110723_a.rotateAngleY = (p_78087_4_ / 57.295776F);
    this.field_110723_a.rotateAngleX = (p_78087_5_ / 57.295776F);
  }
}
