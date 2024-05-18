package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelWitch extends ModelVillager
{
  public boolean field_82900_g;
  private ModelRenderer field_82901_h = new ModelRenderer(this).setTextureSize(64, 128);
  private ModelRenderer witchHat;
  private static final String __OBFID = "CL_00000866";
  
  public ModelWitch(float p_i46361_1_)
  {
    super(p_i46361_1_, 0.0F, 64, 128);
    field_82901_h.setRotationPoint(0.0F, -2.0F, 0.0F);
    field_82901_h.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
    villagerNose.addChild(field_82901_h);
    witchHat = new ModelRenderer(this).setTextureSize(64, 128);
    witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
    witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
    villagerHead.addChild(witchHat);
    ModelRenderer var2 = new ModelRenderer(this).setTextureSize(64, 128);
    var2.setRotationPoint(1.75F, -4.0F, 2.0F);
    var2.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
    rotateAngleX = -0.05235988F;
    rotateAngleZ = 0.02617994F;
    witchHat.addChild(var2);
    ModelRenderer var3 = new ModelRenderer(this).setTextureSize(64, 128);
    var3.setRotationPoint(1.75F, -4.0F, 2.0F);
    var3.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
    rotateAngleX = -0.10471976F;
    rotateAngleZ = 0.05235988F;
    var2.addChild(var3);
    ModelRenderer var4 = new ModelRenderer(this).setTextureSize(64, 128);
    var4.setRotationPoint(1.75F, -2.0F, 2.0F);
    var4.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
    rotateAngleX = -0.20943952F;
    rotateAngleZ = 0.10471976F;
    var3.addChild(var4);
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    villagerNose.offsetX = (villagerNose.offsetY = villagerNose.offsetZ = 0.0F);
    float var8 = 0.01F * (p_78087_7_.getEntityId() % 10);
    villagerNose.rotateAngleX = (MathHelper.sin(ticksExisted * var8) * 4.5F * 3.1415927F / 180.0F);
    villagerNose.rotateAngleY = 0.0F;
    villagerNose.rotateAngleZ = (MathHelper.cos(ticksExisted * var8) * 2.5F * 3.1415927F / 180.0F);
    
    if (field_82900_g)
    {
      villagerNose.rotateAngleX = -0.9F;
      villagerNose.offsetZ = -0.09375F;
      villagerNose.offsetY = 0.1875F;
    }
  }
}
