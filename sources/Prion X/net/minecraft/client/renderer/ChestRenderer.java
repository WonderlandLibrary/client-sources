package net.minecraft.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;

public class ChestRenderer
{
  private static final String __OBFID = "CL_00002530";
  
  public ChestRenderer() {}
  
  public void func_178175_a(net.minecraft.block.Block p_178175_1_, float p_178175_2_)
  {
    GlStateManager.color(p_178175_2_, p_178175_2_, p_178175_2_, 1.0F);
    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
    TileEntityRendererChestHelper.instance.renderByItem(new net.minecraft.item.ItemStack(p_178175_1_));
  }
}
