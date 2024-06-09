package net.minecraft.client.model;

import net.minecraft.entity.Entity;







public class ModelSlime
  extends ModelBase
{
  ModelRenderer slimeBodies;
  ModelRenderer slimeRightEye;
  ModelRenderer slimeLeftEye;
  ModelRenderer slimeMouth;
  private static final String __OBFID = "CL_00000858";
  
  public ModelSlime(int p_i1157_1_)
  {
    slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
    slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
    
    if (p_i1157_1_ > 0)
    {
      slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
      slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
      slimeRightEye = new ModelRenderer(this, 32, 0);
      slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
      slimeLeftEye = new ModelRenderer(this, 32, 4);
      slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
      slimeMouth = new ModelRenderer(this, 32, 8);
      slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
    }
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    slimeBodies.render(p_78088_7_);
    
    if (slimeRightEye != null)
    {
      slimeRightEye.render(p_78088_7_);
      slimeLeftEye.render(p_78088_7_);
      slimeMouth.render(p_78088_7_);
    }
  }
}
