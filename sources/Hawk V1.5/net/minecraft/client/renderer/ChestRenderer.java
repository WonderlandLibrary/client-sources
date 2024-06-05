package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.item.ItemStack;

public class ChestRenderer {
   private static final String __OBFID = "CL_00002530";

   public void func_178175_a(Block var1, float var2) {
      GlStateManager.color(var2, var2, var2, 1.0F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      TileEntityRendererChestHelper.instance.renderByItem(new ItemStack(var1));
   }
}
