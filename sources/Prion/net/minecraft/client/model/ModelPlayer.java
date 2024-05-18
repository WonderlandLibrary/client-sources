package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPlayer extends ModelBiped
{
  public ModelRenderer field_178734_a;
  public ModelRenderer field_178732_b;
  public ModelRenderer field_178733_c;
  public ModelRenderer field_178731_d;
  public ModelRenderer field_178730_v;
  private ModelRenderer field_178729_w;
  private ModelRenderer field_178736_x;
  private boolean field_178735_y;
  private static final String __OBFID = "CL_00002626";
  
  public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_)
  {
    super(p_i46304_1_, 0.0F, 64, 64);
    field_178735_y = p_i46304_2_;
    field_178736_x = new ModelRenderer(this, 24, 0);
    field_178736_x.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
    field_178729_w = new ModelRenderer(this, 0, 0);
    field_178729_w.setTextureSize(64, 32);
    field_178729_w.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46304_1_);
    
    if (p_i46304_2_)
    {
      bipedLeftArm = new ModelRenderer(this, 32, 48);
      bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
      bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
      bipedRightArm = new ModelRenderer(this, 40, 16);
      bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
      bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
      field_178734_a = new ModelRenderer(this, 48, 48);
      field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
      field_178734_a.setRotationPoint(5.0F, 2.5F, 0.0F);
      field_178732_b = new ModelRenderer(this, 40, 32);
      field_178732_b.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
      field_178732_b.setRotationPoint(-5.0F, 2.5F, 10.0F);
    }
    else
    {
      bipedLeftArm = new ModelRenderer(this, 32, 48);
      bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_);
      bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
      field_178734_a = new ModelRenderer(this, 48, 48);
      field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
      field_178734_a.setRotationPoint(5.0F, 2.0F, 0.0F);
      field_178732_b = new ModelRenderer(this, 40, 32);
      field_178732_b.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
      field_178732_b.setRotationPoint(-5.0F, 2.0F, 10.0F);
    }
    
    bipedLeftLeg = new ModelRenderer(this, 16, 48);
    bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_);
    bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
    field_178733_c = new ModelRenderer(this, 0, 48);
    field_178733_c.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
    field_178733_c.setRotationPoint(1.9F, 12.0F, 0.0F);
    field_178731_d = new ModelRenderer(this, 0, 32);
    field_178731_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
    field_178731_d.setRotationPoint(-1.9F, 12.0F, 0.0F);
    field_178730_v = new ModelRenderer(this, 16, 32);
    field_178730_v.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46304_1_ + 0.25F);
    field_178730_v.setRotationPoint(0.0F, 0.0F, 0.0F);
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
    GlStateManager.pushMatrix();
    
    if (isChild)
    {
      float var8 = 2.0F;
      GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
      GlStateManager.translate(0.0F, 24.0F * p_78088_7_, 0.0F);
      field_178733_c.render(p_78088_7_);
      field_178731_d.render(p_78088_7_);
      field_178734_a.render(p_78088_7_);
      field_178732_b.render(p_78088_7_);
      field_178730_v.render(p_78088_7_);
    }
    else
    {
      if (p_78088_1_.isSneaking())
      {
        GlStateManager.translate(0.0F, 0.2F, 0.0F);
      }
      
      field_178733_c.render(p_78088_7_);
      field_178731_d.render(p_78088_7_);
      field_178734_a.render(p_78088_7_);
      field_178732_b.render(p_78088_7_);
      field_178730_v.render(p_78088_7_);
    }
    
    GlStateManager.popMatrix();
  }
  
  public void func_178727_b(float p_178727_1_)
  {
    func_178685_a(bipedHead, field_178736_x);
    field_178736_x.rotationPointX = 0.0F;
    field_178736_x.rotationPointY = 0.0F;
    field_178736_x.render(p_178727_1_);
  }
  
  public void func_178728_c(float p_178728_1_)
  {
    field_178729_w.render(p_178728_1_);
  }
  





  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    func_178685_a(bipedLeftLeg, field_178733_c);
    func_178685_a(bipedRightLeg, field_178731_d);
    func_178685_a(bipedLeftArm, field_178734_a);
    func_178685_a(bipedRightArm, field_178732_b);
    func_178685_a(bipedBody, field_178730_v);
  }
  
  public void func_178725_a()
  {
    bipedRightArm.render(0.0625F);
    field_178732_b.render(0.0625F);
  }
  
  public void func_178726_b()
  {
    bipedLeftArm.render(0.0625F);
    field_178734_a.render(0.0625F);
  }
  
  public void func_178719_a(boolean p_178719_1_)
  {
    super.func_178719_a(p_178719_1_);
    field_178734_a.showModel = p_178719_1_;
    field_178732_b.showModel = p_178719_1_;
    field_178733_c.showModel = p_178719_1_;
    field_178731_d.showModel = p_178719_1_;
    field_178730_v.showModel = p_178719_1_;
    field_178729_w.showModel = p_178719_1_;
    field_178736_x.showModel = p_178719_1_;
  }
  
  public void postRenderHiddenArm(float p_178718_1_)
  {
    if (field_178735_y)
    {
      bipedRightArm.rotationPointX += 1.0F;
      bipedRightArm.postRender(p_178718_1_);
      bipedRightArm.rotationPointX -= 1.0F;
    }
    else
    {
      bipedRightArm.postRender(p_178718_1_);
    }
  }
}
