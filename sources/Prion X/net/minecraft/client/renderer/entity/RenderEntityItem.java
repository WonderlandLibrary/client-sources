package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderEntityItem extends Render
{
  private final RenderItem field_177080_a;
  private Random field_177079_e = new Random();
  private static final String __OBFID = "CL_00002442";
  
  public RenderEntityItem(RenderManager p_i46167_1_, RenderItem p_i46167_2_)
  {
    super(p_i46167_1_);
    field_177080_a = p_i46167_2_;
    shadowSize = 0.15F;
    shadowOpaque = 0.75F;
  }
  
  private int func_177077_a(EntityItem p_177077_1_, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_)
  {
    ItemStack var10 = p_177077_1_.getEntityItem();
    net.minecraft.item.Item var11 = var10.getItem();
    
    if (var11 == null)
    {
      return 0;
    }
    

    boolean var12 = p_177077_9_.isAmbientOcclusionEnabled();
    int var13 = func_177078_a(var10);
    float var14 = 0.25F;
    float var15 = net.minecraft.util.MathHelper.sin((p_177077_1_.func_174872_o() + p_177077_8_) / 10.0F + hoverStart) * 0.1F + 0.1F;
    GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + var15 + 0.25F, (float)p_177077_6_);
    

    if ((var12) || ((renderManager.options != null) && (renderManager.options.fancyGraphics)))
    {
      float var16 = ((p_177077_1_.func_174872_o() + p_177077_8_) / 20.0F + hoverStart) * 57.295776F;
      GlStateManager.rotate(var16, 0.0F, 1.0F, 0.0F);
    }
    
    if (!var12)
    {
      float var16 = -0.0F * (var13 - 1) * 0.5F;
      float var17 = -0.0F * (var13 - 1) * 0.5F;
      float var18 = -0.046875F * (var13 - 1) * 0.5F;
      GlStateManager.translate(var16, var17, var18);
    }
    
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    return var13;
  }
  

  private int func_177078_a(ItemStack p_177078_1_)
  {
    byte var2 = 1;
    
    if (stackSize > 48)
    {
      var2 = 5;
    }
    else if (stackSize > 32)
    {
      var2 = 4;
    }
    else if (stackSize > 16)
    {
      var2 = 3;
    }
    else if (stackSize > 1)
    {
      var2 = 2;
    }
    
    return var2;
  }
  
  public void func_177075_a(EntityItem p_177075_1_, double p_177075_2_, double p_177075_4_, double p_177075_6_, float p_177075_8_, float p_177075_9_)
  {
    ItemStack var10 = p_177075_1_.getEntityItem();
    field_177079_e.setSeed(187L);
    boolean var11 = false;
    
    if (bindEntityTexture(p_177075_1_))
    {
      renderManager.renderEngine.getTexture(func_177076_a(p_177075_1_)).func_174936_b(false, false);
      var11 = true;
    }
    
    GlStateManager.enableRescaleNormal();
    GlStateManager.alphaFunc(516, 0.1F);
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GlStateManager.pushMatrix();
    IBakedModel var12 = field_177080_a.getItemModelMesher().getItemModel(var10);
    int var13 = func_177077_a(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_9_, var12);
    
    for (int var14 = 0; var14 < var13; var14++)
    {
      if (var12.isAmbientOcclusionEnabled())
      {
        GlStateManager.pushMatrix();
        
        if (var14 > 0)
        {
          float var15 = (field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
          float var16 = (field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
          float var17 = (field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
          GlStateManager.translate(var15, var16, var17);
        }
        
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        field_177080_a.func_180454_a(var10, var12);
        GlStateManager.popMatrix();
      }
      else
      {
        field_177080_a.func_180454_a(var10, var12);
        GlStateManager.translate(0.0F, 0.0F, 0.046875F);
      }
    }
    
    GlStateManager.popMatrix();
    GlStateManager.disableRescaleNormal();
    GlStateManager.disableBlend();
    bindEntityTexture(p_177075_1_);
    
    if (var11)
    {
      renderManager.renderEngine.getTexture(func_177076_a(p_177075_1_)).func_174935_a();
    }
    
    super.doRender(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_8_, p_177075_9_);
  }
  
  protected ResourceLocation func_177076_a(EntityItem p_177076_1_)
  {
    return net.minecraft.client.renderer.texture.TextureMap.locationBlocksTexture;
  }
  



  protected ResourceLocation getEntityTexture(Entity p_110775_1_)
  {
    return func_177076_a((EntityItem)p_110775_1_);
  }
  






  public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
  {
    func_177075_a((EntityItem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
  }
}
