package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LayerCape implements LayerRenderer
{
  private final RenderPlayer playerRenderer;
  private static final String __OBFID = "CL_00002425";
  
  public LayerCape(RenderPlayer p_i46123_1_)
  {
    playerRenderer = p_i46123_1_;
  }
  
  public void doRenderLayer(AbstractClientPlayer p_177166_1_, float p_177166_2_, float p_177166_3_, float p_177166_4_, float p_177166_5_, float p_177166_6_, float p_177166_7_, float p_177166_8_)
  {
    if ((p_177166_1_.hasCape()) && (!p_177166_1_.isInvisible()) && (p_177166_1_.func_175148_a(net.minecraft.entity.player.EnumPlayerModelParts.CAPE)) && (p_177166_1_.getLocationCape() != null))
    {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      playerRenderer.bindTexture(p_177166_1_.getLocationCape());
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.0F, 0.125F);
      double var9 = field_71091_bM + (field_71094_bP - field_71091_bM) * p_177166_4_ - (prevPosX + (posX - prevPosX) * p_177166_4_);
      double var11 = field_71096_bN + (field_71095_bQ - field_71096_bN) * p_177166_4_ - (prevPosY + (posY - prevPosY) * p_177166_4_);
      double var13 = field_71097_bO + (field_71085_bR - field_71097_bO) * p_177166_4_ - (prevPosZ + (posZ - prevPosZ) * p_177166_4_);
      float var15 = prevRenderYawOffset + (renderYawOffset - prevRenderYawOffset) * p_177166_4_;
      double var16 = MathHelper.sin(var15 * 3.1415927F / 180.0F);
      double var18 = -MathHelper.cos(var15 * 3.1415927F / 180.0F);
      float var20 = (float)var11 * 10.0F;
      var20 = MathHelper.clamp_float(var20, -6.0F, 32.0F);
      float var21 = (float)(var9 * var16 + var13 * var18) * 100.0F;
      float var22 = (float)(var9 * var18 - var13 * var16) * 100.0F;
      
      if (var21 < 0.0F)
      {
        var21 = 0.0F;
      }
      
      if (var21 > 165.0F)
      {
        var21 = 165.0F;
      }
      
      float var23 = prevCameraYaw + (cameraYaw - prevCameraYaw) * p_177166_4_;
      var20 += MathHelper.sin((prevDistanceWalkedModified + (distanceWalkedModified - prevDistanceWalkedModified) * p_177166_4_) * 6.0F) * 32.0F * var23;
      
      if (p_177166_1_.isSneaking())
      {
        var20 += 25.0F;
        GlStateManager.translate(0.0F, 0.142F, -0.0178F);
      }
      
      GlStateManager.rotate(6.0F + var21 / 2.0F + var20, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var22 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(-var22 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
      playerRenderer.func_177136_g().func_178728_c(0.0625F);
      GlStateManager.popMatrix();
    }
  }
  
  public boolean shouldCombineTextures()
  {
    return false;
  }
  
  public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
  {
    doRenderLayer((AbstractClientPlayer)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
