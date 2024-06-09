package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.MathHelper;























public class ModelRabbit
  extends ModelBase
{
  ModelRenderer rabbitLeftFoot;
  ModelRenderer rabbitRightFoot;
  ModelRenderer rabbitLeftThigh;
  ModelRenderer rabbitRightThigh;
  ModelRenderer rabbitBody;
  ModelRenderer rabbitLeftArm;
  ModelRenderer rabbitRightArm;
  ModelRenderer rabbitHead;
  ModelRenderer rabbitRightEar;
  ModelRenderer rabbitLeftEar;
  ModelRenderer rabbitTail;
  ModelRenderer rabbitNose;
  private float field_178701_m = 0.0F;
  private float field_178699_n = 0.0F;
  private static final String __OBFID = "CL_00002625";
  
  public ModelRabbit()
  {
    setTextureOffset("head.main", 0, 0);
    setTextureOffset("head.nose", 0, 24);
    setTextureOffset("head.ear1", 0, 10);
    setTextureOffset("head.ear2", 6, 10);
    rabbitLeftFoot = new ModelRenderer(this, 26, 24);
    rabbitLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
    rabbitLeftFoot.setRotationPoint(3.0F, 17.5F, 3.7F);
    rabbitLeftFoot.mirror = true;
    setRotationOffset(rabbitLeftFoot, 0.0F, 0.0F, 0.0F);
    rabbitRightFoot = new ModelRenderer(this, 8, 24);
    rabbitRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
    rabbitRightFoot.setRotationPoint(-3.0F, 17.5F, 3.7F);
    rabbitRightFoot.mirror = true;
    setRotationOffset(rabbitRightFoot, 0.0F, 0.0F, 0.0F);
    rabbitLeftThigh = new ModelRenderer(this, 30, 15);
    rabbitLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
    rabbitLeftThigh.setRotationPoint(3.0F, 17.5F, 3.7F);
    rabbitLeftThigh.mirror = true;
    setRotationOffset(rabbitLeftThigh, -0.34906584F, 0.0F, 0.0F);
    rabbitRightThigh = new ModelRenderer(this, 16, 15);
    rabbitRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
    rabbitRightThigh.setRotationPoint(-3.0F, 17.5F, 3.7F);
    rabbitRightThigh.mirror = true;
    setRotationOffset(rabbitRightThigh, -0.34906584F, 0.0F, 0.0F);
    rabbitBody = new ModelRenderer(this, 0, 0);
    rabbitBody.addBox(-3.0F, -2.0F, -10.0F, 6, 5, 10);
    rabbitBody.setRotationPoint(0.0F, 19.0F, 8.0F);
    rabbitBody.mirror = true;
    setRotationOffset(rabbitBody, -0.34906584F, 0.0F, 0.0F);
    rabbitLeftArm = new ModelRenderer(this, 8, 15);
    rabbitLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
    rabbitLeftArm.setRotationPoint(3.0F, 17.0F, -1.0F);
    rabbitLeftArm.mirror = true;
    setRotationOffset(rabbitLeftArm, -0.17453292F, 0.0F, 0.0F);
    rabbitRightArm = new ModelRenderer(this, 0, 15);
    rabbitRightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
    rabbitRightArm.setRotationPoint(-3.0F, 17.0F, -1.0F);
    rabbitRightArm.mirror = true;
    setRotationOffset(rabbitRightArm, -0.17453292F, 0.0F, 0.0F);
    rabbitHead = new ModelRenderer(this, 32, 0);
    rabbitHead.addBox(-2.5F, -4.0F, -5.0F, 5, 4, 5);
    rabbitHead.setRotationPoint(0.0F, 16.0F, -1.0F);
    rabbitHead.mirror = true;
    setRotationOffset(rabbitHead, 0.0F, 0.0F, 0.0F);
    rabbitRightEar = new ModelRenderer(this, 52, 0);
    rabbitRightEar.addBox(-2.5F, -9.0F, -1.0F, 2, 5, 1);
    rabbitRightEar.setRotationPoint(0.0F, 16.0F, -1.0F);
    rabbitRightEar.mirror = true;
    setRotationOffset(rabbitRightEar, 0.0F, -0.2617994F, 0.0F);
    rabbitLeftEar = new ModelRenderer(this, 58, 0);
    rabbitLeftEar.addBox(0.5F, -9.0F, -1.0F, 2, 5, 1);
    rabbitLeftEar.setRotationPoint(0.0F, 16.0F, -1.0F);
    rabbitLeftEar.mirror = true;
    setRotationOffset(rabbitLeftEar, 0.0F, 0.2617994F, 0.0F);
    rabbitTail = new ModelRenderer(this, 52, 6);
    rabbitTail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2);
    rabbitTail.setRotationPoint(0.0F, 20.0F, 7.0F);
    rabbitTail.mirror = true;
    setRotationOffset(rabbitTail, -0.3490659F, 0.0F, 0.0F);
    rabbitNose = new ModelRenderer(this, 32, 9);
    rabbitNose.addBox(-0.5F, -2.5F, -5.5F, 1, 1, 1);
    rabbitNose.setRotationPoint(0.0F, 16.0F, -1.0F);
    rabbitNose.mirror = true;
    setRotationOffset(rabbitNose, 0.0F, 0.0F, 0.0F);
  }
  
  private void setRotationOffset(ModelRenderer p_178691_1_, float p_178691_2_, float p_178691_3_, float p_178691_4_)
  {
    rotateAngleX = p_178691_2_;
    rotateAngleY = p_178691_3_;
    rotateAngleZ = p_178691_4_;
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    
    if (isChild)
    {
      float var8 = 2.0F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 5.0F * p_78088_7_, 2.0F * p_78088_7_);
      rabbitHead.render(p_78088_7_);
      rabbitLeftEar.render(p_78088_7_);
      rabbitRightEar.render(p_78088_7_);
      rabbitNose.render(p_78088_7_);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
      GlStateManager.translate(0.0F, 24.0F * p_78088_7_, 0.0F);
      rabbitLeftFoot.render(p_78088_7_);
      rabbitRightFoot.render(p_78088_7_);
      rabbitLeftThigh.render(p_78088_7_);
      rabbitRightThigh.render(p_78088_7_);
      rabbitBody.render(p_78088_7_);
      rabbitLeftArm.render(p_78088_7_);
      rabbitRightArm.render(p_78088_7_);
      rabbitTail.render(p_78088_7_);
      GlStateManager.popMatrix();
    }
    else
    {
      rabbitLeftFoot.render(p_78088_7_);
      rabbitRightFoot.render(p_78088_7_);
      rabbitLeftThigh.render(p_78088_7_);
      rabbitRightThigh.render(p_78088_7_);
      rabbitBody.render(p_78088_7_);
      rabbitLeftArm.render(p_78088_7_);
      rabbitRightArm.render(p_78088_7_);
      rabbitHead.render(p_78088_7_);
      rabbitRightEar.render(p_78088_7_);
      rabbitLeftEar.render(p_78088_7_);
      rabbitTail.render(p_78088_7_);
      rabbitNose.render(p_78088_7_);
    }
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    float var8 = p_78087_3_ - ticksExisted;
    EntityRabbit var9 = (EntityRabbit)p_78087_7_;
    rabbitNose.rotateAngleX = (rabbitHead.rotateAngleX = rabbitRightEar.rotateAngleX = rabbitLeftEar.rotateAngleX = p_78087_5_ * 0.017453292F);
    rabbitNose.rotateAngleY = (rabbitHead.rotateAngleY = p_78087_4_ * 0.017453292F);
    rabbitRightEar.rotateAngleY = (rabbitNose.rotateAngleY - 0.2617994F);
    rabbitLeftEar.rotateAngleY = (rabbitNose.rotateAngleY + 0.2617994F);
    field_178701_m = MathHelper.sin(var9.func_175521_o(var8) * 3.1415927F);
    rabbitLeftThigh.rotateAngleX = (rabbitRightThigh.rotateAngleX = (field_178701_m * 50.0F - 21.0F) * 0.017453292F);
    rabbitLeftFoot.rotateAngleX = (rabbitRightFoot.rotateAngleX = field_178701_m * 50.0F * 0.017453292F);
    rabbitLeftArm.rotateAngleX = (rabbitRightArm.rotateAngleX = (field_178701_m * -40.0F - 11.0F) * 0.017453292F);
  }
  
  public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {}
}
