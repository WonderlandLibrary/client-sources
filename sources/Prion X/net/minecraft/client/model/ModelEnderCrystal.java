package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;



public class ModelEnderCrystal
  extends ModelBase
{
  private ModelRenderer cube;
  private ModelRenderer glass = new ModelRenderer(this, "glass");
  
  private ModelRenderer base;
  
  private static final String __OBFID = "CL_00000871";
  
  public ModelEnderCrystal(float p_i1170_1_, boolean p_i1170_2_)
  {
    glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    cube = new ModelRenderer(this, "cube");
    cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    
    if (p_i1170_2_)
    {
      base = new ModelRenderer(this, "base");
      base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
    }
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    GlStateManager.pushMatrix();
    GlStateManager.scale(2.0F, 2.0F, 2.0F);
    GlStateManager.translate(0.0F, -0.5F, 0.0F);
    
    if (base != null)
    {
      base.render(p_78088_7_);
    }
    
    GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
    GlStateManager.translate(0.0F, 0.8F + p_78088_4_, 0.0F);
    GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
    glass.render(p_78088_7_);
    float var8 = 0.875F;
    GlStateManager.scale(var8, var8, var8);
    GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
    GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
    glass.render(p_78088_7_);
    GlStateManager.scale(var8, var8, var8);
    GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
    GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
    cube.render(p_78088_7_);
    GlStateManager.popMatrix();
  }
}
